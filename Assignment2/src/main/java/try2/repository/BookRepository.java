package try2.repository;

import org.springframework.data.repository.CrudRepository;
import try2.model.Book;

public interface BookRepository extends CrudRepository<Book,Long>{



}
