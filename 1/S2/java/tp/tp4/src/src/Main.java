

public class Main {
	
  public static void main(String[] args){
  //  	var library = new Library();
  //  	var book = new Book("Da Vinci Code", "Da firenze");
  //    library.add(book);
  //    library.add(new Book("Da Vinci Code1212", "Da firenze1213131"));
  //    library.add(new Book("Heae", "Rasc"));
  //    System.out.println(library.toString());
    
    
    var library2 = new Library();
    library2.add(new Book("Da Vinci Code", "Dan Brown"));
    library2.add(new Book("Angels & Demons", "Dan Brown"));
    // System.out.println(library2.toString());
    library2.removeAllBooksFromAuthor("Dan Brown");
    // System.out.println(library2.toString());
  }
}
