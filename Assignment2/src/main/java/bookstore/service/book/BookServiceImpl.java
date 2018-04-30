package bookstore.service.book;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bookstore.model.Book;
import bookstore.model.validation.BookValidator;
import bookstore.model.validation.Notification;
import bookstore.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;


    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

//    @Override
//    public void save(Book book) {
//        bookRepository.save(book);
//    }

    @Override
    public List<Book> findall() {
        final Iterable<Book> items = bookRepository.findAll();
        List<Book> result = new ArrayList<>();
        items.forEach(result::add);
        return result;
    }

    @Override
    public Notification<Boolean> updateBook(long id, String name, String author, String genre, int quantity, int price) {
        Notification<Boolean> notification = new Notification<>();
        if(id<0) {
            notification.addError("Id must be positive!");
            notification.setResult(false);
            return notification;
        }
        Book book = (Book) bookRepository.findById(id);
        if(book==null){
            notification.addError("Book does not exist!");
            notification.setResult(false);
            return notification;
        }
        book.setTitle(name);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setQuantity(quantity);
        book.setPrice(price);

        BookValidator bookValidator = new BookValidator(book);
        bookValidator.validate();
        if (bookValidator.hasErrors()) {
            notification.addError(bookValidator.getFormatedErrors());
            notification.setResult(false);
        } else {
            bookRepository.save(book);
            notification.setResult(true);
        }
        return notification;
    }

    @Override
    public Notification<Boolean> deleteBook(long id) {
        Notification<Boolean> notification = new Notification<>();
        if(id<0) {
            notification.addError("Id must be positive!");
            return notification;
        }
        Book book = (Book) bookRepository.findById(id);
        if(book==null){
            notification.addError("Book does not exist!");
            notification.setResult(false);
            return notification;
        }
        bookRepository.deleteById(id);
        notification.setResult(true);
        return notification;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> findByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    public Book findById(long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findByQuantity(int quantity) {
        return bookRepository.findByQuantity(quantity);
    }

    public Notification<Boolean> addBook(Book book) {
        BookValidator bookValidator = new BookValidator(book);
        bookValidator.validate();
        Notification<Boolean> notification = new Notification<>();
        if (bookValidator.hasErrors()) {
            notification.addError(bookValidator.getFormatedErrors());
            notification.setResult(false);
        } else {
            bookRepository.save(book);
            notification.setResult(true);
        }
        return notification;
    }

}
