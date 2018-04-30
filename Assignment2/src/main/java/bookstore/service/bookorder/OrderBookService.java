package bookstore.service.bookorder;

import bookstore.model.Book;
import bookstore.model.OrderBook;

import java.util.List;

public interface OrderBookService {
    OrderBook findById(long id);

    void save(OrderBook orderBook);

    List<OrderBook> findall();

   void deleteOrder(long id);

}
