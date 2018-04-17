package try2;

import bookstore.model.Book;
import bookstore.model.builder.BookBuilder;
import bookstore.repository.BookRepository;
import bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController1 {
    private BookService1 bookService;
@Autowired
    public BookController1(BookService1 bookService) {

    this.bookService = bookService;
    }

    @GetMapping("/todos")
    public String findAll(Model model){
        final List<Book1> items= bookService.findall();
        model.addAttribute("itemsCount",items.size());
        return "home";
    }

    @RequestMapping(value = "/book",method = RequestMethod.GET)
    public String showBook(){
        return "book";
    }
//
    @RequestMapping(value = "/book",method= RequestMethod.POST)
    public @ResponseBody
    String addNewBook(@RequestParam String title
            , @RequestParam String author, @RequestParam String isbn) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        System.out.println(title+" "+ author+ " " + isbn);

        Book1 book=new Book1();
        book.setName(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        bookService.save(book);

        //return "Saved";
        return "book";
    }
}
