import javax.xml.bind.annotation.XmlElement;
import java.io.PrintStream;

public class ParagraphWithList extends Paragraph{

    //String title;
    @XmlElement(name = "list")
    UnorderedList items = new UnorderedList();

    ParagraphWithList(String newtext) {
        super(newtext);
    }

    public ParagraphWithList() {
        super("");
    }



    ParagraphWithList setContent(String newtext)
    {
        this.text=newtext;
        return this;
    }
    ParagraphWithList addListItem(String itemName)
    {
        ListItem i = new ListItem(itemName);
        this.items.additem(itemName);
        return this;
    }
    ParagraphWithList addListItem(ListItem i)
    {
        this.items.additem(i);
        return this;
    }

    void writeHTML(PrintStream out)
    {
        super.writeHTML(out);
        items.writeHTML(out);
    }
}
