package jovami.trees;

/**
 * @param <E>
 * @author DEI-ESINF
 */
public class AVL<E extends Comparable<E>> extends BST<E> {


    private int balanceFactor(Node<E> node) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Node<E> rightRotation(Node<E> node) {
        Node<E> leftson = node.getLeft();
        node.setLeft(leftson.getRight());
        leftson.setRight(node);
        node = leftson;
        return node;
    }

    private Node<E> leftRotation(Node<E> node) {
        Node<E> rightson = node.getRight();
        node.setRight(rightson.getLeft());
        rightson.setLeft(node);
        node = rightson;
        return node;
    }

    private Node<E> twoRotations(Node<E> node) {
        if (balanceFactor(node) < 0) {
            node.setLeft(leftRotation(node.getLeft()));
            node = rightRotation(node);
        } else {
            node.setRight(rightRotation(node.getRight()));
            node = leftRotation(node);
        }
        return node;
    }

    private Node<E> balanceNode(Node<E> node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(E element) {
        this.root = insert(element, this.root);
    }

    private Node<E> insert(E element, Node<E> node) {
        if (node == null)
            return new Node<E>(element, null, null);

        if (node.getElement() == element) {
            node.setElement(element);
        } else {
            if (element.compareTo(node.getElement()) < 0) {
                node.setLeft(insert(element, node.getLeft()));
            } else if (element.compareTo(node.getElement()) > 0) {
                node.setRight(insert(element, node.getRight()));
            }
            node = balanceNode(node);
        }
        return node;
    }

    @Override
    public void remove(E element) {
        root = remove(element, root());
    }

    private Node<E> remove(E element, BST.Node<E> node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public boolean equals(Object otherObj) {
        if (this == otherObj)
            return true;

        if (otherObj == null || this.getClass() != otherObj.getClass())
            return false;

        @SuppressWarnings("unchecked")
        AVL<E> second = (AVL<E>) otherObj;
        return equals(root, second.root);
    }

    public boolean equals(Node<E> root1, Node<E> root2) {
        if (root1 == null && root2 == null) {
            return true;
        } else if (root1 != null && root2 != null) {
            if (root1.getElement().compareTo(root2.getElement()) == 0) {
                return equals(root1.getLeft(), root2.getLeft())
                        && equals(root1.getRight(), root2.getRight());
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
