package jovami.util;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author DEI-ESINF
 */
public class BSTSortTest {

    public BSTSortTest() {
    }

    /**
     * Test of sortByBST method, of class BSTSort.
     */
    @Test
    public void testSortByBST() {
        List<String> lStrIni = Arrays.asList("rui","joao","carlos","filipe","berta","ze","cunha");
        List<String> lStrRes = Arrays.asList("berta","carlos","cunha","filipe","joao","rui","ze");

        List<Integer> lIntIni = Arrays.asList(12,4,8,2,4,5,78,1,6);
        List<Integer> lIntRes = Arrays.asList(1,2,4,5,6,8,12,78);

        assertEquals(lIntRes, BSTSort.sortByBST(lIntIni));
        assertEquals(lStrRes, BSTSort.sortByBST(lStrIni));
    }
}
