%include "asm_io.inc"

SECTION .data

SECTION .bss

SECTION .text
global main
main :
    call affiche

    mov ebx, 0
    mov eax, 1
    int 0x80

affiche :
    push dword affiche
    mov eax, 0
    call print_int
    call print_nl
    ret

