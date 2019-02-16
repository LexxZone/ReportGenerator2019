package parser.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 2019-01-25
 *
 * @author Alexey Dvoryaninov  ( lexxzone@gmail.com )
 */

@XmlRootElement(name = "settings")
public class Settings {

    public Settings() {}

    @XmlElement(name = "page")
    private PageSettings page;

    @XmlElementWrapper(name = "columns")
    @XmlElement(name = "column")
    private List<ColumnSettings> columns;

    public PageSettings getPage() {
        return page;
    }

    public List<ColumnSettings> getColumns() {
        return columns;
    }
}
