import java.util.Objects;

public record Book(String author, String title) {
  public Book {
    Objects.requireNonNull(author, "L'auteur ne doit pas être null.");
    Objects.requireNonNull(title, "Le titre ne doit pas être null.");
  }

  public Book(String title) {
    this("<no author>", title);
    Objects.requireNonNull(title, "Le titre ne doit pas être null."); // Cette ligne est en fait redondante ici, car la vérification sera déjà effectuée dans le constructeur canonique.
  }

  public Book withTitle(String newTitle) {
    return new Book(this.author, newTitle);
  }
}