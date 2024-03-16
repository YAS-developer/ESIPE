#include <stdio.h>
#include <stdlib.h>

void print_info_zone(void *adr) {
    size_t *info_adr = (size_t *)adr;
    printf("Zone at address: %p\n", adr);
    printf("%lu\n", info_adr[-1]);
    printf("%lu\n", info_adr[-2]);
}

int main() {
    int *p = malloc(sizeof(int) * 10);
    if (p == NULL) {
        fprintf(stderr, "Memory allocation failed\n");
        return 1;   
    }

    print_info_zone(p);

    free(p);
    return 0;
}
