package src;

//import java.util.ArrayList;
//
//public class Library {
// private ArrayList<Book> books;
//
// public Library() {
//   this.books = new ArrayList<Book>();
// }
//
// public void add(Book book) {
//	 books.add(book);
// }
//
// public Book findByTitle(String title) {
//   for (Book book : books) {
//     if (book.title().equals(title)) {
//    	 return book;
//     }
//   }
//   return null; 
// }
// 
 
import java.util.HashMap;
import java.util.Map;

public class Library {
    private Map<String, Book> books;

    public Library() {
        this.books = new HashMap<String, Book>();
    }

    public void add(Book book) {
        books.put(book.title(), book);
    }

    public Book findByTitle(String title) {
        return books.get(title);
    }


// @Override
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		for(Book b: books) {
//			sb.append(b).append("\n");
//		}
//		return sb;
//	}
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Book book : books.values()) {
            sb.append(book.toString()).append("\n");
        }
        return sb.toString();
    }

}
