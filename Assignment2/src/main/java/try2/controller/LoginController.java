package try2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import try2.model.User;
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
        return "register";
    }

    //    //CREATE
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    String addNewUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        if (!userService.registerUser(username, password, role).getResult()) {
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
    public String loginUser(Model model, @RequestParam String username, @RequestParam String password) {
        Notification<Boolean> loginNotification = userService.login(username, password);
        if (!loginNotification.getResult()) {
            if (loginNotification.hasErrors()) {
                System.out.println(loginNotification.getFormattedErrors());
                model.addAttribute("error","NOOOOOO");
                return "login";
            }
        }
        User user=userService.findByName(username);
        if(user.getRole().equalsIgnoreCase("admin")){
            return "redirect:/book";
        }
        return "redirect:/employeeOp";
    }
}
