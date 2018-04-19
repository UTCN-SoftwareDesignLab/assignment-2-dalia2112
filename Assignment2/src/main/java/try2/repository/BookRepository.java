package try2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import try2.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findById(long id);
    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByGenre(String genre);

}
