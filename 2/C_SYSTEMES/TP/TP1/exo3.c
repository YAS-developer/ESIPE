#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char *argv[])
{
    int opt;
    int no_space = 0;
    int no_newline = 0;
    int separator = 0;

    while ((opt = getopt(argc, argv, "sn:")) != -1)
    {
        printf("opt : %c\n", opt);
        switch (opt)
        {
        case 's':
            no_space = 1;
            break;
        case 'n':
            no_newline = 1;
            break;
        case 'S':
            separator = 1;
            break;
        default: 
            fprintf(stderr, "Usage: %s [-t nsecs] [-n] name\n",
                    argv[0]);
            exit(EXIT_FAILURE);
        }
    }

    for (int i = optind; i < argc; i++)
    {
        if(separator){

        }
        printf("%s", argv[i]);
        if(!no_space){
            if(i != argc-1)
                printf(" ");
        }
    }
    if(!no_newline){
        printf("\n");
    }
    
}
