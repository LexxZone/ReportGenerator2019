package generator.parser.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 2019-01-26
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
@XmlRootElement(name = "column")
public class ColumnSettings {

    public ColumnSettings() {}

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "width")
    private int width;

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }
}
