package bookstore.service.bookApi;

import com.google.api.services.books.model.Volume;
import bookstore.model.Book;

import java.util.List;

public interface BookApiService {
    List<Volume> apiSearchBookByTitle(String title);

    List<Book> apiBookByTitle(String title);
}
