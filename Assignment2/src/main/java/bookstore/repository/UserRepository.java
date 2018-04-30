package bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import bookstore.model.User;
import bookstore.model.validation.Notification;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
    User findById(long id);
    List<User> findAll();

    User findByNameAndPassword(String username, String password);
}
