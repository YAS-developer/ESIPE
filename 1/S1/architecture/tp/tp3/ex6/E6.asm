%include "asm_io.inc"

SECTION .data
prompt1 : db " ",0

SECTION .text
global main

main :
    mov ecx, 0
    mov ebx, 0
loop :
    call read_int
    cmp eax, -1
    je input_done
    push eax
    inc ecx
    jmp loop

input_done :
    cmp ebx, ecx
    je done

    pop eax
    call print_int
    
    mov eax, prompt1
    call print_string 
    
    inc ebx
    jmp input_done
done :
    mov eax, 1
    mov ebx, 0
    int 0x80
