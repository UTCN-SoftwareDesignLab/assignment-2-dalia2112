package try2.controller;

import bookstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import try2.model.builder.UserBuilder;
import try2.service.user.UserService;

import java.util.List;

//@Controller
public class UserController {
    /*private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showUser() {
        return "login";
    }

    //CREATE
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public @ResponseBody
//    String addNewUser(@RequestParam String username, @RequestParam String password) {
//
//        User user = new UserBuilder()
//                .setName(username)
//                .setPassword(password)
//                .build();
//
//        userService.save(user);
//
//        return "login";
//    }

    //READ
    @GetMapping("/todos")
    public String findAll(Model model) {
        final List<User> items = userService.findall();
        model.addAttribute("itemsCount", items.size());
        return "home";
    }

    //UPDATE


    //DELETE

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public @ResponseBody
//    String deleteUser(@RequestParam String id) {
//
//        long idd=Long.parseLong(id);
//        userService.deleteUser(idd);
//
//        return "book";
//    }*/
}
