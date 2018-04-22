package try2.controller;

import com.google.api.services.books.model.Volume;
import try2.model.BookApi;
import try2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import try2.model.builder.BookBuilder;
import try2.model.validation.Notification;
import try2.model.validation.UserValidator;
import try2.service.book.BookService;
import try2.model.Book;
import try2.service.bookApi.BookApiService;
import try2.service.bookorder.OrderBookService;
import try2.service.report.ReportFactory;
import try2.service.report.ReportService;
import try2.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderBookService orderBookService;
    @Autowired
    private ReportFactory reportFactory;
    @Autowired
    private BookApiService bookApiService;


    /***************************** BOOK **********************/
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String showBook() {
        return "book";
    }

    //CREATE BOOK
    @RequestMapping(value = "/book", params = "add", method = RequestMethod.POST)
    public String addNewBook(Model model, @ModelAttribute Book book1) {

        Notification<Boolean> notification = bookService.addBook(book1);
        if (notification.hasErrors()) {
            model.addAttribute("addBook", true);
            model.addAttribute("addBErr", notification.getFormattedErrors());
        } else {
            model.addAttribute("addBookS", true);
            model.addAttribute("addBSucc", "Book created succesfully!");
        }
        return "book";
    }

    //READ BOOKS
    @RequestMapping(value = "/bookView", params = "viewBooks", method = RequestMethod.GET)
    public String findAll(Model model) {
        final List<Book> items = bookService.findall();
        model.addAttribute("books", items);
        return "book";
    }

    //UPDATE BOOK
    @RequestMapping(value = "/book", params = "update", method = RequestMethod.POST)
    public String update(Model model, @RequestParam String title, @RequestParam String author, @RequestParam String genre
            , @RequestParam int price, @RequestParam int quantity, @RequestParam long id) {


//        if (!validateNr(id)) {
//            model.addAttribute("updBook", true);
//            model.addAttribute("addBErr2", "Id not available");
//            return "book";
//        }
//        long idd = Long.parseLong(id);
        Notification<Boolean> notification = bookService.update(id, title, author, genre, quantity, price);
        if (notification.hasErrors()) {
            model.addAttribute("updBook", true);
            model.addAttribute("addBErr2", notification.getFormattedErrors());
        } else {
            model.addAttribute("updBookS", true);
            model.addAttribute("addBSucc2", "Book updated succesfully!");
        }
        return "book";
    }

    //DELETE BOOK

    @RequestMapping(value = "/delBook", params = "delete", method = RequestMethod.GET)
    public String deleteBook(Model model, @RequestParam("BookID") long id) {

//        BookValidator bookValidator = new BookValidator();
//        if (!validateNr(id)) {
//            model.addAttribute("delBook", true);
//            model.addAttribute("delErr", "Id not available");
//            return "book";
//        }
//        long idd = Long.parseLong(id);
        if (bookService.findById(id) == null) {
            model.addAttribute("delBook", true);
            model.addAttribute("delErr", "Book does not exist");
        } else {
            bookService.deleteBook(id);
            model.addAttribute("delBookS", true);
            model.addAttribute("delBSucc", "Book deleted succesfully!");
        }

        return "book";
    }


    /***************************USER *************************************/

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUser() {
        return "user";
    }

    //CREATE USER
    @RequestMapping(value = "/register", params = "reg", method = RequestMethod.POST)
    public @ResponseBody
    String addNewUser(Model model, @ModelAttribute User user) {
        System.out.println(user.getName() + " " + user.getPassword() + " " + user.getPassword());
        if (userService.registerUser(user.getName(), user.getPassword(), user.getRole()).hasErrors()) {
            model.addAttribute("registerErr", true);
            model.addAttribute("errMsg2", userService.registerUser(user.getName(), user.getPassword(), user.getRole()).getFormattedErrors());
        }
        return "register";
    }

    //READ/LIST USER
    @RequestMapping(value = "/userView", params = "viewUsers", method = RequestMethod.GET)
    public String readUsers(Model model) {
        List<User> items = userService.findAll();
        model.addAttribute("users", items);
        return "user";
    }

    //
    //UPDATE USER
    @RequestMapping(value = "/user", params = "update", method = RequestMethod.POST)
    public String updateUser(Model model, @RequestParam long id, @RequestParam String username, @RequestParam String password, @RequestParam String role) {

//        if (!validateNr(id)) {
//            model.addAttribute("updUErr", true);
//            model.addAttribute("updMessage", "Invalid ID");
//            return "user";
//        }

//        long idd = Long.parseLong(id);
        if (userService.findById(id) == null) {
            model.addAttribute("updUErr", true);
            model.addAttribute("updMessage", "User does not exist");
            return "user";
        }

        UserValidator userValidator = new UserValidator();
        if (userValidator.validateUpdate(username, password, role)) {
            userService.update(id, username, password, role);
            model.addAttribute("updUSucc", true);
            model.addAttribute("updMessage2", "User updated successfully!");
        } else {
            model.addAttribute("updUErr", true);
            model.addAttribute("updMessage", userValidator.getFormattedErrors());
        }
        return "user";
    }


//    //DELETE USER

    @RequestMapping(value = "/delUser", params = "delete", method = RequestMethod.GET)
    public String deleteUser(Model model, @RequestParam("UserID") long id) {
//        if (!validateNr(id)) {
//            model.addAttribute("updUErr", true);
//            model.addAttribute("updMessage", "Invalid ID");
//            return "user";
//        }

//        long idd = Long.parseLong(id);
        if (userService.findById(id) == null) {
            model.addAttribute("delUErr", true);
            model.addAttribute("delMessage", "User does not exist");
            return "user";
        }

        model.addAttribute("delSucc", true);
        userService.deleteUser(id);
        return "user";
    }


    @RequestMapping(value = "/logout", params = "logout", method = RequestMethod.GET)
    public String logout() {
        return "redirect:/login";
    }

    /**************REPORTS**************/

    @RequestMapping(value = "/book", params = "genReport", method = RequestMethod.POST)
    public String generateReportPDF(Model model, @RequestParam String format) {

        List<Book> books = bookService.findByQuantity(0);
        if (format.equalsIgnoreCase("pdf") || format.equalsIgnoreCase("csv")) {
            ReportService reportService = reportFactory.getReport(format);
            reportService.generateReport(books);
            model.addAttribute("repSucc", true);
            model.addAttribute("repSMsc", "Report created successfully!");
        } else {
            model.addAttribute("repErr", true);
            model.addAttribute("repEMsg", "Wrong format (PDF or CSV only)!");
        }

        return "book";
    }


    /*********GOOGLE BOOK API*********/

    @RequestMapping(value = "/apiBook", method = RequestMethod.GET)
    public String showApi() {
        return "apiBook";
    }

    @RequestMapping(value = "/apiBook", params = "srcApi", method = RequestMethod.POST)
    public String searchBookApi(Model model, @RequestParam String title) {

        List<Book> books = bookApiService.apiBookByTitle(title);
        model.addAttribute("bookApi", books);
        return "apiBook";
    }

    @RequestMapping(value = "/apiBook", params = "addApi", method = RequestMethod.POST)
    public String addBookApi(Model model, @RequestParam String title, @RequestParam int id) {

        Book book = bookApiService.apiBookByTitle(title).get(id);
        book.setGenre("SF");
        Notification<Boolean> notification = bookService.addBook(book);
        if (notification.hasErrors()) {
            model.addAttribute("apiadd", true);
            model.addAttribute("apiMsg", notification.getFormattedErrors());
        } else {
            model.addAttribute("apiaddS", true);
            model.addAttribute("apiMsg2", "Book added successfully!");
        }
        return "apiBook";
    }

}
