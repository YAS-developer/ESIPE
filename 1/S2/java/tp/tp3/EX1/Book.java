import java.util.Objects;

public record Book(String title, String author) {
  public Book {
    Objects.requireNonNull(title, "Le titre ne doit pas être null.");
    Objects.requireNonNull(author, "L'auteur ne doit pas être null.");
  }

  public Book(String title) {
    this(title, "<no author>");
    Objects.requireNonNull(title, "Le titre ne doit pas être null."); // Cette ligne est en fait redondante ici, car la vérification sera déjà effectuée dans le constructeur canonique.
  }

  public Book withTitle(String newTitle) {
    return new Book(this.author, newTitle);
  }


  public boolean isFromTheSameAuthor(Book b){
    return this.author.equals(b.author);
  }

  @Override
  public String toString(){
    return this.title+" by "+this.author;
  }
}