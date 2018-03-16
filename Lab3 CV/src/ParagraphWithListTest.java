import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class ParagraphWithListTest {
    @org.junit.Test
    public void setContent() throws Exception {
    }

    @org.junit.Test
    public void addListItem() throws Exception {
    }

    @org.junit.Test
    public void addListItem1() throws Exception {
    }

    @org.junit.Test
    public void writeHTML() throws Exception {
        String imageUrl = "an-kowaljski.png";
        // Utwórz strumień zapisujący w pamięci
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        // Utwórz obiekt i zapisz do strumienia
        new ParagraphWithList(imageUrl).writeHTML(ps);
        String result = null;
        // Pobierz jako String
        try {
            result = os.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println(result);

        // Sprawdź, czy result zawiera wybrane elementy
        assertTrue(result.contains("<p"));
        assertTrue(result.contains("/p>"));
        assertTrue(result.contains(imageUrl));
        assertTrue(result.contains("<ul"));
        assertTrue(result.contains("/ul>"));
    }

}