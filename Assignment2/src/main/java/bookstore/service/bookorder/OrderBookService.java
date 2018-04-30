package bookstore.service.bookorder;

import bookstore.model.Book;
import bookstore.model.OrderBook;
import bookstore.model.validation.Notification;

import java.util.List;

public interface OrderBookService {
    OrderBook findById(long id);

    Notification<Boolean> saveOrder(long bookId,int quantity);

    List<OrderBook> findall();

   Notification<Boolean> processOrder(long id);

}
