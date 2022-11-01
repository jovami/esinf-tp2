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

    protected E nearestNeightbor(KDNode<E> node, double x, double y, KDNode<E> closestNode, boolean divX) {
        if (node == null) {return null;}

        double d = Point2D.distanceSq(node.coords.x, node.coords.y, x, y);
        double closestDist = Point2D.distanceSq(closestNode.coords.x, closestNode.coords.y, x, y);

        if (closestDist > d){ closestNode = node;}

        double delta = divX ? x - node.coords.x : y - node.coords.y;
        double delta2 = delta * delta;

        Node<E> node1 = delta < 0 ? node.getLeft() : node.getRight();
        Node<E> node2 = delta < 0 ? node.getRight() : node.getLeft();
        return null;
    }

    private KDNode<E> insert(KDNode<E> currentNode, KDNode<E> node, boolean divX){
        if (node.coords.equals(currentNode.coords))
            return null;
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
