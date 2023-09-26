section .data
    hello db 'Hello, World!',0

section .text
    global _start

_start:
    ; Écrit "Hello, World!" sur la sortie standard (stdout)
    mov eax, 4            ; Code de l'appel système pour écrire
    mov ebx, 1            ; Descripteur de fichier (stdout)
    mov ecx, hello        ; Pointeur vers la chaîne à écrire
    mov edx, 13           ; Longueur de la chaîne
    int 0x80              ; Appel système

    ; Terminaison du programme
    mov eax, 1            ; Code de l'appel système pour exit
    mov ebx, 0            ; Code de retour (0 pour succès)
    int 0x80              ; Appel système

