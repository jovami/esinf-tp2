package jovami.trees;

import java.util.List;

/**
 * KDInterface
 */
public interface KDInterface<E> extends BSTInterface<E> {

    public void insert(E e, double x, double y);

    public E nearestNeighbor(double x, double y);

    public List<E> kNearestNeighbors(double x, double y, int n);

     //TODO rangeSearch recebe um consumer para executar para cada valor dentro da area?
    public List<E> rangeSearch(double x1, double y1, double x2, double y2);
     
}
