package jovami.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 *
 * @author DEI-ESINF
 */
public class UtilsTest {

    @Test
    void testAVLSort() {
        var lStrIni = List.of("rui", "joao", "carlos", "filipe", "berta", "ze", "cunha");
        var lStrRes = List.of("berta", "carlos", "cunha", "filipe", "joao", "rui", "ze");

        var lIntIni = List.of(12,4,8,2,4,5,78,1,6);
        var lIntRes = List.of(1,2,4,5,6,8,12,78);

        assertEquals(lIntRes, Utils.avlSort(lIntIni));
        assertEquals(lStrRes, Utils.avlSort(lStrIni));
    }

    @Test
    void testAVLSortCmp() {
        var lStrIni = List.of("rui", "joao", "carlos", "filipe", "berta", "ze", "cunha");
        var lStrRes = List.of("ze", "rui", "joao", "filipe", "cunha", "carlos", "berta");

        var lIntIni = List.of(12, 4, 8, 2, 4, 5, 78, 1, 6);
        var lIntRes = List.of(78, 12, 8, 6, 5, 4, 2, 1);

        assertEquals(lIntRes, Utils.avlSort(lIntIni, Comparator.reverseOrder()));
        assertEquals(lStrRes, Utils.avlSort(lStrIni, Comparator.reverseOrder()));
    }
}
