package jovami.trees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;


/**
 *
 * @author DEI-ESINF
 */
public class BST<E> implements BSTInterface<E>, Iterable<E> {

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

    protected Comparator<? super E> cmp;

    /** Constructs an empty binary search tree using the natural ordering of its elements.
     * Elements inserted in this tree must implement the {@code Comparable} interface, otherwise
     * a {@code ClassCastException} will be thrown when calling this constructor.
     * @throws {@code ClassCastException} if E does not implement Comparable<? super E>
     */
    public BST() {
        this ((e1, e2) -> {
            @SuppressWarnings("unchecked")
            Comparable<? super E> e = (Comparable<? super E>) e1;
            return e.compareTo(e2);
        });
    }

    /** Constructs an empty binary search tree, ordered according to the given comparator.
     * @param cmp    The comparator that will be used to order the tree
     * @throws NullPointerException if the provided comparator was null
     */
    public BST(Comparator<? super E> cmp) {
        Objects.requireNonNull(cmp);
        this.root = null;
        this.cmp = cmp;
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
    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * Searches for the given element in the tree, returning an {@code Optional}
     * describing the result of the search
     *
     * @param element   the element to search for
     * @return an optional describing the result
     */
    public Optional<E> find(E element) {
        Objects.requireNonNull(element);
        E ret = null;

        Node<E> node = this.find(this.root, element);
        if (node != null)
            ret = node.getElement();

        return Optional.ofNullable(ret);
    }

    /**
     * Returns the Node containing a specific Element, or null otherwise.
     *
     * @param element   the element to find
     * @param cmp       the comparator to use to find the object
     * @return the Node that contains the Element, or null otherwise
     *
     * This method despite not being essential is very useful.
     * It is written here in order to be used by this class and its
     * subclasses avoiding recoding.
     * So its access level is protected
     */
    protected Node<E> find(Node<E> node, E element) {
        boolean found = false;

        if (element == null)
            return null;

        while(node != null && !found) {
            // -1 if element < node.getElement(), 0 if ==, 1 if >
            int result = Integer.signum(cmp.compare(element, node.getElement()));

            switch (result) {
                case -1 -> node  = node.getLeft();
                case +1 -> node  = node.getRight();
                default -> found = true;
            }
        }

        return node;
    }

    /*
     * Inserts an element in the tree.
     */
    @Override
    public void insert(E element) {
        root = insert(element,root());
    }

    private Node<E> insert(E element, Node<E> node) {
        if(node == null)
            return new Node<E>(element,null,null);

        int res = Integer.signum(cmp.compare(element, node.getElement()));

        switch (res) {
            case -1 -> node.setLeft(insert(element, node.getLeft()));
            case +1 -> node.setRight(insert(element, node.getRight()));
            default -> node.setElement(element);
        }

        return node;
    }

    /**
     * Removes an element from the tree maintaining its consistency as a Binary Search Tree.
     */
    @Override
    public void remove(E element) {
        this.root = remove(element, root());
    }

    private Node<E> remove(E element, Node<E> node) {
        if (node == null) {
            return null;
        }
        int res = Integer.signum(cmp.compare(element, node.getElement()));

        switch (res) {
            case -1 -> node.setLeft(remove(element, node.getLeft()));
            case +1 -> node.setRight(remove(element, node.getRight()));
            default -> {
                if (node.isLeaf())
                    return null;
                else if (!node.hasLeft())   //has only right child
                    return node.getRight();
                else if (!node.hasRight())  //has only left child
                    return node.getLeft();

                E min = smallestElement(node.getRight());
                node.setElement(min);
                node.setRight(remove(min, node.getRight()));
            }
        }

        return node;
    }

    /*
     * Returns the number of nodes in the tree.
     * @return number of nodes in the tree
     */
    @Override
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
    @Override
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
    @Override
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


    public E biggestElement() {
        return biggestElement(root);
    }

    protected E biggestElement(Node<E> node) {
        if(node == null)
            return null;
        if(node.getRight() == null)
            return node.getElement();
        else
            return biggestElement(node.getRight());
    }

    /**
     * Returns an iterable collection of elements of the tree, reported in in-order.
     * @return iterable collection of the tree's elements reported in in-order
     */
    @Override
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

    /**
     * Adds elements of the subtree rooted at Node node to the given
     * snapshot using an in-order traversal
     * @param node      Node serving as the root of a subtree
     * @param consumer  An action to perform on node
     */
    private void inOrderSubtree(Node<E> node, Consumer<? super E> consumer) {
        if (node == null)
            return;
        inOrderSubtree(node.getLeft(), consumer);
        consumer.accept(node.getElement());
        inOrderSubtree(node.getRight(), consumer);
    }

    /**
     * Returns an iterable collection of elements of the tree, reported in pre-order.
     * @return iterable collection of the tree's elements reported in pre-order
     */
    @Override
    public Iterable<E> preOrder() {
        List<E> snapshot = new ArrayList<>();
        preOrderForEach(snapshot::add);
        return snapshot;
    }

    /**
     * Performs an action over all elements of the tree, reported pre-order.
     * @param action the action to perform
     * @throws NullPointerException if the specified action is null
    */
    public void preOrderForEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        if (this.root != null)
            preOrderSubtree(this.root, action);
    }

    /**
     * Adds elements of the subtree rooted at Node node to the given
     * snapshot using an pre-order traversal
     * @param node      Node serving as the root of a subtree
     * @param consumer  An action to perform on node
     */
    private void preOrderSubtree(Node<E> node, Consumer<? super E> consumer) {
        if (node == null)
            return;
        consumer.accept(node.getElement());
        preOrderSubtree(node.getLeft(), consumer);
        preOrderSubtree(node.getRight(), consumer);
    }

    /**
     * Returns an iterable collection of elements of the tree, reported in post-order.
     * @return iterable collection of the tree's elements reported in post-order
     */
    @Override
    public Iterable<E> posOrder() {
        List<E> snapshot = new ArrayList<>();
        posOrderForEach(snapshot::add);
        return snapshot;
    }

    /**
     * Performs an action over all elements of the tree, reported pos-order.
     * @param action the action to perform
     * @throws NullPointerException if the specified action is null
    */
    public void posOrderForEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        if (this.root != null)
            posOrderSubtree(this.root, action);
    }

    /**
     * Adds positions of the subtree rooted at Node node to the given
     * snapshot using an post-order traversal
     * @param node      Node serving as the root of a subtree
     * @param consumer  An action to perform on node
     */
    private void posOrderSubtree(Node<E> node, Consumer<? super E> consumer) {
        if (node == null)
            return;
        posOrderSubtree(node.getLeft(), consumer);
        posOrderSubtree(node.getRight(), consumer);
        consumer.accept(node.getElement());
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


    /*****************************Iterable<E> impl*****************************/

    /**
     * Returns an iterator over elements of type {@code E}.
     * @implNote This method is highly inefficient due to an under-the-hood list
     * of type {@code E} having to be built every time this method is invoked.
     * Additionally, since this method is highly likely to be used in a while()
     * construct, an unnecessary amount of operations is performed.
     * It is highly recommended to use the BST::forEach() method with a lambda
     * expression instead.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return this.inOrder().iterator();
    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.
     * Actions are performed using an in-order iteration.
     * Exceptions thrown by the action are relayed to the caller.
     * @throws NullPointerException if the specified action is null
     */
    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        this.inOrderForEach(action);
    }
}
