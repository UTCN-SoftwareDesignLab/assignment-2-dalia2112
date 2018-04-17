package bookstore.controller;

import bookstore.model.Book;
import bookstore.model.User;
import bookstore.model.builder.BookBuilder;
import bookstore.model.builder.UserBuilder;
import bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /*@GetMapping("/tst")
    public String findAll(Model model) {
        final List<Book> items = bookService.getAll();
        model.addAttribute("itemsCount", items.size());
        return "tst";
    }*/

    @RequestMapping(value = "/book",method = RequestMethod.GET)
    public String showBook(){
        return "book";
    }

    @RequestMapping(value = "/book",method= RequestMethod.POST)
    public @ResponseBody
    String addNewBook(@RequestParam String title
            , @RequestParam String author, @RequestParam String isbn) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Book book=new BookBuilder()
                .setName(title)
                .setAuthor(author)
                .setIsbn(isbn)
                .build();

        bookService.create(book);
        //return "Saved";
        return "book";
    }

}
