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
        this.books = new HashMap<>();
    }

    public void add(Book book) {
        books.put(book.title(), book);
    }

    public Book findByTitle(String title) {
        return books.get(title);
    }


// @Override
//	public String toString() {
//		String str = new String();
//		for(Book b: books) {
//			str += b+"\n";
//		}
//		return str;
//	}
}
