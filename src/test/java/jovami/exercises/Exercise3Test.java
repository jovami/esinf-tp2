package jovami.exercises;

import static jovami.MainTest.*;
import static jovami.exercises.Exercise3DataSet.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import jovami.model.Value;
import jovami.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jovami.App;
import jovami.model.Area;

/**
 * Exercise3Test
 */
public class Exercise3Test {

    App app;
    Exercise3 ex3;
    ArrayList<Pair<String, Float>> test = new ArrayList<>();
    ArrayList<Pair<Area, Value>> testSort = new ArrayList<>();
    ArrayList<Pair<String, Float>> expected = new ArrayList<>();
    ArrayList<Pair<String, Float>> listTopN = new ArrayList<>();


    @BeforeEach
    void setup() {
        resetSingleton();

        loadFlags(flagsFile);
        loadCoords(coordsFile);
        loadShuffle(shuffleFile);
        loadItemCodes(itemFile);

        ex3 = new Exercise3();
        app = App.getInstance();

        String itemCode = "393";
        String elementCode = "5510";

        ex3.getListLastYearValues(itemCode, elementCode);            //adds data to listBiggestValues

        for (Pair<Area, Value> areaValuePair : ex3.listBiggestValues) {
            String name = areaValuePair.first().getAreaName();
            Float value = areaValuePair.second().getValue().orElse(0.0F);
            test.add(new Pair<>(name, value));
            testSort.add(new Pair<>(areaValuePair.first(), areaValuePair.second()));
        }
    }

    @Test
    void testGetListLastYearValues() {

        expected.add(new Pair<>("Algeria", 38110.000000F));
        expected.add(new Pair<>("Belgium", 112706.000000F));
        expected.add(new Pair<>("France", 531844.000000F));
        expected.add(new Pair<>("Germany", 149164.000000F));

        assertEquals(expected.size(), test.size());
        for (int i = 0; i < test.size(); i++) {
            assertEquals(expected.get(i), test.get(i));
        }
    }

    @Test
    void testSortListDescending() {
        expected.add(new Pair<>("France", 531844.000000F));
        expected.add(new Pair<>("Germany", 149164.000000F));
        expected.add(new Pair<>("Belgium", 112706.000000F));
        expected.add(new Pair<>("Algeria", 38110.000000F));
        ex3.sortListDescending(testSort);

        assertEquals(expected.size(), testSort.size());
        for (int i = 0; i < testSort.size(); i++) {
            assertEquals(expected.get(i).first(), testSort.get(i).first().getAreaName());
            assertEquals(expected.get(i).second(), testSort.get(i).second().getValue().orElse(0.0F));
        }
    }

    @Test
    void testAddTopNValues() {
        int topNumArea = 3;
        expected.add(new Pair<>("France", 531844.000000F));
        expected.add(new Pair<>("Germany", 149164.000000F));
        expected.add(new Pair<>("Belgium", 112706.000000F));
        ex3.addTopNValuesToList(topNumArea);

        assertEquals(expected.size(), ex3.listTopN.size());
        for (int i = 0; i < listTopN.size(); i++) {
            assertEquals(expected.get(i).first(), listTopN.get(i).first());
            assertEquals(expected.get(i).second(), listTopN.get(i).second());
        }
    }


}