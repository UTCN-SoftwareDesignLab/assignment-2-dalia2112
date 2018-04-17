package bookstore.model.builder;

import bookstore.model.Book;
import bookstore.model.Order;

public class OrderBuilder {

    private Order order;

    public OrderBuilder() {
        order = new Order();
    }

    public OrderBuilder setId(long id) {
        order.setId(id);
        return this;
    }

    public OrderBuilder setBook(Book book) {
        order.setBook(book);
        return this;
    }

    public OrderBuilder setQuantity(int quantity) {
        order.setQuantity(quantity);
        return this;
    }

    public Order build() {
        return order;
    }
}
