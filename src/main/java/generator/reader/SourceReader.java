package generator.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 2019-02-16
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
public class SourceReader {

    private static final Logger LOG = LogManager.getLogger(SourceReader.class.getName());
    private String sourceUrl;

    public SourceReader(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public List<String> read() {
        File file = new File(sourceUrl);
        BufferedReader TSVFile = null;
        try {
            TSVFile = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_16));
        } catch (FileNotFoundException e) {
            LOG.error("Файл не найден", e);
        }
        String dataRow = null;
        ArrayList<String> sourceData = new ArrayList<>();
        try {
            dataRow = TSVFile.readLine();
        } catch (NullPointerException e) {
            LOG.error("Ошибка чтения файла. NullPointer.", e);
        }
        catch (IOException e) {
            LOG.error("Ошибка чтения файла", e);
            dataRow = null;
        }
        while (null != dataRow) {
            String[] wordsLineArray = dataRow.split("\t");
            Collections.addAll(sourceData, wordsLineArray);
            try {
                dataRow = TSVFile.readLine();
            } catch (IOException e) {
                LOG.error("Ошибка чтения файла", e);
                dataRow = null;
            }
        }
        try {
            TSVFile.close();
        } catch (IOException e) {
            LOG.error("Ошибка при закрытии файла", e);
        }
        return sourceData;
    }
}
