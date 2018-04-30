package bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import bookstore.model.Book;
import bookstore.model.OrderBook;
import bookstore.model.builder.OrderBuilder;
import bookstore.service.book.BookService;
import bookstore.service.bookorder.OrderBookService;

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
    public String searchByTitle(Model model, @RequestParam String title) {
        List<Book> books = bookService.findByTitle(title);
        if (!books.isEmpty()) {
            model.addAttribute("books", books);
        }

        return "employeeOp";
    }

    @RequestMapping(value = "/employeeOp", params = "srcAuthor", method = RequestMethod.POST)
    public String searchByAuthor(Model model, @RequestParam String author) {
        List<Book> books = bookService.findByAuthor(author);
        if (!books.isEmpty()) {
            model.addAttribute("books", books);
        }
        return "employeeOp";
    }

    @RequestMapping(value = "/employeeOp", params = "srcGenre", method = RequestMethod.POST)
    public String searchByGenre(Model model, @RequestParam String genre) {

        List<Book> books = bookService.findByGenre(genre);

        if (!books.isEmpty()) {
            model.addAttribute("books", books);
        }
        return "employeeOp";
    }

    /********************PROCESS ORDER BOOK********************/

    @RequestMapping(value = "/employeeOp", params = "addOrder", method = RequestMethod.POST)
    public String addOrder(Model model, @RequestParam long bookId, @RequestParam int quantity) {
        Book book = bookService.findById(bookId);
        if (book == null) {
            model.addAttribute("addOErr", true);
            model.addAttribute("oErrMsg", "Book does not exist!");
        } else {
            OrderBook orderBook = new OrderBuilder()
                    .setBook(book)
                    .setQuantity(quantity)
                    .build();
            orderBookService.save(orderBook);
            model.addAttribute("addOSucc", true);
            model.addAttribute("oSuccMsg", "Order created succesfully!");
        }
        return "employeeOp";
    }

    @RequestMapping(value = "/employeeOp", params = "processOrder", method = RequestMethod.POST)
    public String processOrder(Model model, @RequestParam long orderId) {
        OrderBook orderBook = orderBookService.findById(orderId);
        if (orderBook == null) {
            model.addAttribute("procOErr", true);
            model.addAttribute("procErrMsg", "Order does not exist!");
            return "employeeOp";
        }

        Book book = orderBook.getBook();
        int remaining = book.getQuantity() - orderBook.getQuantity();
        if (remaining >= 0) {
            bookService.updateBook(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), remaining, book.getPrice());
            orderBookService.deleteOrder(orderId);
            model.addAttribute("procOSucc", true);
            model.addAttribute("procSuccMsg", "Order processed succesfully!");

        } else {
            model.addAttribute("procOErr", true);
            model.addAttribute("procErrMsg", "Not enough books!");
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
