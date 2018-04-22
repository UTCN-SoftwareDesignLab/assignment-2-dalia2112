package try2.service.report;

import org.springframework.stereotype.Service;

@Service
public class ReportFactory {

    public ReportService getReport(String format){
        if(format.equalsIgnoreCase("pdf")){
            return new ReportPDF();
        }
        else if(format.equalsIgnoreCase("csv")){
            return new ReportCSV();
        }
        return null;
    }
}
