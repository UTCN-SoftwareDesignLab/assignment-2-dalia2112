package bookstore.service.report;

import org.springframework.stereotype.Service;
import bookstore.model.Book;

import java.util.List;

@Service
public interface ReportService {
    void generateReport(List<Book> books);
}
