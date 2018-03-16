import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement

public class Document {
    @XmlElement(name = "title")
    String title;
    @XmlElement
    Photo photo = new Photo();
    @XmlElement
    List<Section> sections = new ArrayList<>();
    Document(String newtitle)
    {
        this.title=newtitle;
    }
    Document() {this.title="";}
    Document setTitle(String title){
        this.title = title;
        return this;
    }

    Document setPhoto(String photoUrl){
        this.photo.url = photoUrl;
        return this;
    }

   Section addSection(String sectionTitle){
        Section s = new Section(sectionTitle);
        this.sections.add(s);
        return s;
    }
    Document addSection(Section s){
        this.sections.add(s);

        return this;
    }

    public void write(String fileName){
        try {
            JAXBContext jc = JAXBContext.newInstance(Document.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            FileWriter writer= new FileWriter(fileName);;
            m.marshal(this, writer);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public static Document read(String fileName){
        try {
            JAXBContext jc = JAXBContext.newInstance(Document.class);
            Unmarshaller m = jc.createUnmarshaller();
            FileReader reader = new FileReader(fileName);
            return (Document) m.unmarshal(reader);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    void writeHTML(PrintStream out){
        out.print("<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" +
                "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<title>"+this.title+"</title>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml;\n" +
                "charset=UTF-8\" />\n" +
                "</head>");
        out.print("<h1>"+this.title+"</h1>\n");
        this.photo.writeHTML(out);
        for (int i = 0; i < sections.size(); i++)
            sections.get(i).writeHTML(out);
        out.print("</body>\n" + "</html>\n");
    }


}
