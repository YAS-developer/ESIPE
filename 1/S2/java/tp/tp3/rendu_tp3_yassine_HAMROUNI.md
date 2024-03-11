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

## Exercice 2 - Liberté, Égalité, toString

### 1- Qu'affiche le code ci-dessous ?

```java
  var b1 = new Book("Da Java Code", "Duke Brown");
  var b2 = b1;
  var b3 = new Book("Da Java Code", "Duke Brown");

  System.out.println(b1 == b2);
  System.out.println(b1 == b3);
```
#### System.out.println(b1 == b2); affiche true, parce que b1 et b2 pointent vers la même instance en mémoire. L'opérateur == compare les références (les adresses mémoire) des objets, et dans ce cas, les références sont identiques.
#### System.out.println(b1 == b3); affiche false, parce que même si b1 et b3 représentent des objets avec les mêmes valeurs de champs (titre et auteur), ils sont deux instances distinctes situées à des adresses mémoire différentes. L'opérateur == compare les références, pas le contenu des objets, donc cette comparaison renvoie false.

### 2- Comment faire pour tester si deux objets ont le même contenu ?

```java
  var b1 = new Book("Da Java Code", "Duke Brown");
  var b2 = b1;
  var b3 = new Book("Da Java Code", "Duke Brown");

  System.out.println(b1.equals(b2));
  System.out.println(b1.equals(b3));
```

### 3- Écrire une méthode isFromTheSameAuthor() qui renvoie vrai si deux livres sont du même auteur.
### Et vérifier avec les deux livres suivants : 

```java
  var book1 = new Book("Da Vinci Code", "Dan Brown");
  var book2 = new Book("Angels & Demons", new String("Dan Brown"));
```

#### Réponse:

```java 
  public boolean isFromTheSameAuthor(Book b){
    return this.author.equals(b.author);
  }
```

### 4 - Comment faire pour que le code suivant

```java
  var javaBook = new Book("Da Java Code", "Duke Brown");
  System.out.println(javaBook);
```

### Affiche
####  Da Java Code by Duke Brown

### 5- Utiliser l'annotation @Override (java.lang.Override) sur la méthode ajoutée à Book.

### 6- A quoi sert l'annotation @Override ?

#### Réponse 4, 5, 6: Pour afficher cela, Il faut réecrire avec la notation @Override la méthode toString présent sur tout Object, qui retourne par défaut, l'adresse de l'objet.

```java
  @Override
  public String toString(){
    return this.title+" by "+this.author;
  }
```
## Exercice 3 - Liberté, equals, Fraternité

```java
public class Book2 {
  private final String title;
  private final String author;

  public Book2(String title, String author) {
    this.title = title;
    this.author = author;
  }

  public static void main(String[] args) {
    var book1 = new Book2("Da Vinci Code", "Dan Brown");
    var book2 = new Book2("Da Vinci Code", "Dan Brown");
    System.out.println(book1.equals(book2));
  }
}
```

### 1- Quel est le problème ?

#### Le problème avec le code présenté est que la méthode equals par défaut de la classe Object est utilisée pour comparer les instances de Book2. Cette méthode compare les références (adresses mémoire) des objets, et non pas leur contenu. Puisque book1 et book2 sont deux instances distinctes (même si elles ont les mêmes valeurs pour title et author), la comparaison avec equals retournera false. Ce n'est pas le comportement attendu, car nous voulons comparer les objets basés sur le contenu de leurs attributs, et non pas sur leurs références.

### 2- Comment corriger le problème si on s'entête à utiliser une classe ? 

#### Nous devons surcharger (override) la méthode equals dans notre classe Book2. En surchargeant cette méthode, nous pouvons définir notre propre logique de comparaison qui vérifiera l'égalité des titres et des auteurs des deux livres.

```java
@Override
  public boolean equals(Object obj) {
    if (obj instanceof Book2) {
      Book2 otherBook = (Book2) obj;
      return this.title.equals(otherBook.title) && this.author.equals(otherBook.author);
    }
    return false;
  }
```

## Exercice 4

### 1- Écrire une méthode swap qui échange les valeurs de deux cases d'un tableau : 






