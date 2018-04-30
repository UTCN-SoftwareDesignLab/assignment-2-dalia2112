import bookstore.model.Book;
import bookstore.model.OrderBook;
import bookstore.model.User;
import bookstore.model.builder.BookBuilder;
import bookstore.model.builder.UserBuilder;
import bookstore.repository.BookRepository;
import bookstore.repository.OrderBookRepository;
import bookstore.repository.UserRepository;
import bookstore.service.bookorder.OrderBookService;
import bookstore.service.bookorder.OrderBookServiceImpl;
import bookstore.service.user.UserService;
import bookstore.service.user.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration
public class OrderServiceImplTest {


    @Mock
    OrderBookRepository orderRepository;

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    OrderBookService orderService = new OrderBookServiceImpl(orderRepository);


    OrderBook orderBook;
    Book book;

    @Before
    public void init() {
        book = new BookBuilder()
                .setTitle("Fram")
                .setAuthor("AuthorTest")
                .setGenre("SF")
                .setQuantity(2)
                .setPrice(10)
                .build();
        orderBook = new OrderBook();
        orderBook.setBook(book);
        orderBook.setQuantity(2);
    }

    @Test
    public void addOrder() {
        when(bookRepository.findById(1)).thenReturn(book);
        Assert.assertTrue(orderService.saveOrder(1,2).getResult());
    }

    @Test
    public void processOrder(){
        long id=1;
        when(orderService.findById(id)).thenReturn(orderBook);
        Assert.assertTrue(orderService.processOrder(id).getResult());
    }

}