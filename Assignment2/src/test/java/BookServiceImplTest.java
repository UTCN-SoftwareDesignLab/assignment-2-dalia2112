import bookstore.model.Book;
import bookstore.model.builder.BookBuilder;
import bookstore.repository.BookRepository;
import bookstore.service.book.BookService;
import bookstore.service.book.BookServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration
public class BookServiceImplTest {


    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService = new BookServiceImpl(bookRepository);


    List<Book> testBooks;

    @Before
    public void init() {
        testBooks = new ArrayList<>();

        Book book = new BookBuilder()
                .setTitle("Fram")
                .setAuthor("AuthorTest")
                .setGenre("SF")
                .setQuantity(0)
                .setPrice(10)
                .build();

        testBooks.add(book);
    }

    @Test
    public void addBook() {
        Assert.assertTrue(bookService.addBook(testBooks.get(0)).getResult());
    }

    @Test
    public void updateBook() {

        long id = 1;
        int newQuantity = 200;
        when(bookRepository.findById(id)).thenReturn(testBooks.get(0));
        Book book = bookRepository.findById(id);
        Assert.assertTrue(bookService.updateBook(id, book.getTitle(), book.getAuthor(), book.getGenre(), newQuantity, book.getPrice()).getResult());
    }

    @Test
    public void deleteBook() {
        long id = 1;
        when(bookRepository.findById(id)).thenReturn(testBooks.get(0));
        Assert.assertTrue(bookService.deleteBook(id).getResult());
    }

    @Test
    public void findByTitle() {
        when(bookRepository.findByTitle("Fram")).thenReturn(testBooks);
        Assert.assertEquals("Fram", bookRepository.findByTitle("Fram").get(0).getTitle());
    }

    @Test
    public void findByAuthor() {
        when(bookRepository.findByAuthor("AuthorTest")).thenReturn(testBooks);
        Assert.assertEquals("AuthorTest", bookRepository.findByAuthor("AuthorTest").get(0).getAuthor());
    }

    @Test
    public void findByQuantity() {
        when(bookRepository.findByQuantity(0)).thenReturn(testBooks);
        Assert.assertTrue(bookService.findByQuantity(0) != null);
    }


}