package parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.dto.Settings;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * 2019-02-16
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
public class XmlParser {
    static final Logger rootLogger = LogManager.getRootLogger();
    private String settingsUrl;

    public XmlParser(String settingsUrl) {
        this.settingsUrl = settingsUrl;
    }

    public Settings getSettings() {
        File xmlFile = new File(settingsUrl);
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Settings.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Settings settings = (Settings) jaxbUnmarshaller.unmarshal(xmlFile);
            System.out.println(settings);
            return settings;
        } catch (JAXBException e) {
            rootLogger.error("LOG: Ошибка при чтения файла XML", e.getMessage());
        }
        return null;
    }
}
