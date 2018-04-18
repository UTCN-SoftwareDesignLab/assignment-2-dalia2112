package try2.service.book;

import try2.model.Book;

import java.util.List;

public interface BookService {
    void save(Book book);

    List<Book> findall();

    void update(long id,String name,String author,String genre,int quantity,int price);

    void deleteBook(long id);
}
