package try2.repository;

import org.springframework.data.repository.CrudRepository;
import try2.model.User;

public interface UserRepository extends CrudRepository<User,Long> {
}
