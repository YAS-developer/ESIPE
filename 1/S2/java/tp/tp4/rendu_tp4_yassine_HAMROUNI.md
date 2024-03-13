# TP4 Java Yassine Hamrouni

## Exercice 1 - Eclipse

### 4- Écrire une classe Main qui affiche Hello Eclipse.

```java
package src;

public class Main {
  public static void main(String[] args){
  	System.out.println("Hello");
  }
}
```

### 5-1 Que fait sysout + Ctrl + Space dans un main ?

#### C'est un raccourci pour faire un System.out.println(). 

### 5-2 Que fait toStr + Ctrl + Space dans une classe ?

#### Cela me propose 2 possibilités:
#### Soit d'Override la méthode toString() de la classe ou de créer directement une méthode de classe nommé toStr()

### 5-3 Définir un champs foo de type int, que fait get + Ctrl + Space, et set + Ctrl + Space .

#### Cela me propose de créer un getter nommée getFoo() qui me permet de récupérer mon champ foo. Par contre pour set, cela ne me propose pas un setter nommé 
#### setFoo(int newFoo), ce que je trouve bizarre ?

### 5-4 Dans le menu Source, comment générer un constructeur initialisant le champ foo ?

#### Sur le menu Source, il faut cliquer sur "Generate constructor using field"

```java
public Test(int foo) {
  super();
  this.foo = foo;
}
```

### 5-5 Sélectionner le nom de la classe puis Alt + Shift + R, qu'obtient-on ? Même question avec le champ foo .

#### Permet de modifier le nom de classe sur tout le code et également le nom du fichier, le constructeur. Pour une variable, ca modifie également sur tout le code. 

### 5-6 Écrire a = 2 + 3 + 4, puis sélectionner 2 + 3 puis Alt + Shift + L

#### Cela stocke dans une variable la zone sélectionner.

```java
int i = 2 + 3;
int a = i + 4;
```

### 5-7 Écrire new Integer(2), en gardant le curseur après ')', appuyer sur Ctrl + 1, que se passe-t-il ?

#### Propose de créer une variable local et d'affecter à ma variable courant, la variable local.

```java
Integer integer = new Integer(2);
this.foo = integer;
```
### 5-8 Déclarer une variable s de type String et cliquer sur String en maintenant la touche Ctrl . Que se passe-t-il ?

#### Cela me ramène vers le code source de la classe String.

### 5-9 Dans la méthode toString(), que fait un Ctrl + Clic sur super.toString() ?

#### Ca nous ramène vers le code source de toString().

```java 
public String toString() {
  return getClass().getName() + "@" + Integer.toHexString(hashCode());
}
```
#### Ce code retourne une chaîne de caractères représentant l'objet. Il inclut le nom de la classe de l'objet suivi de son code de hachage en format hexadécimal, séparés par le symbole "@". C'est souvent utilisé pour identifier l'objet lors du débogage.

### 5-10 Sélectionner le champs foo, puis Ctrl + Shift + G. Que se passe-t-il ?
Cela me montre le chemin dans l'arborescence du projet ou est le champ sélectionner en précisant quel est le type de ce dernier.

### 5-11 À quoi sert Ctrl + Shift + O ?

#### Organise les imports de  manière alphabétique et cohérente dans le code courant. Supprime les imports inutilisés et ajoute les imports manquants.

### 5-12 À quoi sert Ctrl + Shift + C ?

#### Commente une ligne ou une partie de code.



## Exercice 2 - Library

### 1- Écrire une classe Library avec un champs books de type ArrayList ainsi qu'un constructeur sans paramètre initialisant le champ books.
### Attention à déclarer les bons modificateurs pour le champ books.

```java
public class Library {
	 private ArrayList<Book> books;

   public Library() {
       this.books = new ArrayList<Book>();
   }
}
```

### 2- Ajouter une méthode add qui permet d'ajouter des books (non null) à la liste de livres.

```java
public void add(Book book) {
  books.add(book);
}
```

### 3- Écrire une méthode findByTitle qui permet de trouver un livre en fonction de son titre dans la bibliothèque. La méthode doit renvoyer null dans le cas où aucun livre n'a le bon titre.

```java
public Book findByTitle(String title) {
   for (Book book : books) {
     if (book.title().equals(title)) {
    	 return book;
     }
   }
   return null; 
 }
```