import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import try2.model.Book;
import try2.model.builder.BookBuilder;
import try2.model.validation.Notification;
import try2.service.book.BookService;
import try2.service.book.BookServiceImpl;

import javax.swing.*;
import java.util.List;

public class BookServiceImplTest {
    private static BookService target;

    @BeforeClass
    public static void init(){
        target=new BookServiceImpl(new MockBookRepository());
    }

    @Test
    public void testSave(){
        Book book=new BookBuilder()
                .setTitle("TestBook")
                .setAuthor("Sadoveanu")
                .setGenre("Horror")
                .setQuantity(1)
                .setPrice(30)
                .build();
        Notification<Boolean> notification=target.addBook(book);
        Assert.assertTrue(!notification.hasErrors());
    }

    @Test
    public void testUpdate(){
        Book book=new BookBuilder()
                .setTitle("TestBook")
                .setAuthor("Sadoveanu")
                .setGenre("Horror")
                .setQuantity(1)
                .setPrice(30)
                .build();
        Notification<Boolean> notification=target.addBook(book);
//        Notification<Boolean>notification2=target.update(book.getId(),"Gramy","Petre","Horror",2,30);
//        JOptionPane.showMessageDialog(null,notification.getFormattedErrors());
//        Assert.assertTrue(!notification.hasErrors());
    }
}
