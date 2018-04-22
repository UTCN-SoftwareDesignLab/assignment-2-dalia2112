package try2.service.bookorder;

import try2.model.Book;
import try2.model.OrderBook;

import java.util.List;

public interface OrderBookService {
    OrderBook findById(long id);

    void save(OrderBook orderBook);

    List<OrderBook> findall();

   void deleteOrder(long id);

}
