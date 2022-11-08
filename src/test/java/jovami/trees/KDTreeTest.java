package jovami.trees;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * KDTreeTest
 */
public class KDTreeTest {

    int[] values = new int[] { 10, -12, +20, 440, 77, 25, 44 };
    int[] coordx = new int[] { -1, +44, -12,   0, 10, -5, +3 };
    int[] coordy = new int[] { +2,  +4,  -2,  -7, 11, -9, +8 };

    KDTree<Integer> tree;

    @BeforeEach
    void setUp() {
        tree = new KDTree<>();
        IntStream.range(0, values.length)
                 .forEach(i -> tree.insert(values[i], coordx[i], coordy[i]));
    }

    @Test
    void testNearestNeighbor(){
        int testCoordX[] = new int[] {  -5, +44, -12,  -2,  7, -5,  0 };
        int testCoordY[] = new int[] { +10,  +4,  -8,  -5, 10, -8, +6 };

        IntStream.range(0, values.length)
                .forEach(i -> {
                    assertEquals(values[i], tree.nearestNeighbor(testCoordX[i], testCoordY[i]), "KDTree has the wrong size!");
                });
    }

    @Test
    void testInsertNew() {
        // Override setUp()
        tree = new KDTree<>();
        assertEquals(0, tree.size());   // Empty tree => size 0

        IntStream.range(0, values.length)
                 .forEach(i -> {
                     tree.insert(values[i], coordx[i], coordy[i]);
                     assertEquals(i+1, tree.size(), "KDTree has the wrong size!");
                 });
    }

    @Test
    void testInsertRepeated() {
        // Tree was already filled with values[] in setUp()

        final int finalSize = tree.size();
        // Repeated values => Size does not alter
        IntStream.range(0, values.length)
            .forEach(i -> {
                tree.insert(values[i], coordx[i], coordy[i]);
                assertEquals(finalSize, tree.size(), "KDTree's size shouldn't have changed!");
            });
    }
}
