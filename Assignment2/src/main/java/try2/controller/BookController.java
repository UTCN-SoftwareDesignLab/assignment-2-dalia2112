package try2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import try2.service.BookService;
import try2.model.Book;

import java.util.List;

@Controller
public class BookController {
    private BookService bookService;
@Autowired
    public BookController(BookService bookService) {

    this.bookService = bookService;
    }

    @GetMapping("/todos")
    public String findAll(Model model){
        final List<Book> items= bookService.findall();
        model.addAttribute("itemsCount",items.size());
        return "home";
    }

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
        System.out.println(title+" "+ author+ " " + isbn);

        Book book=new Book();
        book.setName(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        bookService.save(book);

        return "book";
    }
}
