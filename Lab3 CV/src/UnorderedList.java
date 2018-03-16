import javax.xml.bind.annotation.XmlElement;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class UnorderedList {

    @XmlElement(name="item")
    List<ListItem> items = new ArrayList<>() ;
    UnorderedList additem(String itemName)
    {
        ListItem i = new ListItem(itemName);
        this.items.add(i);
        return this;
    }
    UnorderedList additem(ListItem i)
    {
        this.items.add(i);
        return this;
    }

    void writeHTML(PrintStream out)
    {
        out.print("<ul style=\"list-style-type:disc\">\n");
        for (int i = 0; i < items.size(); i++)
            items.get(i).writeHTML(out);
        out.print("</ul>\n");
    }
}
