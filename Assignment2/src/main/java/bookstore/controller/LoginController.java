package bookstore.controller;

import bookstore.model.validation.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookstore.service.user.UserService;


@Controller
public class LoginController implements WebMvcConfigurer {
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {

        this.userService = userService;
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegister() {
        return "register";
    }

    //CREATE
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addNewUser(Model model, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        System.out.println(username + " " + password + " " + password);
        if (userService.registerUser(username, password, role).hasErrors()) {
            model.addAttribute("registerErr", true);
            model.addAttribute("errMsg2", userService.registerUser(username, password, role).getFormattedErrors());
        }
        return "register";
    }


    //LOGIN

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, @RequestParam String username, @RequestParam String password) {
        Notification<Boolean> notification = userService.login(username, password);
        if (notification.hasErrors()) {
            model.addAttribute("loginErr", true);
            model.addAttribute("errMsg", notification.getFormattedErrors());
            return "/login";
        }
        if (userService.findByUsername(username).getRole().equalsIgnoreCase("admin"))
            return "redirect:/book";
        else return "redirect:/employeeOp";

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showUser() {
        return "login";
    }

    //LOGOUT
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
