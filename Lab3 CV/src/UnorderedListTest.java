import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class UnorderedListTest {
    @org.junit.Test
    public void additem() throws Exception {
        String str = "newSection.png";
        UnorderedList newitem = new UnorderedList();
        assertTrue(newitem.items.isEmpty());
        newitem.additem(str);
        assertTrue(!newitem.items.isEmpty());
        assertTrue(newitem.items.get(newitem.items.size()-1).name==str);
    }

    @org.junit.Test
    public void additem1() throws Exception {
        String str = "newSection";
        ListItem s = new ListItem(str);
        ListItem ss = new ListItem("other");
        UnorderedList newList = new UnorderedList();
        assertTrue(newList.items.isEmpty());
        newList.additem(s);
        newList.additem(ss);
        assertTrue(!newList.items.isEmpty());
        assertTrue(newList.items.get(0).name==str);
        assertTrue(newList.items.get(1).name=="other");

    }

    @org.junit.Test
    public void writeHTML() throws Exception {
        // Utwórz strumień zapisujący w pamięci
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        // Utwórz obiekt i zapisz do strumienia
       UnorderedList a =  new UnorderedList();
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
        assertTrue(result.contains("<ul"));
        assertTrue(result.contains("/ul>"));
        for (int i = 0; i < a.items.size(); i++) {
            assertTrue(result.contains(a.items.get(i).name));
        }


    }

}