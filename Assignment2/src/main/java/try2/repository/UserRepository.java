package try2.repository;

import org.springframework.data.repository.CrudRepository;
import try2.model.User;
import try2.model.validation.Notification;

public interface UserRepository extends CrudRepository<User,Long> {
   User findByName(String name);
   User findByNameAndPassword(String username, String password);
}
