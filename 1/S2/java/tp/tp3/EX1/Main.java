public class Main{
  public static void main(String[] args){
    // var originalBook = new Book("J.K. Rowling", "Harry Potter");
    // var newBook = originalBook.withTitle("Harry Potter and the Chamber of Secrets");

    // System.out.println("Original: " + originalBook.title());
    // System.out.println("New: " + newBook.title());


    var b1 = new Book("Da Java Code", "Duke Brown");
    var b2 = b1;
    var b3 = new Book("Da Java Code", "Duke Brown2232");

    System.out.println(b1.isFromTheSameAuthor(b3));
    // System.out.println(b1.equals(b3));
  }
}