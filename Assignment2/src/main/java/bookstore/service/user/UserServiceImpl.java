package bookstore.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import bookstore.model.User;
import bookstore.model.builder.UserBuilder;
import bookstore.model.validation.Notification;
import bookstore.model.validation.UserValidator;
import bookstore.repository.UserRepository;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> findAll() {
        final Iterable<User> items = userRepository.findAll();
        List<User> result = new ArrayList<>();
        items.forEach(result::add);
        return result;
    }

    @Override
    public Notification<Boolean> deleteUser(long id) {
        Notification<Boolean> notification = new Notification<>();
        if (id < 0) {
            notification.addError("Id must be positive!");
            notification.setResult(false);
            return notification;
        }
        if (userRepository.findById(id) == null) {
            notification.addError("User does not exist");
            notification.setResult(false);
            return notification;
        }
        userRepository.deleteById(id);
        notification.setResult(true);
        return notification;
    }

    public User findByName(String username) {
        return userRepository.findByName(username);
    }


    public Notification<Boolean> registerUser(String username, String password, String role) {
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        User user = new UserBuilder()
                .setName(username)
                .setPassword(password)
                .setRole(role)
                .build();
        UserValidator userValidator = new UserValidator(user);
        if (userRepository.findByName(username) != null) {
            userValidator.setUserExists();
        }
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(passEncoder.encode(password));
            userRegisterNotification.setResult(true);
            userRepository.save(user);
        }
        return userRegisterNotification;
    }

    public Notification<Boolean> login(String username, String password) {
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByNameAndPassword(username, passEncoder.encode(password));
        Notification<Boolean> userLoginNotification = new Notification<>();
        if (user == null) {
            userLoginNotification.addError("USER does not exist!");
            userLoginNotification.setResult(Boolean.FALSE);
        } else {
            userLoginNotification.setResult(true);
        }
        return userLoginNotification;

    }

    @Override
    public Notification<Boolean> updateUser(long id, String name, String password, String role) {

        Notification<Boolean> notification = new Notification<>();
        if (id < 0) {
            notification.addError("Id must be positive!");
            notification.setResult(false);
            return notification;
        }
        if (userRepository.findById(id) == null) {
            notification.addError("User does not exist");
            notification.setResult(false);
            return notification;
        }
        UserValidator userValidator = new UserValidator();
        if (!userValidator.validateUpdate(name, password, role)) {
            userValidator.getErrors().forEach(notification::addError);
            notification.setResult(false);
            return notification;
        }
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        User user = (User) userRepository.findById(id);
        user.setName(name);
        user.setPassword(passEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);
        notification.setResult(true);
        return notification;
    }

    public User findById(long id) {
        return userRepository.findById(id);
    }

    public User findByUsername(String username){
        return userRepository.findByName(username);
    }
}
