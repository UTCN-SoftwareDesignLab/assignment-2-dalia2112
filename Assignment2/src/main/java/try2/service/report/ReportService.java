package try2.service.report;

import org.springframework.stereotype.Service;
import try2.model.Book;

import java.util.List;

@Service
public interface ReportService {
    void generateReport(List<Book> books);
}
