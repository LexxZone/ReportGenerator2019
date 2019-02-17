package generator.exporter;

import org.apache.logging.log4j.LogManager;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 2019-02-16
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
public class ReportExporter {

   private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(ReportExporter.class.getName());
    private List<String> report;
    private String fileName;

    public ReportExporter(List<String> report, String fileName) {
        this.report = report;
        this.fileName = fileName;
    }

    public void export() {
        try {
            File finalReportFile = new File(this.fileName);
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(finalReportFile), StandardCharsets.UTF_16));
            for (String line : report) {
                writer.write(line);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LOG.error("Ошибка записи файла", e);

        } catch (Exception e) {
            LOG.error("Возникла ошибка", e);
        }
    }
}
