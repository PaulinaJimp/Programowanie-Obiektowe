import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Section {

    @XmlAttribute(name="title")
    String title;
    @XmlElements(value= {
            @XmlElement(name = "paragraph", type = Paragraph.class),
            @XmlElement(name = "paragraph-with-list", type = ParagraphWithList.class)
    })
    List<Paragraph> paragraps = new ArrayList<>() ;

    Section(String newtitle)
    {
        this.title=newtitle;
    }
    Section(){this.title="";}
    Section setTitle(String title)
    {
        this.title=title;
        return this;
    }
    Section addParagraph(String paragraphText)
    {
        Paragraph p = new Paragraph(paragraphText);
        this.paragraps.add(p);
        return this;
    }
    Section addParagraph(Paragraph p)
    {
        this.paragraps.add(p);
        return this;
    }
    /*Section addParagraph(ParagraphWithList p)
    {
        this.paragraps.add(p);
        return this;
    }*/
    void writeHTML(PrintStream out)
    {
        out.print("<h1>"+this.title+"</h1>\n");
        for (int i = 0; i < paragraps.size(); i++)
            paragraps.get(i).writeHTML(out);
    }

}
