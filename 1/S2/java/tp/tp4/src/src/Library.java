package src;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Library {
    private final Map<String, Book> books;

    public Library() {
        this.books = new LinkedHashMap<String, Book>();
    }

    public void add(Book book) {
        books.put(book.title(), book);
    }

    public Book findByTitle(String title) {
        return books.get(title);
    }
    
    public void removeAllBooksFromAuthor(String author) {
        books.values().removeIf(book -> book.author().equals(author));
    }
    
//    public void removeAllBooksFromAuthor(String author) {
//    	ArrayList<String> titlesToRemove = new ArrayList<>();
//        
//     
//        for (Map.Entry<String, Book> entry : books.entrySet()) {
//            if (entry.getValue().author().equals(author)) {
//                titlesToRemove.add(entry.getKey());
//            }
//        }
//        
//    
//        for (String title : titlesToRemove) {
//            books.remove(title);
//        }
//    }
    
    
    @Override
    public String toString() {
        var sb = new StringBuilder();
        for(var book : books.values()) {
            sb.append(book.toString()).append("\n");
        }
        return sb.toString();
    }
    
// @Override
//	public String toString() {
//		var sb = new StringBuilder();
//		for(var b: books) {
//			sb.append(b).append("\n");
//		}
//		return sb;
//	}
    
   
}
