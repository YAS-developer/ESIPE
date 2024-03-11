public class Book2 {
  private final String title;
  private final String author;

  public Book2(String title, String author) {
    this.title = title;
    this.author = author;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Book2) {
      Book2 otherBook = (Book2) obj;
      return this.title.equals(otherBook.title) && this.author.equals(otherBook.author);
    }
    return false;
  }

  @Override
  public int hashCode(){
    return this.title.hashCode() ^ this.author.hashCode();
  }


  public static void main(String[] args) {
    var book1 = new Book2("Da Vinci Code", "Dan Brown");
    var book2 = new Book2("Da Vinci Code", "Dan Brown");
    System.out.println(book1.equals(book2));
  }
}