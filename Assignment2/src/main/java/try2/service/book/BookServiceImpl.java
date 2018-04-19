package try2.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import try2.model.Book;
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
    public void update(long id, String name, String author, String genre, int quantity, int price) {
        Book book = (Book) bookRepository.findById(id);
        book.setTitle(name);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setQuantity(quantity);
        book.setPrice(price);
        bookRepository.save(book);
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

    public Book findById(long id){
        return bookRepository.findById(id);
    }

    public List<Book> findByQuantity(int quantity){
        return bookRepository.findByQuantity(quantity);
    }



}
