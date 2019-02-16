import builder.ReportBuilder;
import exporter.ReportExporter;
import parser.XmlParser;
import parser.dto.Settings;
import reader.SourceReader;

import java.util.List;

/**
 * 2019-020-16
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
public class Generator {

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

        Generator generator = new Generator("settings.xml", "source-data.tsv", "report.txt");
        XmlParser parser = new XmlParser(generator.getSettings());
        Settings set = parser.getSettings();

        List<String> sourceData = new SourceReader(generator.getSource()).read();
        ReportBuilder builder = new ReportBuilder(set, sourceData);
        List<String> report = builder.createReport();

        ReportExporter exporter = new ReportExporter(report, generator.getResultFile());
        exporter.export();
    }

    public String getSettings() {
        return settings;
    }

    public String getSource() {
        return source;
    }

    public String getResultFile() {
        return resultFile;
    }
}
