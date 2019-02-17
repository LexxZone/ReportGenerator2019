package generator;

import generator.builder.ReportBuilder;
import generator.exporter.ReportExporter;
import generator.parser.XmlParser;
import generator.parser.dto.Settings;
import generator.reader.SourceReader;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.List;

/**
 * 2019-020-16
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
public class Generator {

    private static final Logger LOG = LogManager.getLogger(Generator.class.getName());
    private final String settings;
    private final String source;
    private final String resultFile;

    public Generator(String settings, String source, String resultFile) {
        this.settings = settings;
        this.source = source;
        this.resultFile = resultFile;
    }

    public Generator() {
        this.settings = "settings.xml";
        this.source = "source-data.tsv";
        this.resultFile = "report.txt";
    }

    public static void main(String[] args) {
        Generator generator = new Generator();
        XmlParser parser = new XmlParser(generator.getSettings());
        Settings set = parser.getSettings();

        List<String> sourceData = new SourceReader(generator.getSource()).read();
        ReportBuilder builder = new ReportBuilder(set, sourceData);
        List<String> report = builder.createReport();

        ReportExporter exporter = new ReportExporter(report, generator.getResultFile());
        exporter.export();
    }

    private String getSettings() {
        return settings;
    }

    private String getSource() {
        return source;
    }

    private String getResultFile() {
        return resultFile;
    }
}
