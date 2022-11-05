package jovami.trees;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * KDTree
 *
 * @param <E> the type parameter
 */
public class KDTree<E extends Comparable<E>> extends BST<E> implements KDInterface<E> {

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

    private KDNode<E> root;

    public KDTree(){
        root = null;
    }

    private final Comparator<KDNode<E>> cmpX = Comparator.comparingDouble(p -> p.coords.x);

    private final Comparator<KDNode<E>> cmpY = Comparator.comparingDouble(p -> p.coords.y);

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
    public KDNode<E> insert(KDNode<E> currentNode, KDNode<E> node, boolean divX){
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

        return node;
    }

    @Override
    public E nearestNeighbor(double x, double y) {
        return nearestNeighbor(root, x, y, root, true);
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
    protected E nearestNeighbor(KDNode<E> node, double x, double y, KDNode<E> closestNode, boolean divX) {
        if (node == null)
            return null;

        double d = Point2D.distanceSq(node.coords.x, node.coords.y, x, y);
        double closestDist = Point2D.distanceSq(closestNode.coords.x, closestNode.coords.y, x, y);

        if (closestDist > d)
            closestNode = node;

        double delta = divX ? x - node.coords.x : y - node.coords.y;
        double delta2 = delta * delta;

        KDNode<E> node1 = delta < 0 ? node.getLeft() : node.getRight();
        KDNode<E> node2 = delta < 0 ? node.getRight() : node.getLeft();

        nearestNeighbor(node1, x, y, closestNode, !divX);
        if (delta2 < closestDist)
            nearestNeighbor(node2, x, y, closestNode, !divX);
        return closestNode.getElement();
    }

    @Override
    public List<E> rangeSearch(KDNode<E> node,Point2D.Double coordInicial,Point2D.Double coordFinal, Boolean cmpX) {
        
        List<E> result = new LinkedList<>();// ou Iterable
        searchArea(node, coordInicial,coordFinal, cmpX,result::add);
        return result;
    }


    private void searchArea(KDNode<E> node,Point2D.Double coordInicial,Point2D.Double coordFinal, Boolean cmpX,Consumer<? super E> action ) {

        if (node==null){ //Se o node não tiver um valor n faz nada
            return;
        }


        if(cmpX){//se tivermos num nivel em que temos de comparar o x

            if(node.coords.x<coordInicial.x ){//se n estiver dentro da area
                searchArea (node.getRight(),coordInicial, coordFinal, !cmpX,action);    //procura de um valor maior que x inicial
            }else{ // se estiver dentro da area

                if (node.coords.x<=coordFinal.x){//verificar se nao ultrapassou a coord final
                    //enquanto estiver dentro da area, quero verificar todos os valores para a direita e para a esquerda
                    searchArea (node.getRight(),coordInicial, coordFinal, !cmpX,action);    
                    searchArea (node.getLeft(),coordInicial, coordFinal, !cmpX,action);    
                    action.accept(node.getElement());
                }else{
                    return;
                    //se tiver limpo a area entre x inicial e x final, n fazer nada?
                    //rangeSearch (node.getRight(),coordInicial, coordFinal, !cmpX);
                }
            }

        }else{ 

            if(node.coords.y<coordInicial.y ){//se n estiver dentro da area
                searchArea(node.getRight(),coordInicial, coordFinal, !cmpX,action);    //procura de um valor maior que x inicial

            }else{ // se estiver dentro da area

                if (node.coords.y<=coordFinal.y){//verificar se nao ultrapassou a coord final
                    searchArea (node.getRight(),coordInicial, coordFinal, !cmpX,action);    
                    searchArea (node.getLeft(),coordInicial, coordFinal, !cmpX,action);    
                    
                    action.accept(node.getElement());
                    
                }else{
                    return;
                    //se tiver fora da area entre y inicial e  final, n fazer nada?
                    //rangeSearch (node.getRight(),coordInicial, coordFinal, !cmpX);
                }
                
            }
        }
    }



/*
        List<E> rangeSearch(KDNode<E> node, boolean divX) {
            if (node == null)
                return result;

            if (radius >= d)
                result.add(node.object);
            if
            double delta = divX ? x - node.coords.x : y - node.coords.y;
            double delta2 = delta * delta;
            Node<E> node1 = delta < 0 ? node.left : node.right;
            Node<E> node2 = delta < 0 ? node.right : node.left;
            rangeSearch(node1, !divX);
            if (delta2 < radius) {
                rangeSearch(node2, !divX);
            }*/
    @Override
    public List<E> kNearestNeighbors(double x, double y, int n) {
        throw new UnsupportedOperationException("Not implemented!");
    }
}
