%include "asm_io.inc"

section .bss
    input resb 256     
    number_counts resd 51     
    sorted_numbers resd 51   
    non_saisis_count resd 1  

section .text
global main

main:
    mov edi, input  

loop:
    call read_int
    cmp eax, -1
    je input_fin

    cmp eax, 0
    jl loop
    cmp eax, 50
    jg loop
    
    mov edx, eax
    mov eax, [number_counts + edx * 4]
    inc eax
    mov [number_counts + edx * 4], eax
    jmp loop

input_fin:
    mov ecx, 0
    mov edi, sorted_numbers
    mov eax, 0

fill_sorted_numbers:
    cmp ecx, 51
    je print_sorted_numbers
    mov eax, [number_counts + ecx * 4]
    test eax, eax
    jz non_saisi
    inc ecx
    jmp fill_sorted_numbers

non_saisi:
    mov [edi], ecx
    add edi, 4
    inc ecx
    inc dword [non_saisis_count]
    jmp fill_sorted_numbers

print_sorted_numbers:
    mov ecx, [non_saisis_count]
    mov edi, sorted_numbers

print_numbers_loop:
    dec ecx
    jz fin
    mov eax, [edi]
    call print_int
    call print_espace
    add edi, 4
    jmp print_numbers_loop

fin:
    call print_nl
    mov eax, 1
    mov ebx, 0
    int 0x80
