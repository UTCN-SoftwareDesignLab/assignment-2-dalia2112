package try2;

import bookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl1 implements BookService1 {
    private BookRepository1 bookRepository1;

    @Autowired
    public BookServiceImpl1(BookRepository1 bookRepository1) {
        this.bookRepository1 = bookRepository1;
    }

    @Override
    public void save(Book1 book1){
        bookRepository1.save(book1);
    }

    @Override
    public List<Book1> findall() {
        final Iterable<Book1> items=bookRepository1.findAll();
        List<Book1> result=new ArrayList<>();
        items.forEach(result::add);
        return result;
    }


}
