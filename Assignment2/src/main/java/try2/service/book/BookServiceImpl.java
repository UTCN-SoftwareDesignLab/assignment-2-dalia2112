package try2.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import try2.model.Book;
import try2.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> findall() {
        final Iterable<Book> items = bookRepository.findAll();
        List<Book> result = new ArrayList<>();
        items.forEach(result::add);
        return result;
    }

    @Override
    public void deleteBook(long id){
        bookRepository.deleteById(id);
    }


}
