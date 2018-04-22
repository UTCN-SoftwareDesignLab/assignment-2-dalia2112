package try2.service.book;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import try2.model.Book;
import try2.model.validation.BookValidator;
import try2.model.validation.Notification;
import try2.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> findall() {
        final Iterable<Book> items = bookRepository.findAll();
        List<Book> result = new ArrayList<>();
        items.forEach(result::add);
        return result;
    }

    @Override
    public Notification<Boolean> update(long id, String name, String author, String genre, int quantity, int price) {
        Notification<Boolean> notification = new Notification<>();

        Book book = (Book) bookRepository.findById(id);
        if(book==null){
            notification.addError("Book does not exist!");
            notification.setResult(false);
            return notification;
        }
        book.setTitle(name);
        book.setAuthor(author);
        book.setGenre(genre);
//        if (!validateNr(quantity)) {
//            notification.addError("Invalid quantity!");
//            notification.setResult(false);
//        }
//        if (!validateNr(price)) {
//            notification.addError("Invalid quantity!");
//            notification.setResult(false);
//        }
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
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
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

    private boolean validateNr(String nr) {
        return nr.chars().allMatch(Character::isDigit);
    }

}
