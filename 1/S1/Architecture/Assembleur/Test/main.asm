section .data
    a dd 10
    b dd 15
    msg1 db 'ECX superieur a EBX'
    msg2 db 'ECX inferieur a EBX'

section .text
    global _start

_start:
    mov eax, [a]
    mov ebx, [b]

    cmp eax, ebx
    
    jl end_if
    
    mov eax, 4
    mov ebx, 1
    mov ecx, msg1
    mov edx, 20
    int 0x80

    end_if:
        mov eax, 4
        mov ebx, 1
        mov ecx, msg2
        mov edx, 20
        int 0x80