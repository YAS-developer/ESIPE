%include "asm_io.inc"

section .text
global main
main:
    call read_int

    mov ecx, 32
    mov ebx, eax
print_binary_loop:
    shl ebx, 1
    jnc bit_zero
    mov eax, 1

bit_zero:
    mov eax, 0

print_char:
    movzx eax, dl
    call print_char
    loop print_binary_loop

    call print_nl

    mov eax, 1
    int 0x80