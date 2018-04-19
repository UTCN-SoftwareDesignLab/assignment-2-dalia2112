package try2.service.bookorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import try2.model.OrderBook;
import try2.repository.OrderBookRepository;

import java.util.List;

@Service
public class OrderBookServiceImpl implements OrderBookService {

    @Autowired
    private OrderBookRepository orderBookRepository;

    @Override
    public OrderBook findById(long id) {
        return orderBookRepository.findById(id);
    }

    @Override
    public void save(OrderBook orderBook){
        orderBookRepository.save(orderBook);
    }

    public List<OrderBook> findall(){
        return orderBookRepository.findAll();
    }
}
