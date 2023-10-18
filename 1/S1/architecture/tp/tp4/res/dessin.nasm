; Taille du canvas
%define WIDTH 300
%define HEIGHT 200

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Global definition to use from C ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
section .bss
frameBuffer: resd WIDTH * HEIGHT
section .data
bufferWidth: dd WIDTH
bufferHeight: dd HEIGHT
frequency: dd 0

section .text
UpdateBuffer:
    call dessin  
    ret

global frameBuffer
global bufferWidth
global bufferHeight
global UpdateBuffer
global frequency

;;;;;;;;;;;;;;;;;;;;;
; Dessin            ;
;;;;;;;;;;;;;;;;;;;;;
section .data
%define RAYON 4
position: dq RAYON, RAYON
vitesse: dq 2, 3
couleur: dd 0Xffffff

section .text

dessin:
    ; place un pixel blanc au centre de la fenêtre
    mov dword [frameBuffer + 4*(HEIGHT * WIDTH / 2 + WIDTH / 2)], 0xffffff

   ;  mov dword [frameBuffer + 4*(60 * 200)], 0xffffff

    mov dword [frameBuffer + 4*(60 * 200)], 0xffffff

    mov rdi, 150
    mov rsi, 200
    call draw_hline  
   ; call plot

   ;  mov dword [frameBuffer + 4*(30 * 200)], 0xffffff
    
    
    ret

; rsi et rdi sont modifiés
plot: ; (x: rdi, y: rsi)
    ; TODO2000000000000031221222232311111112, y: rsi, r: rdx)
    ; TODO

    push rax

    mov rax, rdi
    imul rax, rsi
    imul rax, 4
    add rax, frameBuffer


    mov dword [rax], 0xffffff

    pop rax
    ret
    

; draw_hline(x1,x2,y)
draw_hline:

    push rax
    push rbx
    push rcx
    
    mov rax, rdi
    mov rbx, rsi
    mov rcx, 10
    imul rax, rcx
    imul rax, 4
    add rax, frameBuffer

    loop :
        cmp rax, rbx
        je fin

        mov dword [rax], 0xffffff
        inc rax
        jmp loop
    fin :

        pop rax
        pop rbx
        pop rcx
        ret

; draw_vline(x,y1,y2)
draw_vline:
    ; TODO
    ret

draw_box:
    ; TODO
    ret

clear:
    ; TODO
    ret

move:
    ; TODO
    ret
    

