package try2.controller;

import try2.model.builder.BookBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import try2.model.validation.BookValidator;
import try2.service.book.BookService;
import try2.model.Book;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private BookService bookService;


//    public AdminController(BookService bookService) {
//
//        this.bookService = bookService;
//    }


    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String showBook() {
        return "book";
    }

    //CREATE
    @RequestMapping(value = "/book", params = "add",method = RequestMethod.POST)
    public @ResponseBody
    String addNewBook(@RequestParam String title
            , @RequestParam String author, @RequestParam String genre, @RequestParam String quantity, @RequestParam String price) {
        BookValidator bookValidator=new BookValidator();
        bookValidator.validatePrice(price);
        bookValidator.validateQuantity(quantity);
        bookValidator.validateGenre(genre);
        if(!bookValidator.hasErrors()) {
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
    @RequestMapping(value = "/bookView",params = "viewBooks",method = RequestMethod.GET)
    public String findAll(Model model) {
        final List<Book> items = bookService.findall();
        model.addAttribute("books", items);
        return "book";
    }

//    //UPDATE
    @RequestMapping(value = "/book",params = "update",method = RequestMethod.POST)
    public String update(@RequestParam String title, @RequestParam String author, @RequestParam String genre
            , @RequestParam String price, @RequestParam String quantity,@RequestParam String id) {

        //VALIDATE FIRST
        BookValidator bookValidator=new BookValidator();
        bookValidator.validateQuantity(quantity);
        bookValidator.validatePrice(price);
        bookValidator.validateGenre(genre);
        if(!bookValidator.hasErrors()) {
            long idd = Long.parseLong(id);
            int quant = Integer.parseInt(quantity);
            int pricee = Integer.parseInt(price);
            bookService.update(idd, title, author, genre, quant, pricee);
        }
        return "book";
    }

    //DELETE

    @RequestMapping(value = "/delBook",params = "delete",method = RequestMethod.GET)
    public @ResponseBody
    String deleteBook(Model model,@RequestParam("BookID") String id) {

        long idd=Long.parseLong(id);
        bookService.deleteBook(idd);
        model.addAttribute("deleteMessage","book "+id+" deleted");

        return "book";
    }

    @RequestMapping(value = "/logout",params = "logout",method = RequestMethod.GET)
    public String logout() {
        return "redirect:/login";
    }
}
