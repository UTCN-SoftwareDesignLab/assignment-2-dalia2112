package try2.service.user;

import org.springframework.stereotype.Service;
import try2.model.User;

import java.util.List;

@Service
public interface UserService {

    void save(User user);
    List<User> findall();
    void deleteUser(long id);
}
