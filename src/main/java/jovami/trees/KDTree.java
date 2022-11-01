package jovami.trees;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.List;

/**
 * KDTree
 */
public class KDTree<E extends Comparable<E>> extends BST<E> implements KDInterface<E> {


    public static class KDNode<E> extends BST.Node <E>{
        protected Point2D.Double coords;

        /**
         * Constructs a node with the given element and neighbors.
         *
         * @param e          the element to be stored
         * @param leftChild  reference to a left child node
         * @param rightChild reference to a right child node
         */

        public KDNode(E e, Node<E> leftChild, Node<E> rightChild, double x, double y) {
            super(e, leftChild, rightChild);
            coords = new Point2D.Double(x, y);
        }
    }


    @Override
    public E nearestNeightbor(double x, double y) {

        return null;
    }

    @Override
    public List<E> kNearestNeighbors(double x, double y, int n) {
        return null;
    }

    @Override
    public List<E> rangeSearch(double x1, double y1, double x2, double y2) {
        return null;
    }
}
