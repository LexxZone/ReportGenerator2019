package reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 2019-02-16
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
public class SourceReader {

    static final Logger rootLogger = LogManager.getRootLogger();
    private String sourceUrl;

    public SourceReader(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public List<String> read() {
        File file = new File(sourceUrl);
        BufferedReader TSVFile = null;
        try {
            TSVFile = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), "UTF-16"));
        } catch (UnsupportedEncodingException e) {
            rootLogger.error("LOG: Неподдерживаемая кодировка файла", e.getMessage());
        } catch (FileNotFoundException e) {
            rootLogger.error("LOG: Файл не найден", e.getMessage());
        }
        String dataRow;
        ArrayList<String> sourceData = new ArrayList<String>();
        try {
            dataRow = TSVFile.readLine();
        } catch (IOException e) {
            rootLogger.error("LOG: Ошибка чтения файла", e.getMessage());
            dataRow = null;
        }
        while (dataRow != null) {
            String[] wordsLineArray = dataRow.split("\t");
            for (String item : wordsLineArray) {
                sourceData.add(item);
            }
            try {
                dataRow = TSVFile.readLine();
            } catch (IOException e) {
                rootLogger.error("LOG: Ошибка чтения файла", e.getMessage());
                dataRow = null;
            }
        }
        try {
            TSVFile.close();
        } catch (IOException e) {
            rootLogger.error("LOG: Ошибка при закрытии файла", e.getMessage());
        }
        return sourceData;
    }
}
