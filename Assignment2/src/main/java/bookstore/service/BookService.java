package bookstore.service;


import bookstore.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Catalysts on 8/9/2015.
 */
@Service
public interface BookService {
    List<Book> getAll();
    Book create(Book book);
}
