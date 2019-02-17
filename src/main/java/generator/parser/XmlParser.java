package generator.parser;

import generator.parser.dto.Settings;
import org.apache.logging.log4j.LogManager;

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
    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(XmlParser.class.getName());
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
            return (Settings) jaxbUnmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            LOG.error("Ошибка при чтения файла XML", e);
        }
        return null;
    }
}
