package bookstore.service.bookorder;

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

    @Override
    public OrderBook findById(long id) {
        return orderBookRepository.findById(id);
    }

    @Override
    public void save(OrderBook orderBook) {
        orderBookRepository.save(orderBook);
    }

    public List<OrderBook> findall() {
        return orderBookRepository.findAll();
    }

    public void deleteOrder(long id){ orderBookRepository.deleteById(id);}

}
