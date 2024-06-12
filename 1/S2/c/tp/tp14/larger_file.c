#include <stdio.h>
#include <stdlib.h>
#include <ftw.h>
#include <sys/stat.h>
#include <string.h>
#include <limits.h>   
#include <stdint.h>  

#define MAX_FILES 10

typedef struct {
    char path[PATH_MAX];
    off_t size;
} FileInfo;

FileInfo largest_files[MAX_FILES];
int file_count = 0;

void insert_file(const char *path, off_t size) {
    int i;
    for (i = 0; i < file_count; ++i) {
        if (size > largest_files[i].size) {
            break;
        }
    }

    if (i < MAX_FILES) {
        if (file_count < MAX_FILES) {
            ++file_count;
        }
        for (int j = file_count - 1; j > i; --j) {
            largest_files[j] = largest_files[j - 1];
        }
        strncpy(largest_files[i].path, path, PATH_MAX - 1);
        largest_files[i].path[PATH_MAX - 1] = '\0';
        largest_files[i].size = size;
    }
}

int file_callback(const char *fpath, const struct stat *sb, int typeflag) {
    if (typeflag == FTW_F) {
        insert_file(fpath, sb->st_size);
    }
    return 0;
}

void print_largest_files() {
    for (int i = 0; i < file_count; ++i) {
        printf("%s : %jd octets\n", largest_files[i].path, (intmax_t)largest_files[i].size);
    }
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <directory>\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    const char *dirpath = argv[1];

    if (ftw(dirpath, file_callback, 20) == -1) {
        perror("ftw");
        exit(EXIT_FAILURE);
    }

    print_largest_files();

    return 0;
}
