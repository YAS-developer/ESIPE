# Guide détaillé des fonctions pour les TPs UNIX

## 1. Fonctions d'entrée/sortie standard (stdio)

### fputc/fgetc
```c
int fputc(int c, FILE *stream);
int fgetc(FILE *stream);
```

**Arguments** :
- `c` : Caractère à écrire (converti en unsigned char)
- `stream` : Flux de sortie (ex: stdout, stderr, ou fichier ouvert)

**Exemple** :
```c
// Copie caractère par caractère
int c;
while ((c = fgetc(stdin)) != EOF) {
    fputc(c, stdout);
}
```

### printf/scanf
```c
int printf(const char *format, ...);
int scanf(const char *format, ...);
```

**Format courants** :
- `%d` : entier décimal
- `%s` : chaîne de caractères
- `%c` : caractère
- `%x` : hexadécimal
- `%o` : octal
- `%lu` : unsigned long

**Exemple** :
```c
// Lecture de coordonnées x,y
int x, y;
scanf("%d,%d", &x, &y);
printf("Position: (%d,%d)\n", x, y);
```

## 2. Lecture/Écriture bas niveau

### read/write
```c
ssize_t read(int fd, void *buf, size_t count);
ssize_t write(int fd, const void *buf, size_t count);
```

**Arguments** :
- `fd` : Descripteur de fichier (0: stdin, 1: stdout, 2: stderr)
- `buf` : Buffer pour stocker/lire les données
- `count` : Nombre d'octets à lire/écrire

**Exemple** :
```c
char buffer[1024];
// Lecture de 1024 octets maximum
ssize_t bytes_read = read(0, buffer, sizeof(buffer));
if (bytes_read > 0) {
    // Écriture vers stdout
    write(1, buffer, bytes_read);
}
```

## 3. Manipulation de fichiers

### open
```c
int open(const char *pathname, int flags, mode_t mode);
```

**Arguments** :
- `pathname` : Chemin du fichier
- `flags` : Combinaison de flags avec |
  - `O_RDONLY` : Lecture seule
  - `O_WRONLY` : Écriture seule
  - `O_RDWR` : Lecture et écriture
  - `O_CREAT` : Créer si n'existe pas
  - `O_TRUNC` : Vider le fichier
  - `O_APPEND` : Ajouter à la fin
- `mode` : Permissions (avec O_CREAT)
  - ex: 0644 (rw-r--r--)

**Exemples** :
```c
// Ouvrir pour lecture
int fd_read = open("input.txt", O_RDONLY);

// Créer/écraser pour écriture
int fd_write = open("output.txt", O_WRONLY | O_CREAT | O_TRUNC, 0644);

// Ouvrir pour append
int fd_append = open("log.txt", O_WRONLY | O_CREAT | O_APPEND, 0644);
```

## 4. Gestion des répertoires

### opendir/readdir/closedir
```c
DIR *opendir(const char *name);
struct dirent *readdir(DIR *dirp);
int closedir(DIR *dirp);
```

**Structure dirent** :
```c
struct dirent {
    ino_t          d_ino;       // Numéro d'inode
    char           d_name[256]; // Nom du fichier
    // ... autres champs
};
```

**Exemple** :
```c
DIR *dir = opendir(".");
if (dir) {
    struct dirent *entry;
    while ((entry = readdir(dir)) != NULL) {
        // Ignorer . et ..
        if (strcmp(entry->d_name, ".") != 0 && 
            strcmp(entry->d_name, "..") != 0) {
            printf("%s\n", entry->d_name);
        }
    }
    closedir(dir);
}
```

### scandir
```c
int scandir(const char *dirp, struct dirent ***namelist,
            int (*filter)(const struct dirent *),
            int (*compar)(const struct dirent **, const struct dirent **));
```

**Exemple avec tri** :
```c
struct dirent **entries;
int count = scandir(".", &entries, NULL, alphasort);
if (count >= 0) {
    for (int i = 0; i < count; i++) {
        printf("%s\n", entries[i]->d_name);
        free(entries[i]);
    }
    free(entries);
}
```

## 5. Informations sur les fichiers

### stat/lstat
```c
int stat(const char *pathname, struct stat *statbuf);
int lstat(const char *pathname, struct stat *statbuf);
```

**Structure stat importante** :
```c
struct stat {
    mode_t    st_mode;    // Type et permissions
    uid_t     st_uid;     // UID propriétaire
    gid_t     st_gid;     // GID propriétaire
    off_t     st_size;    // Taille en octets
    time_t    st_mtime;   // Date de modification
};
```

**Macros pour st_mode** :
- `S_ISREG(m)` : Fichier régulier
- `S_ISDIR(m)` : Répertoire
- `S_ISLNK(m)` : Lien symbolique

**Exemple** :
```c
struct stat sb;
if (lstat("file.txt", &sb) == 0) {
    printf("Type: %c\n", 
        S_ISREG(sb.st_mode) ? 'f' :
        S_ISDIR(sb.st_mode) ? 'd' :
        S_ISLNK(sb.st_mode) ? 'l' : '?');
    printf("Taille: %ld octets\n", sb.st_size);
    printf("Modifié: %s", ctime(&sb.st_mtime));
}
```

### readlink
```c
ssize_t readlink(const char *pathname, char *buf, size_t bufsiz);
```

**Exemple** :
```c
char buf[1024];
ssize_t len = readlink("lien.txt", buf, sizeof(buf) - 1);
if (len != -1) {
    buf[len] = '\0';  // readlink ne termine pas par \0
    printf("Lien vers: %s\n", buf);
}
```

## 6. Gestion des utilisateurs et groupes

### getpwuid/getgrgid
```c
struct passwd *getpwuid(uid_t uid);
struct group *getgrgid(gid_t gid);
```

**Exemple** :
```c
struct stat sb;
if (stat("file.txt", &sb) == 0) {
    struct passwd *pw = getpwuid(sb.st_uid);
    struct group *gr = getgrgid(sb.st_gid);
    printf("Propriétaire: %s\n", pw ? pw->pw_name : "?");
    printf("Groupe: %s\n", gr ? gr->gr_name : "?");
}
```

## 7. Gestion des arguments

### getopt
```c
int getopt(int argc, char * const argv[], const char *optstring);
```

**Format optstring** :
- "abc" : options -a, -b, -c sans argument
- "a:bc" : -a avec argument obligatoire, -b et -c sans
- "a::bc" : -a avec argument optionnel

**Exemple complet** :
```c
#include <unistd.h>
#include <stdio.h>

int main(int argc, char *argv[]) {
    int aflag = 0;
    char *svalue = NULL;
    int opt;

    // -a sans arg, -s avec arg obligatoire
    while ((opt = getopt(argc, argv, "as:")) != -1) {
        switch (opt) {
        case 'a':
            aflag = 1;
            break;
        case 's':
            svalue = optarg;
            break;
        case '?':
            // Option inconnue ou argument manquant
            return 1;
        }
    }

    printf("Option a: %s\n", aflag ? "oui" : "non");
    if (svalue)
        printf("Valeur de s: %s\n", svalue);

    // Traitement des arguments non-option
    for (int i = optind; i < argc; i++) {
        printf("Argument: %s\n", argv[i]);
    }
    return 0;
}
```

## Exemples de cas d'utilisation typiques

### Copie de fichier avec buffer
```c
#define BUFFER_SIZE 4096

int copy_file(const char *src, const char *dst) {
    char buffer[BUFFER_SIZE];
    int fd_src, fd_dst;
    ssize_t nread;

    fd_src = open(src, O_RDONLY);
    if (fd_src == -1) return -1;

    fd_dst = open(dst, O_WRONLY | O_CREAT | O_TRUNC, 0644);
    if (fd_dst == -1) {
        close(fd_src);
        return -1;
    }

    while ((nread = read(fd_src, buffer, BUFFER_SIZE)) > 0) {
        ssize_t written = 0;
        while (written < nread) {
            ssize_t nwrite = write(fd_dst, buffer + written, nread - written);
            if (nwrite == -1) {
                close(fd_src);
                close(fd_dst);
                return -1;
            }
            written += nwrite;
        }
    }

    close(fd_src);
    close(fd_dst);
    return 0;
}
```

### Listage récursif de répertoire
```c
void list_directory(const char *path, int recursive) {
    DIR *dir = opendir(path);
    if (!dir) return;

    struct dirent *entry;
    while ((entry = readdir(dir)) != NULL) {
        if (strcmp(entry->d_name, ".") == 0 || 
            strcmp(entry->d_name, "..") == 0)
            continue;

        char full_path[PATH_MAX];
        snprintf(full_path, PATH_MAX, "%s/%s", path, entry->d_name);

        struct stat sb;
        if (lstat(full_path, &sb) == 0) {
            printf("%s", full_path);
            if (S_ISLNK(sb.st_mode)) {
                char link_target[PATH_MAX];
                ssize_t len = readlink(full_path, link_target, 
                                     sizeof(link_target) - 1);
                if (len != -1) {
                    link_target[len] = '\0';
                    printf(" -> %s", link_target);
                }
            }
            printf("\n");

            if (recursive && S_ISDIR(sb.st_mode)) {
                list_directory(full_path, 1);
            }
        }
    }
    closedir(dir);
}
```

## Résumé des bibliothèques nécessaires

```c
#include <stdio.h>      // printf, scanf, fgets, etc.
#include <stdlib.h>     // exit, malloc, etc.
#include <unistd.h>     // read, write, getopt
#include <fcntl.h>      // open
#include <sys/stat.h>   // stat, lstat
#include <sys/types.h>  // types basiques
#include <dirent.h>     // opendir, readdir
#include <pwd.h>        // getpwuid
#include <grp.h>        // getgrgid
#include <time.h>       // ctime
#include <string.h>     // strcpy, strcmp, etc.
#include <limits.h>     // PATH_MAX
```
