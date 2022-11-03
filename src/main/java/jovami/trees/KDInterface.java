package jovami.trees;

import java.util.List;

/**
 * KDInterface
 */
public interface KDInterface<E> extends BSTInterface<E> {

    public E nearestNeightbor(double x, double y);

    public List<E> kNearestNeighbors(double x, double y, int n);

    public List<E> rangeSearch(double x1, double y1, double x2, double y2);

}
