package bookstore.service.report;

import bookstore.model.Book;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportCSV implements ReportService {
    @Override
    public void generateReport(List<Book> books) {
        String csvFile = "C:/Users/dalia/Desktop/Assignment2/reportCSV.csv";
        String comma = ",";
        String newline = "\n";
        String file_header = "id,title,author";
        try {
            FileWriter writer = new FileWriter(csvFile);
            writer.append(file_header);
            for (Book book : books) {
                writer.append(newline);
                writer.append(book.getId().toString());
                writer.append(comma);
                writer.append(book.getTitle());
                writer.append(comma);
                writer.append(book.getAuthor());
                writer.append(comma);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
