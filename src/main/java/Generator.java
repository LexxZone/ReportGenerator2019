import builder.ReportBuilder;
import exporter.ReportExporter;
import parser.XmlParser;
import parser.dto.Settings;
import reader.SourceReader;

import javax.management.modelmbean.XMLParseException;
import java.util.List;

/**
 * 23.01.2019
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
public class Generator {

    private final String settings;
    private final String source;
    private final String result;

    public Generator(String settings, String source, String result) {
        this.settings = settings;
        this.source = source;
        this.result = result;
    }

    public Generator() {
        this.settings = "settings.xml";
        this.source = "source-data.tsv";
        this.result = "settings.xml";
    }

    public static void main(String[] args) {
        Generator generator = new Generator("settings.xml", "source-data.tsv", "report");
        XmlParser parser = new XmlParser(generator.settings);
        Settings set = parser.getSettings();

        List<String> sourceData = new SourceReader(generator.source).read();
        // TODO Сделать чекер на лишние строки из файла данных с именами. Проверка на нуль и кратность трем

        ReportBuilder builder = new ReportBuilder(set, sourceData);
        builder.createReport();

        ReportExporter exporter = new ReportExporter();
        exporter.export();

    }
}
