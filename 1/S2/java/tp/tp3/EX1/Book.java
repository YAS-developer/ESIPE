import java.util.Objects;

public record Book(String title, String author) {
  public Book {
    Objects.requireNonNull(title, "Le titre ne doit pas être null.");
    Objects.requireNonNull(author, "L'auteur ne doit pas être null.");
  }

  public Book(String title) {
    this(title, "<no author>");
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

  @Override
  public int hashCode(){
    return this.title.hashCode() ^ this.author.hashCode();
  }
}