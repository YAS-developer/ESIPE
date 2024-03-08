public class Main{
  public static void main(String[] args){
    var originalBook = new Book("J.K. Rowling", "Harry Potter");
    var newBook = originalBook.withTitle("Harry Potter and the Chamber of Secrets");

    System.out.println("Original: " + originalBook.title());
    System.out.println("New: " + newBook.title());
  }
}