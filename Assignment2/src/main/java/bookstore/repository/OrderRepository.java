package bookstore.repository;

import bookstore.model.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderBook, Long> {
    OrderBook findById(long id);
}

