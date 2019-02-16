package parser.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 2019-01-26
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */
@XmlRootElement(name = "page")
public class PageSettings {

    public PageSettings() {}

    @XmlElement(name = "height")
    private int height;

    @XmlElement(name = "width")
    private int width;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
