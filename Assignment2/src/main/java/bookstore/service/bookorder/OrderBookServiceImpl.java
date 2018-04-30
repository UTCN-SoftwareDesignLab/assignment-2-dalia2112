package bookstore.service.bookorder;

import bookstore.model.builder.BookBuilder;
import bookstore.model.builder.OrderBuilder;
import bookstore.model.validation.Notification;
import bookstore.repository.BookRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bookstore.model.Book;
import bookstore.model.OrderBook;
import bookstore.repository.OrderBookRepository;
import bookstore.service.book.BookService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class OrderBookServiceImpl implements OrderBookService {

    @Autowired
    private OrderBookRepository orderBookRepository;
    @Autowired
    private BookRepository bookRepository;

    public OrderBookServiceImpl(OrderBookRepository orderBookRepository) {
        this.orderBookRepository = orderBookRepository;
    }

    @Override
    public OrderBook findById(long id) {
        return orderBookRepository.findById(id);
    }

    @Override
    public Notification<Boolean> saveOrder(long bookId, int quantity) {
        Notification<Boolean> notification = new Notification<>();
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            notification.addError("Book does not exist");
            notification.setResult(false);
        }
        if (quantity < 0) {
            notification.addError("Quantity must be a positive integer");
            notification.setResult(false);
        }
        if (notification.hasErrors()) {
            return notification;
        }
        OrderBook orderBook = new OrderBuilder()
                .setBook(book)
                .setQuantity(quantity)
                .build();
        orderBookRepository.save(orderBook);
        notification.setResult(true);
        return notification;
    }

    public List<OrderBook> findall() {
        return orderBookRepository.findAll();
    }

    public Notification<Boolean> processOrder(long id) {
        Notification<Boolean> notification= new Notification<>();
        OrderBook orderBook=orderBookRepository.findById(id);
        if(orderBook==null){
            notification.addError("Order does not exist!");
            notification.setResult(false);
        }
        if(orderBook.getQuantity()-orderBook.getBook().getQuantity()<0){
            notification.addError("Not enought stock!");
            notification.setResult(false);
        }
        if(notification.hasErrors()){return notification;}
        Book oldBook=orderBook.getBook();
        oldBook.setQuantity(oldBook.getQuantity()-orderBook.getQuantity());
        orderBookRepository.deleteById(id);
        notification.setResult(true);
        return notification;
    }

}
