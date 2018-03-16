
import javax.xml.bind.annotation.XmlValue;
import java.io.PrintStream;


public class Paragraph {


    @XmlValue
    String text;
    Paragraph(String newtext)
    {
        this.text=newtext;
    }
    Paragraph(){this.text="";}
    void writeHTML(PrintStream out)
    {
        out.print("<p>"+this.text+"</p>\n");
    }
}
