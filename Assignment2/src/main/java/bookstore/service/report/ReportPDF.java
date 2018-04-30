package bookstore.service.report;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import bookstore.model.Book;

import java.io.IOException;
import java.util.List;

public class ReportPDF implements ReportService {

    @Override
    public void generateReport(List<Book> books) {
        String fileName = "report.pdf";
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
            content.beginText();
            content.setFont(PDType1Font.COURIER, 15);
            content.setLeading(20f);
            content.newLineAtOffset(20, 700);
            content.showText("Books out of stock: ");
            content.newLine();
            content.newLine();
            String result = "";
            for (Book book : books) {
                content.showText(book.toString());
                content.newLine();
            }
            content.showText(result);
            content.endText();
            content.close();
            doc.save(fileName);
            System.out.println("PDFFFFFFF "+System.getProperty("user.dir"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
