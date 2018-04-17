package try2.service.user;

import bookstore.model.User;

import java.util.List;

public interface UserService {

    void save(User user);
    List<User> findall();
    void deleteUser(long id);
}
