package jovami.trees;

import java.util.List;
import java.util.function.Consumer;
import java.awt.geom.Point2D;
import jovami.trees.KDTree.KDNode;

/**
 * KDInterface
 */
public interface KDInterface<E> extends BSTInterface<E> {


    public E nearestNeighbor(double x, double y);

    public List<E> kNearestNeighbors(double x, double y, int n);

    public List<E> rangeSearch(KDNode<E> node,Point2D.Double inicial,Point2D.Double coordFinal, Boolean cmpX) ;
     
}
