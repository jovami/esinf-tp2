package jovami.exercises;

import static jovami.MainTest.*;
import static jovami.exercises.Exercise2DataSet.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import jovami.App;
import jovami.model.Area;
import jovami.util.Triplet;
import jovami.util.Utils;

/**
 * Exercise2Test
 */
public class Exercise2Test {

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

        final int expectedLines = 29;

        var unsorted = ex2.getAreaAverages(code, min, max);

        assertEquals(expectedLines, unsorted.size(), "Averages list with wrong size");
    }

    @Test
    @Disabled // TODO: add averages
    void testSortByAverage() {
        Optional<Area> belgium = app.getAreaTree().getAreaByAreaName("Belgium");
        assertTrue(belgium.isPresent());

        final var code = belgium.get().getAreaCode();
        final int min = 2000;
        final int max = 2001;

        final Float expectedAvgs[] = new Float[] {
        };

        var list = ex2.getAreaAverages(code, min, max);
        Utils.mergeSort(list, ex2.cmp);

        Float actualAvgs[] = list.stream().map(Triplet::third).toArray(Float[]::new);

        assertEquals(expectedAvgs.length, actualAvgs.length);
        assertArrayEquals(expectedAvgs, actualAvgs);
    }
}
