package try2.controller;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import try2.model.Book;
import try2.model.OrderBook;
import try2.model.builder.OrderBuilder;
import try2.model.validation.BookValidator;
import try2.service.book.BookService;
import try2.service.bookorder.OrderBookService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private BookService bookService;
    @Autowired
    private OrderBookService orderBookService;

    /*********************BOOK FIND BY*******************/

    @RequestMapping(value = "/employeeOp", method = RequestMethod.GET)
    public String showBook() {
        return "employeeOp";
    }


    @RequestMapping(value = "/employeeOp", params = "srcTitle", method = RequestMethod.POST)
    public @ResponseBody
    String searchByTitle(Model model, @RequestParam String title) {
        List<Book> books = bookService.findByTitle(title);
        if (!books.isEmpty()) {
            model.addAttribute("books", books);
            System.out.println("Found books");
        }
        return "employeeOp";
    }

    @RequestMapping(value = "/employeeOp", params = "srcAuthor", method = RequestMethod.POST)
    public @ResponseBody
    String searchByAuthor(Model model, @RequestParam String author) {
        List<Book> books = bookService.findByAuthor(author);
        if (!books.isEmpty()) {
            model.addAttribute("books", books);
            System.out.println("Found books");
        }
        return "employeeOp";
    }

    @RequestMapping(value = "/employeeOp", params = "srcGenre", method = RequestMethod.POST)
    public @ResponseBody
    String searchByGenre(Model model, @RequestParam String genre) {
        List<Book> books = bookService.findByGenre(genre);
        if (!books.isEmpty()) {
            model.addAttribute("books", books);
            System.out.println("Found books");
        }
        return "employeeOp";
    }

    /********************PROCESS ORDER BOOK********************/

    @RequestMapping(value = "/employeeOp", params = "addOrder", method = RequestMethod.POST)
    public @ResponseBody
    String addOrder(Model model, @RequestParam String bookId, @RequestParam String quantity) {
        long idd=Long.parseLong(bookId);
        int quantityy= Integer.parseInt(quantity);
        Book book=bookService.findById(idd);
        OrderBook orderBook=new OrderBuilder()
                .setBook(book)
                .setQuantity(quantityy)
                .build();
        orderBookService.save(orderBook);
        return "employeeOp";
    }

    @RequestMapping(value = "/employeeOp", params = "processOrder", method = RequestMethod.POST)
    public @ResponseBody
    String processOrder(Model model, @RequestParam String orderId) {
        long id=Long.parseLong(orderId);
        OrderBook orderBook=orderBookService.findById(id);
        Book book=orderBook.getBook();
        BookValidator bookValidator=new BookValidator();
        int remaining=book.getQuantity()-orderBook.getQuantity();
        bookValidator.validateOrder(remaining);
        if(!bookValidator.hasErrors()){
            bookService.update(book.getId(),book.getTitle(),book.getAuthor(),book.getGenre(),remaining,book.getPrice());
        }
        return "employeeOp";
    }

    @RequestMapping(value = "/orderView", params = "viewOrders", method = RequestMethod.GET)
    public String findAll(Model model) {
        final List<OrderBook> items = orderBookService.findall();
        model.addAttribute("orders", items);
        return "employeeOp";
    }
}
