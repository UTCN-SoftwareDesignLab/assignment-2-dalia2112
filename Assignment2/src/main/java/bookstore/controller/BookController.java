package bookstore.controller;

import bookstore.model.Book;
import bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;



    @GetMapping("/tst")
    public String findAll(Model model) {
        final List<Book> items = bookService.getAll();
        model.addAttribute("itemsCount", items.size());
        return "tst";
    }
}
