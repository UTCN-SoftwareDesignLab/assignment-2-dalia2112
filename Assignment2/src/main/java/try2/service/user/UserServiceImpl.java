package try2.service.user;

import bookstore.model.User;
import bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import try2.model.Book;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> findall() {
        final Iterable<User> items = userRepository.findAll();
        List<User> result = new ArrayList<>();
        items.forEach(result::add);
        return result;
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
