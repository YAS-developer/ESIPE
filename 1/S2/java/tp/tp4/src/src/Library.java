package src;

import java.util.ArrayList;

public class Library {
 private ArrayList<Book> books;

 public Library() {
   this.books = new ArrayList<Book>();
 }

 public void add(Book book) {
	 books.add(book);
 }

 public Book findByTitle(String title) {
   for (Book book : books) {
     if (book.title().equals(title)) {
    	 return book;
     }
   }
   return null; 
 }
}
