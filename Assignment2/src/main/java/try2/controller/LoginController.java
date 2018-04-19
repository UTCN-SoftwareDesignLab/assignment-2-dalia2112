package try2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import try2.model.validation.Notification;
import try2.service.user.UserService;

@Controller
public class LoginController {
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {

        this.userService = userService;
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegister() {
        System.out.println("IN GET - REGISTER");
        return "register";
    }

//    //CREATE
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    String addNewUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        if(!userService.registerUser(username, password, role).getResult()){
            return "redirect:/register";
        }
        return "register successful";
    }

    //LOGIN

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showUser() {
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
//                return "redirect:/login";
                return "redirect:/book";
            }
        }
        return "login successful";

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
