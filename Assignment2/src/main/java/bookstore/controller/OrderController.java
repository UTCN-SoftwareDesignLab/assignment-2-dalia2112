package bookstore.controller;

import bookstore.model.OrderBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @Autowired
    private OrderBook orderBookRepository;

    @GetMapping("/tst")
    public String findAll(Model model) {
//        final List<Book> items = bookService.getAll();
//        model.addAttribute("itemsCount", items.size());
        return "tst";

    }
}
