package bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import bookstore.model.OrderBook;

public interface OrderBookRepository extends JpaRepository<OrderBook,Long> {

    OrderBook findById(long id);
}
