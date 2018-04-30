package bookstore.service.book;

import org.aspectj.weaver.ast.Not;
import bookstore.model.Book;
import bookstore.model.validation.Notification;

import java.util.List;

public interface BookService {
//    void save(Book book);

    List<Book> findall();

    Book findById(long id);

    Notification<Boolean> updateBook(long id, String name, String author, String genre, int quantity, int price);

    Notification<Boolean> deleteBook(long id);

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByGenre(String genre);

    List<Book> findByQuantity(int quantity);

    Notification<Boolean> addBook(Book book);

}
