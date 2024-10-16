#include <stdio.h>

int main(){

    int c;

    setbuf(stdin, NULL);
    setbuf(stdout, NULL);

    while((c = fgetc(stdin)) != EOF){
        fputc(c, stdout); 
    }
    
}