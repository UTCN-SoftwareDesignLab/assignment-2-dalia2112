package try2.controller;

import try2.model.User;
import try2.model.builder.BookBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import try2.model.builder.UserBuilder;
import try2.service.book.BookService;
import try2.model.Book;
import try2.service.user.UserService;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showUser() {
        return "login";
    }

    //CREATE
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    String addNewUser(@RequestParam String username, @RequestParam String password) {

        User user=new UserBuilder()
                .setName(username)
                .setPassword(password)
                .build();
        userService.save(user);
        return "login";
    }

//    //READ
//    @GetMapping("/login")
//    public String findAll(Model model) {
//        final List<User> items = userService.findall();
//        model.addAttribute("itemsCount", items.size());
//        return "login";
//    }

    //UPDATE


    //DELETE

//    @RequestMapping(value = "/book", method = RequestMethod.POST)
//    public @ResponseBody
//    String deleteBook(@RequestParam String id) {
//
//        long idd=Long.parseLong(id);
//        bookService.deleteBook(idd);
//
//        return "book";
//    }
}
