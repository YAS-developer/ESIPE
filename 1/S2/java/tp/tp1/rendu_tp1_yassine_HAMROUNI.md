# TP1 Java Yassine Hamrouni

## Exercice 2 

### 1- Que se passe-t-il si l'on ne passe pas d'argument lors de l'exécution du programme ?

#### réponse: Le code s'exécute mais rien ne s'affiche. Cependant dans mon cas j'ai renvoyé une erreur lorsque la taille de args est égale à 0.


### 2- Écrire une boucle affichant le contenu du tableau, sachant qu'en Java, les tableaux possèdent un champ (un attribut) length qui correspond à la taille du tableau.

```java
for(int i=0; i<args.length; i++){
      System.out.println(args[i]);
}
```

3-Changer votre programme pour utiliser la syntaxe dite du 'for deux points', c'est à dire for(Type value: array)

```java
for(var arg: args){
      System.out.println(arg);
}
```

## Exercice 3

### 2- scanner de Type Scanner (Objet), value de type entier.
### Modifier le programme pour déclarer et initialiser les variables en une seule ligne.

```java
Scanner scanner = new Scanner(System.in);
int value = scanner.nextInt(); 
```

### 3-  Pourquoi nextInt() n'est pas une fonction ? Qu'est nextInt() alors ?

#### nextInt est une méthode qui appartient à l'objet Scanner.


### 4- Expliquer la ligne : import java.util.Scanner;

#### Cette ligne signifie que l'on va importer L'objet Scanner qui est situé dans le package java.util

### 5- Modifier le programme pour qu'il demande deux entiers et affiche la somme de ceux-ci.

```java
int value = scanner.nextInt(); 
int value2 = scanner.nextInt();
System.out.println("Somme: "+(value+value2));
```
## Exercice 4


### 1- Quelle doit être la ligne de commande pour compiler le fichier Point.java ?

#### javac Point.java

### 3- Que veut dire "static" pour une méthode ?

#### C'est une fonction qui ne s'applique pas sur une instance

### 4- Que se passe-t-il lorsque l'un des arguments n'est pas un nombre ?

#### Cela renvoie une erreur. J'ai pu catch l'erreur est renvoyé une exception en indiquant un message.

### 5-Dans le main, ajouter des instructions pour créer un instance du record Point, avec le deux entiers x et y et afficher celui-ci.

```java
var point = new Point(x, y);
System.out.println("x: "+point.x()+" y: "+point.y());
```


### 6- Quels sont les paramètres et le type de retour de la méthode distance ? 

#### Le type de retour de la méthode dimension est un double, Le paramétre est un Point.

## Exercice 5

### Comment peut-on expliquer la différence de vitesse ?

#### C:

#### real    0m0,084s
#### user    0m0,067s
#### sys     0m0,022s

#### java:

#### real    0m0,087s
#### user    0m0,048s
#### sys     0m0,041s

#### Pour comprendre les différences de performances entre un programme écrit en C et son équivalent en Java, il est important de reconnaître les mécanismes sous-jacents à l'exécution de ces deux langages.

#### Le programme C est compilé directement en code machine spécifique à la plateforme sur laquelle il s'exécute. Cela signifie que le code exécutable produit par le compilateur C (comme gcc) est optimisé pour l'architecture cible et s'exécute directement sur le matériel, sans intermédiaire, ce qui conduit généralement à une exécution très rapide.

#### Java, en revanche, fonctionne différemment. Le code Java est compilé en bytecode, qui est une représentation intermédiaire, par le compilateur Java (javac). Ce bytecode est ensuite exécuté sur la machine virtuelle Java (JVM), qui agit comme un intermédiaire entre le bytecode et le matériel. La JVM utilise un compilateur Just-In-Time (JIT) pour compiler le bytecode en code machine spécifique à la plateforme au moment de l'exécution. Le JIT peut optimiser le bytecode de manière dynamique en fonction de l'exécution du programme, ce qui peut entraîner une exécution très rapide, parfois comparable à celle du code C.
