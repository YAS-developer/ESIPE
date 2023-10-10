

SECTION .data
debut : db "Le deuxieme TP", 10
fin : db "est presque termine !", 10


SECTION .text            
global main              
main:                   
    mov edx, 15     	; nombre de caracteres a lire     
    mov ecx, debut  	; pointe vers la premiere adresse de debut, cad 'L'   
                         
    mov ebx, 1		; sortie standard     
                         
    mov eax, 4    	; write     
                         
    int 0x80                                    
    
    mov edx, 22        
    mov ecx, fin         
    mov ebx, 1           
    mov eax, 4           
    
    int 0x80            
    
    mov ebx, 0           
    mov eax, 1           
    int 0x80             
