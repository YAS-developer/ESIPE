%include "asm_io.inc"


SECTION .text
global  main
main :
    mov eax, 0xFFFFFFFF
    cmp eax, 0
    jg aff_1
    mov eax, 0
    call print_int

    

aff_1 :
    mov eax, 1
    call print_int


    mov ebx, 0
    mov eax, 1
    int 0x80
