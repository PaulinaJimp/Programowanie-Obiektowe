import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {


    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        {
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

            cv.writeHTML(new PrintStream("cv.html","UTF-8"));
            cv.write("cv.xml");
            Document cv2 = Document.read("cv.xml");
            cv2.writeHTML(new PrintStream("cv2.html","UTF-8"));
            cv2.writeHTML(System.out);
        }
    }
}