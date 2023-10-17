%include "asm_io.inc"

SECTION .data
    message : db 'l assembleur',10, 0

SECTION .text
global main

main :

push message
call print_string2


mov eax, 1
mov ebx, 0
int 0x80

print_string2 :
    push ebp
    mov ebp, esp
    push eax
    push ebx
    push ecx
    push edx

    mov ecx, [ebp+8] 
    mov edx, ecx

    dec edx
    loop_tc:
    inc edx
    cmp byte [edx],0
    jne loop_tc
    sub edx, [ebp+8]    
    
    mov eax, 4
    mov ebx, 1

    int 0x80

    pop edx
    pop ecx
    pop ebx
    pop eax
    pop ebp
    ret



    
