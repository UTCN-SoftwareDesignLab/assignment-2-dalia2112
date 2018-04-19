package try2.service.bookorder;

import try2.model.OrderBook;

public interface OrderBookService {
    OrderBook findById(long id);
    void save(OrderBook orderBook);
}
