package jovami;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jovami.exercises.Exercise1;
import jovami.model.reader.CSVHeader;

public class MainTest {

    public static void beforeEach() {
        resetSingleton();

        new Exercise1().readAllFiles();
    }

    public static void resetSingleton() {
        try {
            Field instance = App.class.getDeclaredField("singleton");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            e.printStackTrace(); // Should not happen
        }
    }


    private static final String DEFAULT_DELIM = ",";

    private static List<String[]> readDataFromStrings(String dataSet[], final int expectedColumns) {
        var ret = new ArrayList<String[]>(dataSet.length);

        boolean quotes = false;

        String delim = DEFAULT_DELIM;
        if (dataSet[0].charAt(0) == '"') {
            delim = '"' + delim + '"';
            quotes = true;
        }

        for (String data : dataSet) {
            String tmp[] = data.split(delim);

            if (tmp.length == expectedColumns) {
                if (quotes) {
                    tmp[0] = tmp[0].replaceAll("\"", "");
                    tmp[tmp.length - 1] = tmp[tmp.length - 1].replaceAll("\"", "");
                }
                ret.add(tmp);
            }
        }

        return ret;
    }

    public static void loadFlags(String dataSet[]) {
        var data = readDataFromStrings(dataSet, CSVHeader.HEADER_FLAGS.getColumnCount());

        (new Exercise1()).saveInfoFlags(data);
    }

    public static void loadCoords(String dataSet[]) {
        var data = readDataFromStrings(dataSet, CSVHeader.HEADER_AREACOORDINATES.getColumnCount());

        (new Exercise1()).saveInfoAreaCoordinates(data);
    }

    public static void loadItemCodes(String dataSet[]) {
        var data = readDataFromStrings(dataSet, CSVHeader.HEADER_ITEMCODES.getColumnCount());

        (new Exercise1()).saveInfoItemCodes(data);
    }

    public static void loadShuffle(String dataSet[]) {
        var data = readDataFromStrings(dataSet, CSVHeader.HEADER_SHUFFLE.getColumnCount());

        (new Exercise1()).saveInfoShuffle(data);
    }
}
