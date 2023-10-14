%include "asm_io.inc"

SECTION .data  
prompt : db "Entrer un nombre superieur a 0 : ",0
prompt2 : db " ",0

SECTION .bss
nb : resd 1


SECTION .text

global main


main :
	mov eax, prompt
	call print_string
	call read_int
	mov [nb], eax
	mov ebx, 0

	loop :
		inc ebx

		mov eax, [nb]
		mov edx, 0

		cmp ebx, [nb]
 		jg end_loop

		div ebx

		cmp edx, 0
		jne loop

		mov eax, ebx
		call print_int
		mov eax, prompt2
		call print_string

		jmp loop	
	

	end_loop:
	call print_nl
	mov ebx, 0
	mov eax, 1
	int 0x80




