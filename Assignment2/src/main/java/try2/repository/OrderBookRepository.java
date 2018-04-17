package try2.repository;

import org.springframework.data.repository.CrudRepository;
import try2.model.OrderBook;

public interface OrderBookRepository extends CrudRepository<OrderBook,Long> {
}
