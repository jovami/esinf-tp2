package jovami.trees;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author DEI-ESINF
 */
public class TreeTest {
    Integer[] arr = {20,15,10,13,8,17,40,50,30,7};
    int[] nnodes= {1,2,4,2,1};
    Integer[] inorderT= {7,8,10,13,15,17,20,30,40,50};
    Integer[] posorderT= {7,8,13,10,17,15,30,50,40,20};
    Integer[] bstAscDes = {7, 8, 10, 13, 15, 17, 20, 50, 40, 30};
    Tree<Integer> instance;

    public TreeTest() {
    }

    @BeforeEach
    public void setUp(){
        instance = new Tree<>();
        for(int i :arr)
            instance.insert(i);
    }
    /**
     * Test of isLeaf method, of class Tree.
     */
    @Test
    public void testcontains() {
        System.out.println("contains");
        assertTrue(instance.contains(7));
        assertTrue(instance.contains(13));
        assertFalse(instance.contains(80));
        assertFalse(instance.contains(45));
    }

    /**
     * Test of isLeaf method, of class Tree.
     */
    @Test
    public void testIsLeaf() {
        System.out.println("isLeaf");
        assertTrue(instance.isLeaf(7));
        assertTrue(instance.isLeaf(13));
        assertTrue(instance.isLeaf(17));
        assertTrue(instance.isLeaf(30));
        assertTrue(instance.isLeaf(50));
        assertFalse(instance.isLeaf(40));
        assertFalse(instance.isLeaf(20));
        assertFalse(instance.isLeaf(8));
        assertFalse(instance.isLeaf(999));
    }

    /**
     * Test of autumnTree method, of class Tree.
     */
    @Test
    public void testAutumnTree() {
        System.out.println("autumntree");
        Tree<Integer> newTree = (Tree<Integer>) instance.autumnTree();

        Integer[] a = {8,10,15,20,40};
        assertEquals(Arrays.asList(a), newTree.inOrder());
        System.out.println(newTree);
    }

    /**
     * Test of ascdes method, of class Tree.
     */
    @Test
    public void testAscdes() {
        System.out.println("ascdes");
        assertEquals(Arrays.asList(bstAscDes),instance.ascdes());
    }

    /**
     * Test of ascdes method, of class Tree.
     */
    @Test
    public void testnumNodesByLevel() {
        System.out.println("numNodesByLevel");
        assertArrayEquals( nnodes, instance.numNodesByLevel());
    }

    /**
     * Test of perfect balanced method, of class Tree.
     */
    @Test
    public void perfectBalanced() {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Test of minimum complete subtree method, of class Tree.
     */
    @Test
    public void testminCompletSubtree() {
        System.out.println("minimum complete subtree ");
        Tree<Integer> newTree = (Tree<Integer>) instance.minCompletSubtree(7, 17);

        Integer[] a = {15,10,8,13,17};
        assertEquals(Arrays.asList(a), newTree.preOrder());
        System.out.println(newTree);
    }

    /**
     * Test construct BST from postorder traversal, of class Tree.
     */
    @Test
    public void testconstrucTreeposOrder() {
        System.out.println("construct Tree posOrder ");
        ArrayList<Integer> posOrder = new ArrayList<>(Arrays.asList(posorderT));
        Tree<Integer> newTree = (Tree<Integer>) instance.construcTreeposOrder(posOrder);

        assertEquals(Arrays.asList(inorderT), newTree.preOrder());
        System.out.println(newTree);
    }

}
