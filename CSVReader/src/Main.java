import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Main {


    public static void main(String[] args) throws Exception {
        {
            CSVReader reader = new CSVReader("with-header.csv", ",", true);
            while (reader.next()) {
                int id = reader.getInt("PassengerId");
                String name = reader.get("Name");
                double fare = reader.getDouble("Fare");

                System.out.printf(Locale.US, "%d %s %f\n", id, name, fare);
            }

        }
        CSVReader reader = new CSVReader("with-header.csv", ",", true);
        while (reader.next()) {
            int id = reader.getInt(0);
            String name = reader.get(3);
            double fare = reader.getDouble(9);
            System.out.printf(Locale.US, "%d %s %f \n", id, name, fare);

        }


        reader.getLocalDateTime("yyyy-MM-dd HH:mm:ss");
        reader.getLocalDateTime("yyyy-MM-dd");
        reader.getLocalDateTime("HH:mm:ss");


        System.out.println(reader.getTime("HH:mm:ss"));
        System.out.println(reader.getDate("yyyy-MM-dd"));


    }
}