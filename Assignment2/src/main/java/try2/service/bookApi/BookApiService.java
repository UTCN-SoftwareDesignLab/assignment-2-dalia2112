package try2.service.bookApi;

import com.google.api.services.books.model.Volume;

import java.util.List;

public interface BookApiService {
    List<Volume> apiSearchBookByTitle(String title);
}
