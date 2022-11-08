package jovami.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import jovami.trees.AVL;

/**
 * Utils
 */
public class Utils {

    public static <E extends Comparable<E>> List<E>
    avlSort(List<E> unsorted)
    {
        var avl = new AVL<E>();
        var ret = new ArrayList<E>();

        unsorted.forEach(avl::insert);
        avl.forEach(ret::add);

        return ret;
    }

    public static <E> List<E>
    avlSort(List<E> unsorted, Comparator<? super E> cmp)
    {
        var ret = new ArrayList<E>();
        var avl = new AVL<E>(cmp);

        unsorted.forEach(avl::insert);
        avl.forEach(ret::add);

        return ret;
    }

    public static <E> void
    mergeSort(List<E> unsorted, Comparator<? super E> cmp)
    {
        unsorted.sort(cmp);
    }
}
