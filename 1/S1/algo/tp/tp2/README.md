Bonjour je vais vous expliquer ce que j'ai effectue comme test.


EXERCICE 1:

test_verify_palindrome_rec1: 
    Ici on test le cas ou c'est une chaine vide "". La fonction retourne 1 car elle rentre dans un premier temps dans la condition str[lo] == str[hi] pour ensuite faire un appel recursif. lo > hi, ducoup elle retourne 1.


test_verify_palindrome_rec2: 
    On test le cas ou c'est un palindrome dont la taille de la chaine est pair "abababababbababababa". La fonction retourne 1 car elle compare tout les caracteres jusqu'a attindre la condition lo > hi, ducoup elle retourne 1.


test_verify_palindrome_rec3:
    On test le cas ou c'est un palindrome dont la taille de la chaine est impair "level". La fonction retourne 1 car elle compare tout les caracteres en incrementant lo et decrementant hi. Arrive au caractere 'v', lo et hi seront egaux et donc la condition tr[lo] == str[hi] sera vrai. Ensuite par appel recursif, la 
    lo > hi est vrai, ducoup elle retourne 1. 


test_verify_palindrome_rec4:
    On test le cas ou ce n'est pas un palindrome "ratattbtra". La fonction retourne 0 car apres 3 appel recursif en comparant le caractere 'a' et 'b', la condition est fausse. Alors la fonction renvoie 0.


test_verify_palindrome1:
    On test le cas ou la chaine est vide. La fonction palindrome_rec retourne 1 car elle a en argument li=0 et hi=-1. Alors pas besoin de recursion pour ce cas, la fonction rentre directement dans la condition lo > hi. Ce qui retourne 1.


test_verify_palyndrome2:
    Meme cas que: test_verify_palindrome_rec2

test_verify_palyndrome3:
    Meme cas que: test_verify_palindrome_rec3

test_verify_palyndrome4:
    Meme cas que: test_verify_palindrome_rec4

   
Exercice 3

test_verify_count1:
    On teste le cas ou c'est un tableau vide. Dans un premier la fonction fait une recursion pour ensuite aller dans la condition lo > hi, pour renvoyer 0.

test_verify_count2:
    On teste le cas ou c'est un tableau avec le même entier. Par exemple ici c'est 3, etant donnée qu'on cherche le nombre d'occurences du même entier et que le tableau à seulement cet entier, cela nous renvoie la taille du tableau.


test_verify_count3:
    On teste le cas ou le elt n'est pas dans le tableau d'entier. Cela nous renvoie 0 car elle ne rentre jamais dans la condition
    t[lo] == elt étant donnée que la valeur n'existe pas dans le tableau.


test_verify_count4:
    On teste le cas ou elt est dans le tableau d'entier. A chaque fois que l'une des cases du tableau est égal à elt, on va ajouter 1 et la recursion sinon return . jusqu'à ce que lo > hi, dans ce cas on return 0+les anciens returns. Ce qui va nous donner 2 dans notre example.






















