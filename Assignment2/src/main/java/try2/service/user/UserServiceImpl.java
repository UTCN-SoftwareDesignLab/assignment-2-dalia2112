package try2.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import try2.model.User;
import try2.model.builder.UserBuilder;
import try2.model.validation.Notification;
import try2.model.validation.UserValidator;
import try2.repository.UserRepository;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

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

    public static String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public User findByName(String username) {
        return userRepository.findByName(username);
    }


    public Notification<Boolean> registerUser(String username, String password, String role) {
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
            user.setPassword(encodePassword(password));
            userRegisterNotification.setResult(true);
            userRepository.save(user);
        }
        return userRegisterNotification;
    }

    public Notification<Boolean> login(String username, String password) {
        User user= userRepository.findByNameAndPassword(username, encodePassword(password));
        UserValidator userValidator=new UserValidator();
        Notification<Boolean> userLoginNotification = new Notification<>();
        if(user==null){
            userLoginNotification.addError("USER does not exist!");
            userLoginNotification.setResult(Boolean.FALSE);
        }
        else{
             userLoginNotification.setResult(true);
        }
        return userLoginNotification;

    }
}
