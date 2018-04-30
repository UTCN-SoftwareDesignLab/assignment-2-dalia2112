package bookstore.service.user;

import org.springframework.stereotype.Service;
import bookstore.model.User;
import bookstore.model.validation.Notification;

import java.util.List;

@Service
public interface UserService {

    void save(User user);
    List<User> findAll();
    Notification<Boolean> deleteUser(long id);
//    User findByName(String username);
    User findById(long id);
//    void updateUser(String username,String password,String role);
    Notification<Boolean> registerUser(String username, String password, String role);
    Notification<Boolean> login(String username, String password);
    Notification<Boolean> updateUser(long id, String name, String password, String role);
}
