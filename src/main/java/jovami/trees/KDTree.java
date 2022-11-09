package jovami.trees;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.function.Consumer;

/**
 * KDTree
 *
 * @param <E> the type parameter
 */
public class KDTree<E extends Comparable<E>> implements KDInterface<E> {

    public static class KDNode<E>{
        protected Point2D.Double coords;
        private E element;          // an element stored at this node
        private KDNode<E> left;       // a reference to the left child (if any)
        private KDNode<E> right;      // a reference to the right child (if any)

        /**
         * Instantiates a new Kd node.
         *
         * @param e          the e
         * @param leftChild  the left child
         * @param rightChild the right child
         * @param x          the x
         * @param y          the y
         */
        public KDNode(E e, KDNode<E> leftChild, KDNode<E> rightChild, double x, double y) {
            this.element = e;
            this.left = leftChild;
            this.right = rightChild;
            this.coords = new Point2D.Double(x, y);
        }

        // checker methods
        public boolean hasElement() {
            return this.element != null;
        }
        public boolean hasLeft() {
            return this.left != null;
        }
        public boolean hasRight() {
            return this.right != null;
        }

        // accessor methods
        public E getElement() {
            return this.element;
        }
        public KDNode<E> getLeft() {
            return this.left;
        }
        public KDNode<E> getRight() {
            return this.right;
        }

        // update methods
        public void setElement(E e) {
            this.element = e;
        }
        public void setLeft(KDNode<E> leftChild) {
            this.left = leftChild;
        }
        public void setRight(KDNode<E> rightChild) {
            this.right = rightChild;
        }
    }
    //----------- end of nested Node class -----------

    protected KDNode<E> root;   //root

    /* Constructs an empty KDtree. */
    public KDTree() {
        root = null;
    }

    //----------- Comparators -----------
    private final Comparator<KDNode<E>> cmpX = Comparator.comparingDouble(p -> p.coords.x);

    private final Comparator<KDNode<E>> cmpY = Comparator.comparingDouble(p -> p.coords.y);

    /*
     * Verifies if the tree is empty
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    public int height() {
        return height(this.root);
    }

    protected int height(KDNode<E> node) {
        if (node == null)
            return -1;

        int left = height(node.getLeft());
        int right = height(node.getRight());

        return 1 + Math.max(left, right);
    }

    public int size() {
        return size(this.root);
    }

    protected int size(KDNode<E> node) {
        if (node == null)
            return 0;

        int left = size(node.getLeft());
        int right = size(node.getRight());

        return 1 + left + right;
    }

    /**
     * Insert.
     *
     * @param e the e
     * @param x the x
     * @param y the y
     */
    public void insert(E e, double x, double y){
        KDNode<E> node = new KDNode<>(e, null, null, x, y);
        this.root = insert(root, node, true);
    }

    /**
     * Insert kd node.
     *
     * @param currentNode the current node
     * @param node        the node
     * @param divX        the div x
     * @return the kd node
     */
    private KDNode<E> insert(KDNode<E> currentNode, KDNode<E> node, boolean divX){
        if (currentNode == null)
            return node;

        if (node.coords.equals(currentNode.coords)) {
            currentNode.setElement(node.element);
        } else {
            int cmpResult = (divX ? cmpX : cmpY).compare(node, currentNode);

            if (cmpResult < 0)
                currentNode.setLeft(insert(currentNode.getLeft(), node, !divX));
            else
                currentNode.setRight(insert(currentNode.getRight(), node, !divX));
        }

        return currentNode;
    }

    @Override
    public E nearestNeighbor(double x, double y) {
        KDNode<E>node = nearestNeighbor(root, x, y, root, true);
        return node.getElement();
    }

    /**
     * Nearest neighbor e.
     *
     * @param node        the node
     * @param x           the x
     * @param y           the y
     * @param closestNode the closest node
     * @param divX        the div x
     * @return the e
     */
    protected KDNode<E> nearestNeighbor(KDNode<E> node, double x, double y, KDNode<E> closestNode, boolean divX) {
        if (node == null)
            return closestNode;

        double d = Point2D.distanceSq(node.coords.x, node.coords.y, x, y);
        double closestDist = Point2D.distanceSq(closestNode.coords.x, closestNode.coords.y, x, y);

        if (d < closestDist)
            closestNode = node;

        double delta = divX ? x - node.coords.x : y - node.coords.y;
        double delta2 = delta * delta;

        KDNode<E> node1 = delta < 0 ? node.getLeft() : node.getRight();
        KDNode<E> node2 = delta < 0 ? node.getRight() : node.getLeft();

        closestNode = nearestNeighbor(node1, x, y, closestNode, !divX);


        if (delta2 < closestDist)
            closestNode = nearestNeighbor(node2, x, y, closestNode, !divX);
        return closestNode;

        // Best case: O(log n))
        // Worst case: O(n)
        // Deterministic algorithm
    }

  /**
     * This method receives:
     * @param x1    x inicial
     * @param y1    y inicial
     * @param x2    x final
     * @param y2    x final
     * @return List<E> with all the elements that were in between the area created by inicial and final coordinates
     */
    @Override
    public List<E> rangeSearch(double x1, double y1, double x2, double y2) {
        KDNode<E> node = this.root;                                                  //O(1)
        
        if(y2<y1){                                                                   //O(1)
            double backup = y2;
            y2 = y1;
            y1 = backup;
        }

        if(x2<x1){                                                                   //O(1)
            double backup = x2;
            x2 = x1;
            x1 = backup;
        }

        Point2D.Double coordInicial= new Point2D.Double(x1,y1);                       //O(1)
        Point2D.Double coordFinal= new Point2D.Double(x2,y2);                         //O(1)

        List<E> result = new LinkedList<>();                                          //O(1)

        searchArea(node, coordInicial,coordFinal, true,result::add);            //Worst time complexity O(n)

        return result;
    }


    private void searchArea(KDNode<E> node,Point2D.Double coordInicial,Point2D.Double coordFinal, boolean cmpX,Consumer<? super E> action ) {
        boolean outside=false;

        if (node==null){
            return;
        }

        double compareInicial= (cmpX ? node.coords.x - coordInicial.x : node.coords.y - coordInicial.y); // >=0 -> inside area
        double compareFinal= (cmpX ? node.coords.x - coordFinal.x : node.coords.y - coordFinal.y);  // <=0 -> outside area

 
        if (compareInicial < 0){   //node.coords is below inicial coords
            searchArea (node.getRight(),coordInicial, coordFinal, !cmpX,action);
            outside=true;
        }
        if (compareFinal > 0){      // node.coords is above final coords
            outside=true;
            searchArea (node.getLeft(),coordInicial, coordFinal, !cmpX,action);
        }
        if(!outside){
            // while between the area, we want to check all the possible nodes
            searchArea (node.getRight(),coordInicial, coordFinal, !cmpX,action);
            searchArea (node.getLeft(),coordInicial, coordFinal, !cmpX,action);

            if(isInside(node.coords, coordInicial, coordFinal))
                action.accept(node.getElement());
         
        }
    }

    public Iterable<E> inOrder() {
        List<E> snapshot = new ArrayList<>();
        inOrderForEach(snapshot::add);
        return snapshot;
    }

    /**
     * Performs an action over all elements of the tree, reported in-order.
     * @param action the action to perform
     * @throws NullPointerException if the specified action is null
     */
    public void inOrderForEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        if (this.root != null)
            inOrderSubtree(this.root, action);
    }

    public void inOrderSubtree(KDNode<E> node, Consumer<? super E> consumer) {
        if (node == null)
            return;
        inOrderSubtree(node.getLeft(), consumer);
        consumer.accept(node.getElement());
        inOrderSubtree(node.getRight(), consumer);
    }

    private boolean isInside(Point2D.Double nodeCoords,Point2D.Double coordInicial,Point2D.Double coordFinal){
        return ((nodeCoords.x >= coordInicial.x && nodeCoords.x <= coordFinal.x) && (nodeCoords.y >=coordInicial.y && nodeCoords.y <= coordFinal.y) ) ;
    }
}
