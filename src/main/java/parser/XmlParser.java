package parser;

import parser.dto.Settings;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * 2019-01-25
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
public class XmlParser {
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
            e.printStackTrace();
        }
        return null;
    }
}
