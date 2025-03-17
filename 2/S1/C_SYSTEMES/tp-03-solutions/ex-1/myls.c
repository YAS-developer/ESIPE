#include <dirent.h>
#include <grp.h>
#include <pwd.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <time.h>
#include <unistd.h>
#include "../try.h"

/* Global configuration */
static struct {
  bool multiArgs, longList, numeric, recursive;
  int exitStatus;
  char *progName;
} cfg;

/* User interface */
static void printHelp(FILE *file);
static void parseCmdLine(int argc, char **argv);

/* Path structure (basically a stack of strings) */
#define MAXPATH 1024
typedef struct {
  char string[MAXPATH];
  size_t length;
} Path;
static void reset(Path *path, size_t length);
static size_t append(Path *path, const char *s);

/* Display directory entries */
static void display(Path *path, const char *fileName);
static void displayFile(Path *path, const char *fileName);
static void displayDir(Path *path, const char *fileName);

/* Filter directory entries */
static int isVisible(const struct dirent *dirEnt);
static int isDir(const struct dirent *dirEnt);

/* -------------------------------------------------------------------------- */

int main(int argc, char **argv) {
  cfg.exitStatus = EXIT_SUCCESS;
  parseCmdLine(argc, argv);
  Path path;
  reset(&path, 0);
  if (optind == argc) {
    display(&path, ".");
  }
  else {
    for ( ; optind < argc; optind++) {
      display(&path, argv[optind]);
    }
  }
  exit(cfg.exitStatus);
}

static void printHelp(FILE *file) {
  fprintf(file, "Usage: %s [OPTIONS]\n", cfg.progName);
  fprintf(file, "Options:\n"
          " -l  use a long listing format\n"
          " -n  like -l, but list numeric user and group IDs\n"
          " -R  list subdirectories recursively\n");
}

static void parseCmdLine(int argc, char **argv) {
  cfg.progName = argv[0];
  int opt;
  while ((opt = getopt(argc, argv, "lnR")) != -1) {
    switch (opt) {
    case 'l':
      cfg.longList = true;
      break;
    case 'n':
      cfg.numeric = true;
      break;
    case 'R':
      cfg.recursive = true;
      break;
    default:
      printHelp(stderr);
      exit(EXIT_FAILURE);
    }
  }
  if (argc - optind > 1) {
    cfg.multiArgs = true;
  }
}

static void reset(Path *path, size_t length) {
  path->string[length] = '\0';
  path->length = length;
}

static size_t append(Path *path, const char *s) {
  size_t oldLength = path->length;
  if (path->length > 0) {
    path->string[path->length++] = '/';
  }
  size_t i = 0;
  while (s[i] != '\0' && path->length < MAXPATH) {
    path->string[path->length++] = s[i++];
  }
  if (path->length == MAXPATH) {
    fprintf(stderr, "%s: exceeded maximum path length.\n", cfg.progName);
    exit(EXIT_FAILURE);
  }
  path->string[path->length] = '\0';
  return oldLength;
}

static void display(Path *path, const char *fileName) {
  struct stat sb;
  if (lstat(fileName, &sb) == -1) {
    fprintf(stderr, "%s: cannot access '%s': %s\n",
            cfg.progName, fileName, strerror(errno));
    cfg.exitStatus = EXIT_FAILURE;
    return;
  }
  if (S_ISDIR(sb.st_mode)) {
    displayDir(path, fileName);
  }
  else {
    displayFile(path, fileName);
  }
}

static void displayFile(Path *path, const char *fileName) {
  if (!cfg.longList && !cfg.numeric) {
    printf("%s\n", fileName);
    return;
  }
  size_t oldLength = append(path, fileName);
  struct stat sb;
  try(lstat(path->string, &sb));
  printf("%c%c%c%c%c%c%c%c%c%c ",
         S_ISDIR(sb.st_mode) ? 'd' : S_ISLNK(sb.st_mode) ? 'l' : '-',
         sb.st_mode & S_IRUSR ? 'r' : '-',
         sb.st_mode & S_IWUSR ? 'w' : '-',
         sb.st_mode & S_IXUSR ? 'x' : '-',
         sb.st_mode & S_IRGRP ? 'r' : '-',
         sb.st_mode & S_IWGRP ? 'w' : '-',
         sb.st_mode & S_IXGRP ? 'x' : '-',
         sb.st_mode & S_IROTH ? 'r' : '-',
         sb.st_mode & S_IWOTH ? 'w' : '-',
         sb.st_mode & S_IXOTH ? 'x' : '-');
  printf("%ld ", sb.st_nlink);
  if (cfg.numeric) {
    printf("%d ", sb.st_uid);
    printf("%d ", sb.st_gid);
  }
  else {
    printf("%s ", getpwuid(sb.st_uid)->pw_name);
    printf("%s ", getgrgid(sb.st_gid)->gr_name);
  }
  printf("%ld ", sb.st_size);
  printf("%.24s ", try(ctime(&sb.st_mtime), NULL));
  printf("%s", fileName);
  if (S_ISLNK(sb.st_mode)) {
    char linkPath[MAXPATH];
    ssize_t length = try(readlink(path->string, linkPath, MAXPATH - 1));
    linkPath[length] = '\0';
    printf(" -> %s ", linkPath);
  }
  printf("\n");
  reset(path, oldLength);
}

static void displayDir(Path *path, const char *fileName) {
  static bool needSeparator = false;
  size_t oldLength = append(path, fileName);
  if (cfg.multiArgs || cfg.recursive) {
    printf("%s%s:\n", needSeparator ? "\n" : "", path->string);
    needSeparator = true;
  }
  struct dirent **dirEntList;
  int dirLength = try(scandir(path->string, &dirEntList, isVisible, alphasort));
  for (int i = 0; i < dirLength; i++) {
    displayFile(path, dirEntList[i]->d_name);
    free(dirEntList[i]);
  }
  free(dirEntList);
  if (cfg.recursive) {
    dirLength = try(scandir(path->string, &dirEntList, isDir, alphasort));
    for (int i = 0; i < dirLength; i++) {
      displayDir(path, dirEntList[i]->d_name);
      free(dirEntList[i]);
    }
    free(dirEntList);
  }
  reset(path, oldLength);
}

static int isVisible(const struct dirent *dirEnt) {
  return dirEnt->d_name[0] != '.';
}

static int isDir(const struct dirent *dirEnt) {
  if (dirEnt->d_type == DT_UNKNOWN) {
    fprintf(stderr, "dirent: file system does not support d_type.\n");
    exit(EXIT_FAILURE);
  }
  return dirEnt->d_type == DT_DIR && isVisible(dirEnt);
}
