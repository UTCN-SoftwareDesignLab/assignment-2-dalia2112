package try2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import try2.model.OrderBook;

public interface OrderBookRepository extends JpaRepository<OrderBook,Long> {

    OrderBook findById(long id);
}
