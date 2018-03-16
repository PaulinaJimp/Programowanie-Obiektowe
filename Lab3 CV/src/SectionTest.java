import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class SectionTest {
    @org.junit.Test
    public void setTitle() throws Exception {
        String str = "newTitle";
        Section newSection = new Section("title").setTitle(str);
        assertTrue(newSection.title==str);
    }

    @org.junit.Test
    public void addParagraph() throws Exception {
        String str = "newSection";
        Section newSection = new Section("title");
        assertTrue(newSection.paragraps.isEmpty());
        newSection.addParagraph(str);
        assertTrue(!newSection.paragraps.isEmpty());
        assertTrue(newSection.paragraps.get(newSection.paragraps.size()-1).text==str);
    }

    @org.junit.Test
    public void addParagraph1() throws Exception {
        String str = "newSection";
        Paragraph s = new Paragraph(str);
        Paragraph ss = new Paragraph("other");
        Section newSection = new Section("title");
        assertTrue(newSection.paragraps.isEmpty());
        newSection.addParagraph(s);
        newSection.addParagraph(ss);
        assertTrue(!newSection.paragraps.isEmpty());
        assertTrue(newSection.paragraps.get(0).text==str);
        assertTrue(newSection.paragraps.get(1).text=="other");
    }


    @org.junit.Test
    public void writeHTML() throws Exception {
        String imageUrl = "an-kowaljski.png";
        // Utwórz strumień zapisujący w pamięci
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        // Utwórz obiekt i zapisz do strumienia
        Section a = new Section(imageUrl);
        a.writeHTML(ps);
        String result = null;
        // Pobierz jako String
        try {
            result = os.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println(result);

        // Sprawdź, czy result zawiera wybrane elementy
        assertTrue(result.contains("<h1"));
        assertTrue(result.contains("/h1>"));
        for (int j = 0; j < a.paragraps.size(); j++) {
            assertTrue(result.contains(a.paragraps.get(j).text));
        }
        assertTrue(result.contains(imageUrl));
    }

}