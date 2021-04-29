package btreeNew;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NodeTest {
    private Node node;

    @BeforeEach
    public void setNode(){
        node=new Node(2);
        node.setKey(1);
        node.setKey(2);
        node.setKey(3);
        node.setKey(4);
    }

    @Test
    public void testSet5Keys() {
        Throwable thrown = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {
                    node.setKey(5);
                });
        Assertions.assertEquals("Количество ключей максимально", thrown.getMessage());
    }

    @Test
    public void testSetKeys() {
        node.setKeyOnPosition(5, 3);
        Assertions.assertEquals(5, node.getKeyFromPosition(3));
        node.setKeysUpToPosition(new Integer[]{1, 2, 3, 6, 7}, 3, 1);
        Assertions.assertEquals(6, node.getKeyFromPosition(2));
        node.setKeys(new Integer[]{5, 2, 8});
        Assertions.assertEquals(3, node.getCountKeys());
        Assertions.assertEquals(8, node.getKeyFromPosition(2));
    }

    @Test
    public void testSetKeysWithExceptions() {
        Throwable thrown = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {
                    node.setKeysUpToPosition(new Integer[]{1}, 5, 0);
                }
        );
        Assertions.assertEquals("Количество ключей максимально", thrown.getMessage());
        thrown = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            node.setKeysUpToPosition(new Integer[]{1}, 1, 1);
        });
        Assertions.assertEquals("Нет таких ключей", thrown.getMessage());
    }

    @Test
    public void testSetCountKeys() {
        node.setCountKeys(2);
        Assertions.assertEquals(2, node.getCountKeys());
    }

    @Test
    public void testGetKeyWithExceptions(){
        Throwable thrown = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {node.getKeyFromPosition(5);});
        Assertions.assertEquals("Ключей максимально 4",thrown.getMessage());
        Assertions.assertEquals(1,node.getKeyFromPosition(0));
        Assertions.assertNotNull(node.getKeys());
        Assertions.assertEquals(4,node.getCountKeys());
    }

    @Test
    public void testGetMethods(){
        Assertions.assertNull(node.getParent());
        Assertions.assertTrue(node.isLeaf());
        Assertions.assertNotNull(node.getChildren());
    }

    @Test
    public void testSetMethods(){
        node.setLeaf(false);
        Assertions.assertFalse(node.isLeaf());
        Node node=new Node(2);
        this.node.setParent(node);
        Assertions.assertEquals(node,this.node.getParent());
    }

    @Test
    public void testSetChildrenWithExceptions(){
        Throwable thrown = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {node.getChildFromPosition(5);});
        Assertions.assertEquals("Позиция ребенка максимально 4",thrown.getMessage());
        Assertions.assertNull(node.getChildFromPosition(4));
        Node[] nnode=new Node[]{new Node(2),new Node(2)};
        thrown = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {node.setChildrenUpToPosition(nnode,5,0);});
        Assertions.assertEquals("Количество детей максимально",thrown.getMessage());
        thrown = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {node.setChildrenUpToPosition(nnode,1,2);});
        Assertions.assertEquals("Нет таких детей",thrown.getMessage());
        thrown = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {node.setChildOnPosition(nnode[0],6);});
        Assertions.assertEquals("Количество детей меньше",thrown.getMessage());
    }

    @Test
    public void testSetChildren(){
        Node[] nnode=new Node[]{new Node(2),new Node(2)};
        node.setChildrenUpToPosition(nnode,1,0);
        Assertions.assertEquals(nnode[0],node.getChildFromPosition(0));
        node.setChildOnPosition(nnode[1],1);
        Assertions.assertEquals(nnode[1],node.getChildFromPosition(1));
    }
}
