import java.io.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;

    /**

     * @param delimiter - separator pól
     * @param hasHeader - czy plik ma wiersz nagłówkowy
     */

    public CSVReader(Reader reader, String delimiter, boolean hasHeader) throws IOException {
        this.reader = new BufferedReader(reader);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if (hasHeader) parseHeader();
    }

    public CSVReader(String filename, String delimiter, boolean hasHeader) throws IOException {
        this(new FileReader(filename), delimiter, hasHeader);
    }

    public CSVReader(String filename) throws IOException {
        this(filename, ",", false);
    }

    public CSVReader(String filename, String delimiter) throws IOException {
        this(filename, delimiter, false);
    }

    // nazwy kolumn w takiej kolejności, jak w pliku
    List<String> columnLabels = new ArrayList<>();
    // odwzorowanie: nazwa kolumny -> numer kolumny
    Map<String, Integer> columnLabelsToInt = new HashMap<>();

    private void parseHeader() throws IOException {

        String line = reader.readLine();
        if (line == null) {
            return;
        }
        String splitting = this.delimiter + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
        String[] header = line.split(splitting);

        for (int i = 0; i < header.length; i++) {

            columnLabels.add(header[i]);
            columnLabelsToInt.put(header[i], i);
        }
    }

    String[] current;

    boolean next() throws IOException {
        String line = reader.readLine();
        if (line == null) {
            return false;
        }
        String splitting = this.delimiter + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
        String[] text = line.split(splitting);
        current = text;
        return true;
    }

    List<String> getColumnLabels() {
        return columnLabels;
    }

    int getRecordLength() {
        return current.length;
    }

    boolean isMissing(int columnIndex) throws Exception {
        if (columnIndex > columnLabels.size() || columnIndex < 0) {
            throw new IndexOutOfBoundsException();
        }
        return current[columnIndex].equals("");
    }

    boolean isMissing(String columnLabel) throws Exception {
        if (!columnLabel.contains(columnLabel)) {
            throw new Exception();
        }
        return current[columnLabelsToInt.get(columnLabel)].equals("");
    }

    String get(int columnIndex) throws Exception {
        if (columnIndex > columnLabels.size() || columnIndex < 0) {
            throw new IndexOutOfBoundsException();
        }
        return current[columnIndex];
    }

    String get(String columnLabel) throws Exception {
        if (!columnLabel.contains(columnLabel)) {
            throw new Exception();
        }
        return current[columnLabelsToInt.get(columnLabel)];
    }

    int getInt(int columnIndex) throws Exception {
        if (columnIndex > columnLabels.size() || columnIndex < 0) {
            throw new IndexOutOfBoundsException();
        }
        return Integer.parseInt(current[columnIndex]);
    }

    int getInt(String columnLabel) throws Exception {
        if (!columnLabel.contains(columnLabel)) {
            throw new Exception();
        }
        return Integer.parseInt(current[columnLabelsToInt.get(columnLabel)]);
    }

    long getLong(int columnIndex) throws Exception {
        if (columnIndex > columnLabels.size() || columnIndex < 0) {
            throw new IndexOutOfBoundsException();
        }
        return Long.parseLong(current[columnIndex]);
    }

    long getLong(String columnLabel) throws Exception {
        if (!columnLabel.contains(columnLabel)) {
            throw new Exception();
        }
        return Long.parseLong(current[columnLabelsToInt.get(columnLabel)]);
    }

    double getDouble(int columnIndex) throws Exception {
        if (columnIndex > columnLabels.size() || columnIndex < 0) {
            throw new IndexOutOfBoundsException();
        }
        return Double.parseDouble(current[columnIndex]);
    }

    double getDouble(String columnLabel) throws Exception {
        if (!columnLabel.contains(columnLabel)) {
            throw new Exception();
        }
        return Double.parseDouble(current[columnLabelsToInt.get(columnLabel)]);
    }

    void getLocalDateTime(String pattern) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(pattern);
        df.setTimeZone(TimeZone.getDefault());
        if ((pattern.contains("y") || pattern.contains("M") || pattern.contains("d")) && (pattern.contains("H") || pattern.contains("m") || pattern.contains("s")))
            System.out.println("Date and time : " + df.format(date));
        else if (pattern.contains("y") || pattern.contains("M") || pattern.contains("d"))
            System.out.println("Date : " + df.format(date));
        else if (pattern.contains("H") || pattern.contains("m") || pattern.contains("s"))
            System.out.println("Time : " + df.format(date));
    }

    LocalTime getTime(String pattern) {
        return LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern(pattern)));
    }

    LocalDate getDate(String pattern) {
        return LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern(pattern)));
    }
    LocalDate getDate(int index, String pattern) throws Exception {
        return LocalDate.parse(this.get(index).format(String.valueOf(DateTimeFormatter.ofPattern(pattern))));
    }
    LocalTime getTime(int index, String pattern) throws Exception {
        return LocalTime.parse(this.get(index).format(String.valueOf(DateTimeFormatter.ofPattern(pattern))));
    }


}