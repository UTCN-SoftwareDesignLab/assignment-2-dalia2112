package bookstore.repository;

import bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Catalysts on 8/9/2015.
 */

public interface BookRepository extends JpaRepository<Book, Long> {
}