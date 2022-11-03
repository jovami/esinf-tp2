package jovami.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 *
 * @author DEI-ESINF
 */
public class BST<E extends Comparable<E>> implements BSTInterface<E> {

    /** Nested static class for a binary search tree node. */
    protected static class Node<E> {
        private E element;          // an element stored at this node
        private Node<E> left;       // a reference to the left child (if any)
        private Node<E> right;      // a reference to the right child (if any)

        /**
         * Constructs a node with the given element and neighbors.
         *
         * @param e  the element to be stored
         * @param leftChild   reference to a left child node
         * @param rightChild  reference to a right child node
         */
        public Node(E e, Node<E> leftChild, Node<E> rightChild) {
            this.element = e;
            this.left = leftChild;
            this.right = rightChild;
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
        public Node<E> getLeft() {
            return this.left;
        }
        public Node<E> getRight() {
            return this.right;
        }

        // update methods
        public void setElement(E e) {
            this.element = e;
        }
        public void setLeft(Node<E> leftChild) {
            this.left = leftChild;
        }
        public void setRight(Node<E> rightChild) {
            this.right = rightChild;
        }

        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }
    }
    //----------- end of nested Node class -----------

    protected Node<E> root = null;     // root of the tree

    /* Constructs an empty binary search tree. */
    public BST() {
        this.root = null;
    }

    /*
     * @return root Node of the tree (or null if tree is empty)
     */
    protected Node<E> root() {
        return this.root;
    }

    /*
     * Verifies if the tree is empty
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    public Node<E> find(E element) {
        return find(root,element);
    }

    /**
     * Searches for the given element in the tree, returning an {@code Optional}
     * describing the result of the search
     *
     * @param element the element to search for
     * @return an optional describing the result
     */
    public Optional<E> find(E element) {
        E ret = null;

        Node<E> node = this.find(this.root, element);
        if (node != null)
            ret = node.getElement();

        return Optional.ofNullable(ret);
    }

    /**
     * Returns the Node containing a specific Element, or null otherwise.
     *
     * @param element    the element to find
     * @return the Node that contains the Element, or null otherwise
     *
     * This method despite not being essential is very useful.
     * It is written here in order to be used by this class and its
     * subclasses avoiding recoding.
     * So its access level is protected
     */
    protected Node<E> find(Node<E> node, E element) {
        boolean found = false;

        while(node != null && !found) {
            int result = Integer.signum(element.compareTo(node.getElement()));

            switch (result) {
                case -1:
                    node = node.getLeft();
                    break;
                case 1:
                    node = node.getRight();
                default:
                    found = true;
                    break;
            }
        }

        return node;
    }

    /*
     * Inserts an element in the tree.
     */
    public void insert(E element) {
        root = insert(element,root());
    }

    private Node<E> insert(E element, Node<E> node) {
        if(node == null)
            return new Node<E>(element,null,null);

        if(node.getElement().compareTo(element) == 0)
            node.setElement(element);
        else if(node.getElement().compareTo(element) > 0)
            node.setLeft(insert(element, node.getLeft()));
        else
            node.setRight(insert(element, node.getRight()));

        return node;
    }

    /**
     * Removes an element from the tree maintaining its consistency as a Binary Search Tree.
     */
    public void remove(E element) {
        this.root = remove(element, root());
    }

    private Node<E> remove(E element, Node<E> node) {

        if (node == null) {
            return null;
        }
        if (element.compareTo(node.getElement())==0) {
            // node is the Node to be removed
            if (node.isLeaf()) {
                return null;
            }
            if (node.getLeft() == null) {   //has only right child
                return node.getRight();
            }
            if (node.getRight() == null) {  //has only left child
                return node.getLeft();
            }
            E min = smallestElement(node.getRight());
            node.setElement(min);
            node.setRight(remove(min, node.getRight()));
        }
        else if (element.compareTo(node.getElement()) < 0)
            node.setLeft( remove(element, node.getLeft()) );
        else
            node.setRight( remove(element, node.getRight()) );

        return node;
    }

    /*
     * Returns the number of nodes in the tree.
     * @return number of nodes in the tree
     */
    public int size() {
        return size(this.root);
    }

    private int size(Node<E> node) {
        if (node == null)
            return 0;

        int left = size(node.getLeft());
        int right = size(node.getRight());

        return 1 + left + right;
    }

    /*
     * Returns the height of the tree
     * @return height
     */
    public int height() {
        return height(this.root);
    }

    /*
     * Returns the height of the subtree rooted at Node node.
     * @param node A valid Node within the tree
     * @return height
     */
    protected int height(Node<E> node) {
        if (node == null)
            return -1;

        int left = height(node.getLeft());
        int right = height(node.getRight());

        return 1 + Math.max(left, right);
    }

    /**
     * Returns the smallest element within the tree.
     * @return the smallest element within the tree
     */
    public E smallestElement() {
        return smallestElement(root);
    }

    protected E smallestElement(Node<E> node) {

        if(node == null)
            return null;
        if(node.getLeft() == null)
            return node.getElement();
        else
            return smallestElement(node.getLeft());

    }

    /*
     * Returns an iterable collection of elements of the tree, reported in in-order.
     * @return iterable collection of the tree's elements reported in in-order
     */
    public Iterable<E> inOrder() {
        List<E> snapshot = new ArrayList<>();
        if (root!=null)
            inOrderSubtree(root, snapshot);   // fill the snapshot recursively
        return snapshot;
    }

    /**
     * Adds elements of the subtree rooted at Node node to the given
     * snapshot using an in-order traversal
     * @param node       Node serving as the root of a subtree
     * @param snapshot  a list to which results are appended
     */
    private void inOrderSubtree(Node<E> node, List<E> snapshot) {
        if (node == null)
            return;
        inOrderSubtree(node.getLeft(), snapshot);
        snapshot.add(node.getElement());
        inOrderSubtree(node.getRight(), snapshot);
    }

    /**
     * Returns an iterable collection of elements of the tree, reported in pre-order.
     * @return iterable collection of the tree's elements reported in pre-order
     */
    public Iterable<E> preOrder() {
        List<E> snapshot = new ArrayList<>();
        if (root!=null)
            preOrderSubtree(root, snapshot);   // fill the snapshot recursively
        return snapshot;
    }

    /**
     * Adds elements of the subtree rooted at Node node to the given
     * snapshot using an pre-order traversal
     * @param node       Node serving as the root of a subtree
     * @param snapshot  a list to which results are appended
     */
    private void preOrderSubtree(Node<E> node, List<E> snapshot) {
        if (node == null)
            return;
        snapshot.add(node.getElement());
        preOrderSubtree(node.getLeft(), snapshot);
        preOrderSubtree(node.getRight(), snapshot);
    }

    /**
     * Returns an iterable collection of elements of the tree, reported in post-order.
     * @return iterable collection of the tree's elements reported in post-order
     */
    public Iterable<E> posOrder() {
        List<E> snapshot = new ArrayList<>();
        if (root!=null)
            posOrderSubtree(root, snapshot);   // fill the snapshot recursively
        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at Node node to the given
     * snapshot using an post-order traversal
     * @param node       Node serving as the root of a subtree
     * @param snapshot  a list to which results are appended
     */
    private void posOrderSubtree(Node<E> node, List<E> snapshot) {
        if (node == null)
            return;
        posOrderSubtree(node.getLeft(), snapshot);
        posOrderSubtree(node.getRight(), snapshot);
        snapshot.add(node.getElement());
    }

    /*
     * Returns a map with a list of nodes by each tree level.
     * @return a map with a list of nodes by each tree level
     */
    public Map<Integer,List<E>> nodesByLevel() {

        Map<Integer, List<E>> result = new HashMap<>();
        processBstByLevel(root, result, 0);

        return result;

    }

    private void processBstByLevel(Node<E> node, Map<Integer,List<E>> result, int level) {
        if (node == null) {
            return;
        }

        if (!result.containsKey(level)) {
            result.put(level, new ArrayList<E>());
        }
        List<E> list = result.get(level);
        list.add(node.getElement());

        processBstByLevel(node.getLeft(), result, level + 1);
        processBstByLevel(node.getRight(), result, level + 1);
    }

    //#########################################################################

    /**
     * Returns a string representation of the tree.
     * Draw the tree horizontally
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringRec(root, 0, sb);
        return sb.toString();
    }

    private void toStringRec(Node<E> root, int level, StringBuilder sb) {
        if (root == null)
            return;

        toStringRec(root.getRight(), level+1, sb);

        if (level != 0) {
            for(int i = 0; i < level-1; i++)
                sb.append("|\t");
            sb.append("|-------"+root.getElement()+"\n");
        } else {
            sb.append(root.getElement()+"\n");
        }

        toStringRec(root.getLeft(), level+1, sb);
    }

}
