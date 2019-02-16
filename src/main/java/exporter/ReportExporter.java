package exporter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.List;

/**
 * 2019-02-16
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
public class ReportExporter {

    static final Logger rootLogger = LogManager.getRootLogger();
    private List<String> report;
    private String fileName;

    public ReportExporter(List<String> report, String fileName) {
        this.report = report;
        this.fileName = fileName;
    }

    public void export() {
        try {
            File finalReportFile = new File(this.fileName);
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(finalReportFile), "UTF-16"));
            for (String line : report) {
                writer.write(line);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            rootLogger.error("LOG: Ошибка записи файла", e.getMessage());

        } catch (Exception e) {
            rootLogger.error("LOG: Возникла ошибка", e.getMessage());
        }
    }
}
