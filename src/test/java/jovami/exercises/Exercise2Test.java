package jovami.exercises;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import jovami.MainTest;

/**
 * Exercise2Test
 */
@TestInstance(Lifecycle.PER_CLASS)
public class Exercise2Test {

    Exercise2 ex2;

    @BeforeAll
    void setup() {
        MainTest.beforeEach();
        ex2 = new Exercise2();
    }


    @Test
    void testNoSuchArea() {
        final int areaCode = -24;
        final int min = 0;      // any year
        final int max = 3000;   // any year

        assertThrows(NoSuchElementException.class, () -> {
            ex2.getAreaAverages(Integer.toString(areaCode), min, max);
        });
    }

    @Test
    void testInvalidInterval() {
        final int areaCode = 174;
        final int min = 1940;
        final int maxBad = min - 1;

        assertThrows(IllegalArgumentException.class, () -> {
            ex2.getAreaAverages(Integer.toString(areaCode), min, maxBad);
        });
    }
}
