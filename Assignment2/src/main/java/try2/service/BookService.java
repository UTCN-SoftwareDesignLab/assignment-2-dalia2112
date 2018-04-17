package try2.service;

import try2.model.Book;

import java.util.List;

public interface BookService {
 void save(Book book);
 List<Book> findall();
}
