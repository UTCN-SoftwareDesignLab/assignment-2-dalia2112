package try2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import try2.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findById(long id);

}
