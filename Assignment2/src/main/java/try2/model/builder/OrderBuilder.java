package try2.model.builder;

import try2.model.Book;
import try2.model.OrderBook;

public class OrderBuilder {

    private OrderBook orderBook;

    public OrderBuilder() {
        orderBook = new OrderBook();
    }

    public OrderBuilder setId(long id) {
        orderBook.setId(id);
        return this;
    }

    public OrderBuilder setBook(Book book) {
        orderBook.setBook(book);
        return this;
    }

    public OrderBuilder setQuantity(int quantity) {
        orderBook.setQuantity(quantity);
        return this;
    }

    public OrderBook build() {
        return orderBook;
    }
}
