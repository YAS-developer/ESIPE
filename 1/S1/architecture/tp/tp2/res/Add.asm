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
    mov eax, prompt1
    call print_string     ; Affichage de prompt1.
    call read_int         ; Lecture d'un entier.
    mov [input1], eax
    mov eax, prompt2
    call print_string     ; Affichage de  prompt2.
    call read_int         ; Lecture d'un entier.
    mov [input2], eax
    mov eax, [input1]
    add eax, [input2]
    mov ebx, eax
    mov eax, outmsg1
    call print_string     ; Affichage de outmsg1.
    mov eax, ebx
    call print_int        ; Affichage de ?
    call print_nl         ; Affichage d'une nouvelle ligne.
    mov ebx, 0
    mov eax, 1
    int 0x80
