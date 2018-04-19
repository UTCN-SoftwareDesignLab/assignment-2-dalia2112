package try2.service.user;

import org.springframework.stereotype.Service;
import try2.model.User;
import try2.model.validation.Notification;

import java.util.List;

@Service
public interface UserService {

    void save(User user);
    List<User> findAll();
    void deleteUser(long id);
    User findByName(String username);
    User findById(long id);
//    void updateUser(String username,String password,String role);
    Notification<Boolean> registerUser(String username, String password, String role);
    Notification<Boolean> login(String username, String password);
    void update(long id,String name, String password, String role);
}
