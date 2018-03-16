
import javax.xml.bind.annotation.XmlValue;
import java.io.PrintStream;

public class ListItem {

    @XmlValue
    String name;
    ListItem(String newname){this.name = newname;}
    ListItem(){this.name= "";}

    void writeHTML(PrintStream out)
    {
        out.print("\t<li>"+this.name+"</li>\n");
    }
}
