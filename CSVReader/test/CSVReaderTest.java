import org.junit.Test;

import java.io.StringReader;
import java.util.Locale;

import static java.lang.StrictMath.exp;
import static org.junit.Assert.*;

public class CSVReaderTest {
    @Test
    public void next() throws Exception {

        CSVReader reader = new CSVReader("with-header.csv", ",", true);
        System.out.printf(reader.columnLabels.toString());
        while (reader.next()) {
            int err1 = 0;
            int err2 = 0;
            for (int i = 0; i < reader.columnLabels.size(); i++) {

                String str = reader.get(i);
                System.out.printf(Locale.US, str + " ");
                if (reader.isMissing(i)) {
                    assertEquals(str, "");
                }
                try {
                    System.out.print(reader.getDouble(170));
                } catch (Exception e) {
                    err1++;
                }

                try {

                    System.out.print(reader.getDouble("Paulina"));
                } catch (Exception e) {
                    err2++;
                }


            }

            System.out.printf("\n----------------------------------------------------------\n");
            assertEquals(reader.columnLabels.size(), err1);
            assertEquals(reader.columnLabels.size(), err2);

            assertEquals(reader.getInt("PassengerId"), Integer.parseInt(reader.current[reader.columnLabelsToInt.get("PassengerId")]));
            assertEquals(reader.get("Name"), reader.current[reader.columnLabelsToInt.get("Name")]);
            assertEquals(reader.getDouble("Fare"), Double.parseDouble(reader.current[reader.columnLabelsToInt.get("Fare")]), exp(-11));

        }
        String text = "a,b,c\n123.4,567.8,91011.12";
        reader = new CSVReader(new StringReader(text), ",", true);
        System.out.printf(reader.columnLabels.toString());
        while (reader.next()) {
            for (int i = 0; i < reader.columnLabels.size(); i++) {
                String str = reader.get(i);
                System.out.printf(Locale.US, str + " ");
                if (reader.isMissing(i)) {
                    assertEquals(str, "");
                }
            }
            System.out.printf("\n----------------------------------------------------------\n");
        }
    }

    @Test
    public void getColumnLabels() throws Exception {
    }

    @Test
    public void getRecordLength() throws Exception {
    }

    @Test
    public void isMissing() throws Exception {
    }

    @Test
    public void isMissing1() throws Exception {
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void get1() throws Exception {
    }

    @Test
    public void getInt() throws Exception {
    }

    @Test
    public void getInt1() throws Exception {
    }

    @Test
    public void getLong() throws Exception {
    }

    @Test
    public void getLong1() throws Exception {
    }

    @Test
    public void getDouble() throws Exception {
    }

    @Test
    public void getDouble1() throws Exception {
    }

}