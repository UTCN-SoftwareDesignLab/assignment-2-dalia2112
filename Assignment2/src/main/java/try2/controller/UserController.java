package try2.controller;

import try2.model.User;
import try2.model.builder.BookBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import try2.model.builder.UserBuilder;
import try2.model.validation.Notification;
import try2.service.book.BookService;
import try2.model.Book;
import try2.service.user.UserService;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegister() {
        System.out.println("IN GET - REGISTER");
        return "register";
    }

    //CREATE
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    String addNewUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        System.out.println("ADD NEW USER");
        userService.registerUser(username, password, role);
        return "register";
    }

    //LOGIN

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showUser() {
        System.out.println("IN GET - LOGIN");
        return "login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    String loginUser(@RequestParam String username, @RequestParam String password) {
        System.out.println("LOGIN USER");
        Notification<Boolean> loginNotification = userService.login(username, password);
        if (!loginNotification.getResult()) {
            if (loginNotification.hasErrors()) {
                System.out.println(loginNotification.getFormattedErrors());
                return "login failed";
            }
        }
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

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public @ResponseBody
//    String deleteUser(@RequestParam String id) {
//
//        long idd=Long.parseLong(id);
//       userService.deleteUser(idd);
//
//        return "login";
//    }
    }
