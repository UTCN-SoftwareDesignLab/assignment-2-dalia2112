package try2.service.bookApi;

import com.google.api.client.auth.oauth2.ClientCredentialsTokenRequest;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volumes;
import com.google.api.services.books.model.Volume;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class BookApiServiceImpl implements BookApiService{

    public List<Volume> apiSearchBookByTitle(String title){
        final String API_KEY="AIzaSyDV8HWT1kQ4WTW5vmtKtGVv1DgNUTAfrkU";
        JsonFactory jsonFactory =  new JacksonFactory();
        try {
            final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                    .setApplicationName("dalApp/1.0")
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
                    .build();
            Volumes volumes=books.volumes().list(title).setFilter("ebooks").execute();
            return volumes.getItems();

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
