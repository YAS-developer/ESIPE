%include "asm_io.inc"

SECTION .data
prompt1 : db "Entrer un nombre : ", 0
prompt2 : db "Un autre nombre : ", 0
outmsg1 : db "La somme est ", 0

SECTION .bss
input1 : resd 1
input2 : resd 1

SECTION .text
global  main
main :
    mov eax , 0 xFFFFFFFF
2 cmp eax , 0
3 jg aff_1
4 mov eax , 0
5 call print_int
6 aff_1 :
7 mov eax , 1
8 call print_int
    mov ebx, 0
    mov eax, 1
    int 0x80