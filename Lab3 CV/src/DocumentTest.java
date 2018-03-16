import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class DocumentTest {
    @org.junit.Test
    public void setTitle() throws Exception {

        String str = "newTitle";
        Document newDocument = new Document("title").setTitle(str);
        assertTrue(newDocument.title==str);
    }

    @org.junit.Test
    public void setPhoto() throws Exception {
        String str = "newPhoto.png";
        Document newDocument = new Document("title").setPhoto(str);
        assertTrue(newDocument.photo.url==str);
    }

    @org.junit.Test
    public void addSection() throws Exception {
        String str = "newSection.png";
        Document newDocument = new Document("title");
        assertTrue(newDocument.sections.isEmpty());
        newDocument.addSection(str);
        assertTrue(!newDocument.sections.isEmpty());
        assertTrue(newDocument.sections.get(newDocument.sections.size()-1).title==str);
    }

    @org.junit.Test
    public void addSection1() throws Exception {
        String str = "newSection";
        Section s = new Section(str);
        Section ss = new Section("other");
        Document newDocument = new Document("title");
        assertTrue(newDocument.sections.isEmpty());
        newDocument.addSection(s);
        newDocument.addSection(ss);
        assertTrue(!newDocument.sections.isEmpty());
        assertTrue(newDocument.sections.get(0).title==str);
        assertTrue(newDocument.sections.get(1).title=="other");
    }

    @org.junit.Test
    public void writeHTML() throws Exception {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        Document cv = new Document("Jana Kowalski - CV");
        cv.setPhoto("http://www.woodsandday.com.au/wp-content/uploads/2016/11/Female-Side-comb-O-neck-512.png");
        cv.addSection("Wykształcenie")
                .addParagraph("2000-2005 Przedszkole im. Królewny Snieżki w ...")
                .addParagraph("2006-2012 SP7 im Ronalda Regana w ...")
                .addParagraph("...");
        cv.addSection("Umiejętności")
                .addParagraph(
                        new ParagraphWithList().setContent("Umiejętności")
                                .addListItem("C")
                                .addListItem("C++")
                                .addListItem("Java")
                );

        cv.writeHTML(ps);
        String result = null;
        try {
            result = os.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //cv.writeHTML(ps);
        System.out.println(result);

        // Sprawdź, czy result zawiera wybrane elementy
        //assertTrue(result.contains("<!DOCTYPE"));
        assertTrue(result.contains(">"));
        assertTrue(result.contains("src="));
        assertTrue(result.contains(cv.title));
        assertTrue(result.contains(cv.photo.url));
        for (int i = 0; i < cv.sections.size(); i++) {
            assertTrue(result.contains(cv.sections.get(i).title));
            for (int j = 0; j < cv.sections.get(i).paragraps.size(); j++) {
                assertTrue(result.contains(cv.sections.get(i).paragraps.get(j).text));
            }
        }


    }

}