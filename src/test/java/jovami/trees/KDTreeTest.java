package jovami.trees;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
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
        int closests[] = new int[] { +44,  -12,  20,  440, 77, 25, 44 };

        IntStream.range(0, values.length)
                .forEach(i -> {
                    assertEquals(closests[i], tree.nearestNeighbor(testCoordX[i], testCoordY[i]));
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


    @Test
    void rangeSearch() {

        List<Integer> expected = new ArrayList<>();
        expected.add(10);
        expected.add(25);
        expected.add(440);
        expected.add(44);

        double x1 = -5;
        double x2 = 5;

        double y1 = -10;
        double y2 = 8;

        
        List<Integer> list = tree.rangeSearch(x1, y1, x2, y2);  
        for (Integer i : expected) {
            System.out.println("i:"+i);
            assertTrue(list.contains(i.intValue()));
            
        }
    }

    @Test
    void rangeSearchNullValues() {


        double x1 = 0;
        double x2 = 0;

        double y1 = 0;
        double y2 = 0;

        
        List<Integer> list = tree.rangeSearch(x1, y1, x2, y2);  
        assertTrue(list.isEmpty());
    } 
    
    
    @Test
    void rangeSearchChangedCoords() {

        List<Integer> expected = new ArrayList<>();
        expected.add(10);
        expected.add(440);
        expected.add(44);
        expected.add(25);

        double x1 = 5;
        double x2 = -6;

        double y1 = 8;
        double y2 = -10;

        
        List<Integer> list = tree.rangeSearch(x1, y1, x2, y2);  
        for (Integer i : expected) {
            System.out.println("i:"+i);
            assertTrue(list.contains(i.intValue()));
            
        }
    }
    
}
