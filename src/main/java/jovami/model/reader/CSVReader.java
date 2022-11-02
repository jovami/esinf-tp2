package jovami.model.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


public class CSVReader {

    private final CSVHeader header;

    private final int EXPECTED_COLUMNS;

    private final String DEFAULT_DELIMITER;

    private static final char BOM = '\ufeff';

    public CSVReader(CSVHeader header) {
        // https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes/1757107#1757107
        // final String magicalRegex = "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

        this.header = header;
        this.EXPECTED_COLUMNS = header.getColumnCount();
        this.DEFAULT_DELIMITER = header.getDelimiter();
    }

    public List<String[]> readCSV(File dir) throws Exception {
        List<String[]> info = new ArrayList<>();
        String delimiter = DEFAULT_DELIMITER;

        String line;
        String[] tmp;

        try (var br = new BufferedReader(new FileReader(dir))) {
            maybeSkipBOM(br);
            line = br.readLine();

            if (!isHeader(line))
                throw new Exception("error: the header of the file is INVALID");
            boolean quotationMarks = checkQuotationMark(br);
            if (quotationMarks){
                delimiter = '"' + delimiter + '"';
            }
            while ((line = br.readLine()) != null) {
                tmp = line.split(delimiter);
                if (tmp.length != EXPECTED_COLUMNS) {
                    continue;
                    //throw new Exception(String.format("error: the csv file contains invalid data!\nOffending Line:\n\t%s", line));
                } else {
                    // remove " at begining and " at end
                    if(quotationMarks) {
                        tmp[0] = tmp[0].replaceAll("\"", "");
                        tmp[tmp.length - 1] = tmp[tmp.length - 1].replaceAll("\"", "");
                    }
                    info.add(tmp);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file. Aborting...");
        }

        return info;
    }

    private boolean isHeader(String line) {
        return line.trim().equalsIgnoreCase(this.header.toString());
    }

    /**
     * Some CSV files start with a BOM (Byte-Order-Mark) character,
     * which messes up with parsing the file
     * This method attempts to circumvent this issue by scanning the
     * first character of the stream being read and resetting the file
     * pointer to the beggining in case it isn't a BOM
     * @param reader
     * @throws IOException
     */
    private void maybeSkipBOM(Reader reader) throws IOException {
        reader.mark(1);
        char[] buf = new char[1];
        reader.read(buf);

        if (buf[0] != BOM)
            reader.reset();
    }

    private boolean checkQuotationMark(Reader reader) throws IOException {
        reader.mark(1);
        char[] buf = new char[1];
        reader.read(buf);
        reader.reset();
        return buf[0] == '"';
    }
}
