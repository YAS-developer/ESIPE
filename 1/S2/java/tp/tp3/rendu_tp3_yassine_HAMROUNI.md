# TP3 Java Yassine Hamrouni

## Exercice 1 - Livre

### 1- Déclarer un record Book avec les composants title et author. 

```java
public record Book(author, title){}
```


### 2- Puis essayer le code suivant dans une méthode main du record Book.

```java
  var book = new Book("Da Vinci Code", "Dan Brown");
  System.out.println(book.title+' '+book.author);
```

#### Ce code ci-dessus permet de créer à partir du record Book, un objet Book nommé "book". book.title sert à accéder au titre de l'objet book, idem pour book.title.

### 3- Créer une classe Main (dans un fichier Main.java) et déplacer le main de la classe Book dans la classe Main.

```java
public class Main{
  public static void main(String[] args){
      var book = new Book("Da Vinci Code", "Dan Brown");
      System.out.println(book.author+" "+book.title);
  }
}
```


### Quel est le problème ? Comment peut-on le corriger ? 

#### Le problème rencontré est le fait que les attributs de l'objet book sont privés. Pour y remédier, il suffit de prendre les getters définit par défaut dans un Record.

```java
  System.out.println(book.author()+" "+book.title());
```

### 4- On peut remarquer que le code permet de créer des livres ayant un titre ou un auteur null. 
### Comment faire pour éviter ce problème sachant qu'il existe une méthode static requireNonNull dans la classe java.util.Objects.


```java
   Objects.requireNonNull(author, "L'auteur ne doit pas être null.");
    Objects.requireNonNull(title, "Le titre ne doit pas être null.");
```

### 5- Commenter le code précédent et utiliser un constructor compact à la place.

```java
public Book(){
  Objects.requireNonNull(author, "L'auteur ne doit pas être null.");
  Objects.requireNonNull(title, "Le titre ne doit pas être null.");
}
```

### 6- Écrire un autre constructeur qui prend juste un titre et pas d'auteur et ajouter un code de test dans le main.
### On initialisera le champ author avec "<no author>" dans ce cas. 

```java
public Book(String title) {
    this("<no author>", title);
    Objects.requireNonNull(title, "Le titre ne doit pas être null.");
}
```


### 7- Comment le compilateur fait-il pour savoir quel constructeur appeler ? 

#### Quand on crée un objet en Java et on dit new, le compilateur regarde les infos qu'on a mis entre les parenthèses pour décider quel constructeur on veut utiliser. Si on donne juste un titre de livre, Java va chercher un constructeur dans la classe qui attend un titre.


### 8- On souhaite maintenant pouvoir changer le titre d'un livre déjà existant en utilisant une méthode nommée withTitle qui prend en paramètre le nouveau titre.
### Pourquoi le code suivant ne marche pas ? 

#### Car on n'est pas censé modifier les attributs d'un record.


### Comment faire alors ?

```java 
public static void main(String[] args){
  var originalBook = new Book("J.K. Rowling", "Harry Potter");
  var newBook = originalBook.withTitle("Harry Potter and the Chamber of Secrets");

  System.out.println("Original: " + originalBook.title());
  System.out.println("New: " + newBook.title());
}
```

