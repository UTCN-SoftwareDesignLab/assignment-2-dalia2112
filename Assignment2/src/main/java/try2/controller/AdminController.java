package try2.controller;

import try2.model.builder.BookBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import try2.service.book.BookService;
import try2.model.Book;

import java.util.List;

@Controller
public class AdminController {
    private BookService bookService;

    @Autowired
    public AdminController(BookService bookService) {

        this.bookService = bookService;
    }


    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String showBook() {
        return "book";
    }

    //CREATE
    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public @ResponseBody
    String addNewBook(@RequestParam String title
            , @RequestParam String author, @RequestParam String genre, @RequestParam int quantity, @RequestParam int price) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
//        System.out.println(title + " " + author + " " + isbn);

        Book book = new BookBuilder()
                .setTitle(title)
                .setAuthor(author)
                .setGenre(genre)
                .setQuantity(quantity)
                .setPrice(price)
                .build();
        bookService.save(book);

        return "book";
    }

    //READ
    @GetMapping("/todos")
    public String findAll(Model model) {
        final List<Book> items = bookService.findall();
        model.addAttribute("itemsCount", items.size());
        return "home";
    }

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
