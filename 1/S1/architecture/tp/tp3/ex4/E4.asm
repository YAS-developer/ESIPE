%include "asm_io.inc"

SECTION .data
prompt1 : db " ",0

SECTION .text
global main

main :

push suite
jmp print_int

suite:
    mov eax, 0

print_int:
    pop eax
    jmp eax



done :
    mov eax, 1
    mov ebx, 0
    int 0x80
