package jovami.exercises;

import static jovami.MainTest.*;
import static jovami.exercises.Exercise3DataSet.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import jovami.model.Value;
import jovami.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jovami.App;
import jovami.model.Area;
import jovami.util.Triplet;
import jovami.util.Utils;

/**
 * Exercise3Test
 */
public class Exercise3Test {

    App app;
    Exercise3 ex3;

    @BeforeEach
    void setup() {
        resetSingleton();

        loadFlags(flagsFile);
        loadCoords(coordsFile);
        loadShuffle(shuffleFile);
        loadItemCodes(itemFile);

        ex3 = new Exercise3();
        app = App.getInstance();
    }

    @Test
    void testGetListLastYearValues() {
        String itemCode = "393";
        String elementCode = "5510";



        ex3.getListLastYearValues(itemCode, elementCode);            //adds data to listBiggestValues

        for (int i = 0; i < ex3.listBiggestValues.size(); i++) {
            System.out.println(ex3.listBiggestValues.get(i).first().getAreaName());
            System.out.println(ex3.listBiggestValues.get(i).second().getValue().orElse(0.0f));
        }


        ArrayList<Pair<String, Float>> expected = new ArrayList<>();
        ArrayList<Pair<String, Float>> test = new ArrayList<>();


        expected.add(new Pair<>("Algeria", 38110.000000F));
        expected.add(new Pair<>("Belgium", 112706.000000F));
        expected.add(new Pair<>("France", 531844.000000F));
        expected.add(new Pair<>("Germany", 149164.000000F));
        expected.add(new Pair<>("Croatia", 85.000000F));


        for (Pair<Area, Value> areaValuePair : ex3.listBiggestValues) {
             String name = areaValuePair.first().getAreaName();
             Float value =areaValuePair.second().getValue().orElse(0.0f);
             test.add(new Pair<>(name,value));
        }

        assertEquals(expected, test);

    }


}