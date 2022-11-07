package jovami.exercises;

import static jovami.MainTest.*;
import static jovami.exercises.Exercise2DataSet.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jovami.App;
import jovami.model.Area;
import jovami.util.Triplet;
import jovami.util.Utils;

/**
 * Exercise2Test
 */
public class Exercise2Test {

    /* Filter needed data
     * awk -F"\",\"" 'NR > 1 && $3 ~ "Belgium"' Production_Crops_Livestock_World_shuffle_small.csv > belgium.csv
     */

    App app;
    Exercise2 ex2;

    @BeforeEach
    void setup() {
        resetSingleton();

        loadFlags(flagsFile);
        loadCoords(coordsFile);
        loadShuffle(shuffleFile);

        ex2 = new Exercise2();
        app = App.getInstance();
    }


    @Test
    void testNoSuchArea() {
        final int areaCode = -24;
        final int min = Integer.MIN_VALUE;  // any year
        final int max = Integer.MAX_VALUE;  // any year

        assertThrows(NoSuchElementException.class, () -> {
            ex2.getAreaAverages(Integer.toString(areaCode), min, max);
        });
    }

    @Test
    void testInvalidInterval() {
        Optional<Area> belgium = app.getAreaTree().getAreaByAreaName("Belgium");
        assertTrue(belgium.isPresent());

        final var areaCode = belgium.get().getAreaCode();
        final int min = 1940;
        final int maxBad = min - 1;

        assertThrows(IllegalArgumentException.class, () -> {
            ex2.getAreaAverages(areaCode, min, maxBad);
        });
    }

    @Test
    void testNoData() {
        Optional<Area> belgium = app.getAreaTree().getAreaByAreaName("Belgium");
        assertTrue(belgium.isPresent());

        final var code = belgium.get().getAreaCode();

        // Impossible interval (we're in 2022)
        final int min = Integer.MAX_VALUE - 1;
        final int max = Integer.MAX_VALUE;

        final int expectedLines = 0;

        var unsorted = ex2.getAreaAverages(code, min, max);

        assertEquals(expectedLines, unsorted.size(), "Averages list with wrong size");
    }

    @Test
    void testListSize() {
        Optional<Area> belgium = app.getAreaTree().getAreaByAreaName("Belgium");
        assertTrue(belgium.isPresent());

        final var code = belgium.get().getAreaCode();
        final int min = 2000;
        final int max = 2001;

        /* awk -F"\",\"" 'NR > 1 && $10 >= 2000 && $10 <= 2001 { print $4, $7 }' belgium.csv | sort -u | uniq | wc -l */
        final int expectedLines = 29;

        var unsorted = ex2.getAreaAverages(code, min, max);

        assertEquals(expectedLines, unsorted.size(), "Averages list with wrong size");
    }

    @Test
    void testSortByAverage() {
        Optional<Area> belgium = app.getAreaTree().getAreaByAreaName("Belgium");
        assertTrue(belgium.isPresent());

        final var code = belgium.get().getAreaCode();
        final int min = 2000;
        final int max = 2001;


        /*
            awk -F"\",\"" 'NR > 1 && $10 >= 2000 && $10 <= 2001 { print $12 }' belgium.csv |
                sort -u | while read num; do
                    if echo "${num}" | grep -Eq '^500(.0+)?$'; then
                        echo "${num}F,"
                    else
                        lua -e "print(${num} / 2 .. 'F,')"
                    fi
                done | sort -ruV
        */
        final Float expectedAvgs[] = new Float[] {
            5640100.0F,
            1518880.0F,
            511500.0F,
            282679.5F,
            206589.5F,
            142625.0F,
            80000.0F,
            46650.0F,
            45595.2F,
            43500.0F,
            31100.0F,
            30200.0F,
            22500.0F,
            15833.5F,
            14500.0F,
            11473.5F,
            4600.0F,
            3361.5F,
            1650.0F,
            1633.0F,
            500.0F,
            150.0F,
            125.0F,
            97.5F,
            38.5F,
            36.19F,
            24.5F,
            18.5F,
            5.0F,
        };

        var list = ex2.getAreaAverages(code, min, max);
        var copy = new ArrayList<>(list);

        {   // with mergeSort()
            Utils.mergeSort(list, ex2.cmp);

            Float actualAvgs[] = list.stream().map(Triplet::third).toArray(Float[]::new);

            assertEquals(expectedAvgs.length, actualAvgs.length);
            assertArrayEquals(expectedAvgs, actualAvgs);
        }

        {   // with avlSort()
            var res = Utils.avlSort(copy, ex2.cmp);

            Float actualAvgs[] = res.stream().map(Triplet::third).toArray(Float[]::new);

            assertEquals(expectedAvgs.length, actualAvgs.length);
            assertArrayEquals(expectedAvgs, actualAvgs);
        }
    }
}
