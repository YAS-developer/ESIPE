%include "asm_io.inc"

section .bss
    input_buffer resb 256     ; Tampon pour stocker l'entrée
    number_counts resd 51     ; Tableau pour compter les occurrences de chaque entier
    sorted_numbers resd 51    ; Tableau pour stocker les entiers non saisis
    non_saisis_count resd 1  ; Compteur pour les entiers non saisis

section .text
global main

main:
    mov edi, input_buffer  ; EDI pointe vers le début du tampon d'entrée

input_loop:
    call read_int
    cmp eax, -1
    je input_done

    ; Vérifier que l'entier est dans la plage [0, 50]
    cmp eax, 0
    jl input_loop
    cmp eax, 50
    jg input_loop

    ; Incrémenter le compteur pour cet entier
    mov edx, eax
    mov eax, [number_counts + edx * 4]
    inc eax
    mov [number_counts + edx * 4], eax
    jmp input_loop

input_done:
    ; Remplir le tableau sorted_numbers avec les entiers non saisis
    mov ecx, 0
    mov edi, sorted_numbers
    mov eax, 0

fill_sorted_numbers:
    cmp ecx, 51
    je print_sorted_numbers  ; Si tous les entiers ont été vérifiés, passez à l'affichage
    mov eax, [number_counts + ecx * 4]
    test eax, eax
    jz not_saisi
    inc ecx
    jmp fill_sorted_numbers
not_saisi:
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
    jz done
    mov eax, [edi]
    call print_int
    call print_espace
    add edi, 4
    jmp print_numbers_loop

done:
    call print_nl

    ; Terminer le programme
    mov eax, 1
    mov ebx, 0
    int 0x80
