package jovami.trees;

import java.util.Comparator;

/**
 * @param <E>
 * @author DEI-ESINF
 */
public class AVL<E> extends BST<E> {

    private int balanceFactor(Node<E> node) {
        return height(node.getRight())-height(node.getLeft());
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
        int nodeBF = balanceFactor(node);
        if (node.getLeft() != null && nodeBF < -1) {
            int leftNodeBF = balanceFactor(node.getLeft());
            if (nodeBF * leftNodeBF < 0)
                return twoRotations(node);
            return rightRotation(node);
        }
        if (node.getRight() != null && nodeBF > 1) {
            int rightNodeBF = balanceFactor(node.getRight());
            if (nodeBF * rightNodeBF < 0)
                return twoRotations(node);
            return leftRotation(node);
        }
        return node;
    }

    public AVL() {
        super();
    }

    public AVL(Comparator<? super E> cmp) {
        super(cmp);
    }

    @Override
    public void insert(E element) {
        this.root = insert(element, this.root);
    }

    private Node<E> insert(E element, Node<E> node) {
        if (node == null)
            return new Node<>(element, null, null);

        int result = cmp.compare(element, node.getElement());

        if (result == 0) {
            node.setElement(element);
        } else {
            if (result < 0)
                node.setLeft(insert(element, node.getLeft()));
            else
                node.setRight(insert(element, node.getRight()));
            node = balanceNode(node);
        }

        return node;
    }

    @Override
    public void remove(E element) {
        this.root = remove(element, this.root);
    }

    private Node<E> remove(E element, Node<E> node) {
        if (node == null)
            return null;

        // -1 if element < node.getElement(), 0 if ==, 1 if >
        int result = Integer.signum(cmp.compare(element, node.getElement()));

        switch (result) {
            case -1 -> node.setLeft(remove(element, node.getLeft()));
            case +1 -> node.setRight(remove(element, node.getRight()));
            default -> {
                if (node.isLeaf())
                    return null;
                if (node.getLeft() == null)
                    return node.getRight();
                if (node.getRight() == null)
                    return node.getLeft();

                E smallElem = smallestElement(node.getRight());
                node.setElement(smallElem);
                node.setRight(remove(smallElem, node.getRight()));
            }
        }

        node = balanceNode(node);

        return node;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj)
            return true;

        if (otherObj == null || this.getClass() != otherObj.getClass())
            return false;

        @SuppressWarnings("unchecked")
        AVL<E> second = (AVL<E>) otherObj;
        return equals(root, second.root);
    }

    protected boolean equals(Node<E> root1, Node<E> root2) {
        if (root1 == null && root2 == null) {
            return true;
        } else if (root1 != null && root2 != null) {
            if (cmp.compare(root1.getElement(), root2.getElement()) == 0) {
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
