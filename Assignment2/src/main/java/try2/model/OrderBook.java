package try2.model;

import javax.persistence.*;

@Entity
public class OrderBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

        @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;
    private int quantity;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
//
//    public Book getBook() {
//        return book;
//    }
//
//    public void setBook(Book book) {
//        this.book = book;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
