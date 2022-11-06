package jovami.util;

import java.util.Comparator;
import java.util.List;

import jovami.trees.AVL;

/**
 * Utils
 */
public class Utils {

    public static <E extends Comparable<E>> Iterable<E>
    avlSort(List<E> unsorted)
    {
        var avl = new AVL<E>();
        unsorted.forEach(avl::insert);
        return avl.inOrder();
    }

    public static <E> void
    mergeSort(List<E> unsorted, Comparator<? super E> cmp)
    {
        unsorted.sort(cmp);
    }
}
