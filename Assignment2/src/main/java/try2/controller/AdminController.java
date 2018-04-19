package try2.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.hibernate.boot.jaxb.SourceType;
import sun.util.resources.sv.CurrencyNames_sv;
import try2.model.User;
import try2.model.builder.BookBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import try2.model.builder.UserBuilder;
import try2.model.validation.BookValidator;
import try2.model.validation.UserValidator;
import try2.service.book.BookService;
import try2.model.Book;
import try2.service.user.UserService;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;


    /***************************** BOOK **********************/
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String showBook() {
        return "book";
    }

    //CREATE
    @RequestMapping(value = "/book", params = "add", method = RequestMethod.POST)
    public @ResponseBody
    String addNewBook(@RequestParam String title
            , @RequestParam String author, @RequestParam String genre, @RequestParam String quantity, @RequestParam String price) {
        BookValidator bookValidator = new BookValidator();
        bookValidator.validatePrice(price);
        bookValidator.validateQuantity(quantity);
        bookValidator.validateGenre(genre);
        if (!bookValidator.hasErrors()) {
            int price1 = Integer.parseInt(price);
            int quantity1 = Integer.parseInt(quantity);
            Book book = new BookBuilder()
                    .setTitle(title)
                    .setAuthor(author)
                    .setGenre(genre)
                    .setQuantity(quantity1)
                    .setPrice(price1)
                    .build();
            bookService.save(book);
        }
        return "book";
    }

    //    //READ
    @RequestMapping(value = "/bookView", params = "viewBooks", method = RequestMethod.GET)
    public String findAll(Model model) {
        final List<Book> items = bookService.findall();
        model.addAttribute("books", items);
        return "book";
    }

    //    //UPDATE
    @RequestMapping(value = "/book", params = "update", method = RequestMethod.POST)
    public String update(@RequestParam String title, @RequestParam String author, @RequestParam String genre
            , @RequestParam String price, @RequestParam String quantity, @RequestParam String id) {

        //VALIDATE FIRST
        BookValidator bookValidator = new BookValidator();
        bookValidator.validateQuantity(quantity);
        bookValidator.validatePrice(price);
        bookValidator.validateGenre(genre);
        if (!bookValidator.hasErrors()) {
            long idd = Long.parseLong(id);
            int quant = Integer.parseInt(quantity);
            int pricee = Integer.parseInt(price);
            bookService.update(idd, title, author, genre, quant, pricee);
        }
        return "book";
    }

    //DELETE

    @RequestMapping(value = "/delBook", params = "delete", method = RequestMethod.GET)
    public @ResponseBody
    String deleteBook(Model model, @RequestParam("BookID") String id) {

        long idd = Long.parseLong(id);
        bookService.deleteBook(idd);
        model.addAttribute("deleteMessage", "book " + id + " deleted");

        return "book";
    }


    /***************************USER *************************************/

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUser() {
        return "user";
    }

    //
    //CREATE USER
    @RequestMapping(value = "/register", params = "reg", method = RequestMethod.POST)
    public @ResponseBody
    String addNewUser(@RequestParam String username
            , @RequestParam String password, @RequestParam String role) {
        System.out.println(username + " " + password + " " + role);
        if (!userService.registerUser(username, password, role).getResult()) {
            return "user";
        }
        return "user";
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
    public String updateUser(@RequestParam String id, @RequestParam String username, @RequestParam String password, @RequestParam String role) {

        UserValidator userValidator = new UserValidator();
        long idd = Long.parseLong(id);
        if (userService.findById(idd) != null) {
            if (userValidator.validateUpdate(username, password, role)) {

                userService.update(idd, username, password, role);
            }
        }
        return "user";
    }

//    //DELETE USER

    @RequestMapping(value = "/delUser", params = "delete", method = RequestMethod.GET)
    public @ResponseBody
    String deleteUser(Model model, @RequestParam("UserID") String id) {

        long idd = Long.parseLong(id);
        userService.deleteUser(idd);
        model.addAttribute("deleteMessage", "user " + id + " deleted");

        return "user";
    }


    @RequestMapping(value = "/logout", params = "logout", method = RequestMethod.GET)
    public String logout() {
        return "redirect:/login";
    }

    /**************REPORTS**************/

    @RequestMapping(value = "/book", params = "genReport", method = RequestMethod.POST)
    public String generateReportPDF() {
        String fileName = "report.pdf";
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        List<Book> booksOutOfStock = bookService.findByQuantity(0);
        try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
            content.beginText();
            content.setFont(PDType1Font.COURIER, 15);
            content.setLeading(20f);
            content.newLineAtOffset(20, 700);
            content.showText("Books out of stock: ");
            content.newLine();
            content.newLine();
            String result = "";
            for (Book book : booksOutOfStock) {
                content.showText(book.toString());
                content.newLine();
            }
            content.showText(result);
            content.endText();
            content.close();
            doc.save(fileName);
            //doc.close();
            System.out.println("created in " + System.getProperty("user.dir"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "book";
    }

    @RequestMapping(value = "/book", params = "genReportCSV", method = RequestMethod.POST)
    public String generateReportCSV() {
        String csvFile = "C:/Users/dalia/Desktop/Assignment2/reportCSV.csv";
        String comma = ",";
        String newline = "\n";
        String file_header = "id,title,author";
        List<Book> booksOutOfStock = bookService.findByQuantity(0);
        try {
            FileWriter writer = new FileWriter(csvFile);
            writer.append(file_header);
            for (Book book : booksOutOfStock) {
                writer.append(newline);
                writer.append(book.getId().toString());
                writer.append(comma);
                writer.append(book.getTitle());
                writer.append(comma);
                writer.append(book.getAuthor());
                writer.append(comma);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "book";
    }
}
