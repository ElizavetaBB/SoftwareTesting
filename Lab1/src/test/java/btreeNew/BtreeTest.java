package btreeNew;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BtreeTest {

    private Btree btree;
    private static int[] testCombination;

    @BeforeAll
    public static void setUpTestCombination() {
        testCombination=new int[]{1,10,8,9,5,7,6,4,2,6};
    }

    private void insertCombination(int position){
        for (int i=0;i<position;i++){
            btree.insert(testCombination[i]);
        }
    }

    private boolean compareBtree(Btree a, Btree b){
        if (a.getRoot().getCountKeys()!=b.getRoot().getCountKeys()) return false;
        return compareNodes(a.getRoot(),b.getRoot());
    }

    private boolean compareNodes(Node a, Node b){
        for (int i=0;i<a.getCountKeys();i++){
            if (a.getKeyFromPosition(i)!=b.getKeyFromPosition(i)) return false;
        }
        if (a.getChildren().length==b.getChildren().length){
            for (int i=0;i<a.getChildren().length;i++){
                if (a.getChildFromPosition(i)==null && b.getChildFromPosition(i)==null){
                    return true;
                } else if (b.getChildFromPosition(i)==null && a.getChildFromPosition(i)!=null || b.getChildFromPosition(i)!=null && a.getChildFromPosition(i)==null){
                    return false;
                }
                compareNodes(a.getChildFromPosition(i),b.getChildFromPosition(i));
            }
            return true;
        }else return false;
    }

    private Node createNode(Node parent,Integer[] keys,int countChildren){
        Node node=new Node(2);
        node.setKeys(keys);
        node.setCountKeys(keys.length);
        node.setLeaf(countChildren==0);
        node.setParent(parent);
        return node;
    }

    @BeforeEach
    public void init(){
        btree=new Btree(2);
    }

    @Test
    public void testTreeEmpty(){
        Assertions.assertFalse(btree.show());
        insertCombination(3);
        Assertions.assertTrue(btree.show());
        btree.clear();
        Assertions.assertFalse(btree.show());
    }

    @Test
    public void testSearchInRoot(){
        btree.insert(1);
        Assertions.assertTrue(btree.search(1));
        btree.remove(1);
        Assertions.assertFalse(btree.search(1));
    }

    @Test
    public void testSearchInChildren(){
        Assertions.assertFalse(btree.search(10));
        insertCombination(3);
        Assertions.assertTrue(btree.search(10));
    }

    @Test
    public void testInsertInRootAfterDeleting(){
        btree.insert(1);
        btree.remove(1);
        btree.insert(1);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{1});
        btreeTest.getRoot().setLeaf(true);
        btreeTest.getRoot().setParent(null);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testInsert3KeysInRoot() {
        insertCombination(3);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{8});
        btreeTest.getRoot().setLeaf(false);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(1);
        Node[] nodes=btreeTest.getRoot().getChildren();
        nodes[0]=createNode(btreeTest.getRoot(),new Integer[]{1},0);
        nodes[1]=createNode(btreeTest.getRoot(),new Integer[]{10},0);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testInsertInLeaf() {
        insertCombination(4);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{8});
        btreeTest.getRoot().setLeaf(false);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(1);
        Node[] nodes=btreeTest.getRoot().getChildren();
        nodes[0]=createNode(btreeTest.getRoot(),new Integer[]{1},0);
        nodes[1]=createNode(btreeTest.getRoot(),new Integer[]{9,10},0);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testInsertInLeafWithPassingToParent() {
        insertCombination(4);
        btree.insert(11);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{8,10});
        btreeTest.getRoot().setLeaf(false);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(2);
        Node[] nodes=btreeTest.getRoot().getChildren();
        nodes[0]=createNode(btreeTest.getRoot(),new Integer[]{1},0);
        nodes[1]=createNode(btreeTest.getRoot(),new Integer[]{9},0);
        nodes[2]=createNode(btreeTest.getRoot(),new Integer[]{11},0);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testInsertInLeafWithRebuildingParent() {
        insertCombination(4);
        btree.insert(11);
        btree.insert(5);
        btree.insert(7);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{8});
        btreeTest.getRoot().setLeaf(false);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(1);
        Node[] nodes1=btreeTest.getRoot().getChildren();
        nodes1[0]=createNode(btreeTest.getRoot(),new Integer[]{5},2);
        nodes1[1]=createNode(btreeTest.getRoot(),new Integer[]{10},2);
        Node[] nodes10=nodes1[0].getChildren();
        nodes10[0]=createNode(nodes1[0],new Integer[]{1},0);
        nodes10[1]=createNode(nodes1[0],new Integer[]{7},0);
        Node[] nodes11=nodes1[1].getChildren();
        nodes11[0]=createNode(nodes1[1],new Integer[]{9},0);
        nodes11[1]=createNode(nodes1[1],new Integer[]{11},0);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testRemoveFromRootLeaf() {
        btree.insert(1);
        btree.remove(1);
        Assertions.assertFalse(btree.show());
    }

    @Test
    public void testRemoveFromLeaf() {
        insertCombination(4);
        btree.remove(9);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{8});
        btreeTest.getRoot().setLeaf(false);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(1);
        Node[] nodes1=btreeTest.getRoot().getChildren();
        nodes1[0]=createNode(btreeTest.getRoot(),new Integer[]{1},0);
        nodes1[1]=createNode(btreeTest.getRoot(),new Integer[]{10},0);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testRemoveWithMerge() {
        insertCombination(6);
        btree.remove(7);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{5,9});
        btreeTest.getRoot().setLeaf(false);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(2);
        Node[] nodes1=btreeTest.getRoot().getChildren();
        nodes1[0]=createNode(btreeTest.getRoot(),new Integer[]{1},0);
        nodes1[1]=createNode(btreeTest.getRoot(),new Integer[]{8},0);
        nodes1[2]=createNode(btreeTest.getRoot(),new Integer[]{10},0);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testRemoveWithMergeParentChild() {
        insertCombination(6);
        btree.remove(9);
        btree.remove(7);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{5});
        btreeTest.getRoot().setLeaf(false);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(1);
        Node[] nodes1=btreeTest.getRoot().getChildren();
        nodes1[0]=createNode(btreeTest.getRoot(),new Integer[]{1},0);
        nodes1[1]=createNode(btreeTest.getRoot(),new Integer[]{8,10},0);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testRemoveParentWithoutKeys() {
        insertCombination(10);
        btree.remove(9);
        btree.remove(7);
        btree.remove(6);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{5});
        btreeTest.getRoot().setLeaf(false);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(1);
        Node[] nodes1=btreeTest.getRoot().getChildren();
        nodes1[0]=createNode(btreeTest.getRoot(),new Integer[]{2},2);
        nodes1[1]=createNode(btreeTest.getRoot(),new Integer[]{8},2);
        Node[] nodes10=nodes1[0].getChildren();
        nodes10[0]=createNode(nodes1[0],new Integer[]{1},0);
        nodes10[1]=createNode(nodes1[0],new Integer[]{4},0);
        Node[] nodes11=nodes1[1].getChildren();
        nodes11[0]=createNode(nodes1[1],new Integer[]{6},0);
        nodes11[1]=createNode(nodes1[1],new Integer[]{10},0);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testRemoveRootWithoutKeys() {
        insertCombination(3);
        btree.remove(8);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{1,10});
        btreeTest.getRoot().setLeaf(true);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(2);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testRemoveWith2KeysInRoot(){
        insertCombination(2);
        btree.remove(10);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{1});
        btreeTest.getRoot().setLeaf(true);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(1);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testFalseRemove(){
        insertCombination(2);
        btree.remove(2);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{1,10});
        btreeTest.getRoot().setLeaf(true);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(2);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testRemove2(){
        insertCombination(9);
        btree.remove(2);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{5,8});
        btreeTest.getRoot().setLeaf(false);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(2);
        Node[] nodes=btreeTest.getRoot().getChildren();
        nodes[0]=createNode(btreeTest.getRoot(),new Integer[]{1,4},0);
        nodes[1]=createNode(btreeTest.getRoot(),new Integer[]{6,7},0);
        nodes[2]=createNode(btreeTest.getRoot(),new Integer[]{9,10},0);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testRemoveWithLeftMerge(){
        insertCombination(5);
        btree.remove(9);
        btree.remove(10);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{5});
        btreeTest.getRoot().setLeaf(false);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(1);
        Node[] nodes=btreeTest.getRoot().getChildren();
        nodes[0]=createNode(btreeTest.getRoot(),new Integer[]{1},0);
        nodes[1]=createNode(btreeTest.getRoot(),new Integer[]{8},0);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testRemoveRight(){
        insertCombination(4);
        btree.remove(9);
        btree.remove(10);
        System.out.println(btree.getHistory());
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{1,8});
        btreeTest.getRoot().setLeaf(true);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(2);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }

    @Test
    public void testRemoveLeft(){
        insertCombination(4);
        btree.remove(1);
        Btree btreeTest=new Btree(2);
        btreeTest.getRoot().setKeys(new Integer[]{9});
        btreeTest.getRoot().setLeaf(true);
        btreeTest.getRoot().setParent(null);
        btreeTest.getRoot().setCountKeys(1);
        Node[] nodes=btreeTest.getRoot().getChildren();
        nodes[0]=createNode(btreeTest.getRoot(),new Integer[]{8},0);
        nodes[1]=createNode(btreeTest.getRoot(),new Integer[]{10},0);
        Assertions.assertTrue(compareBtree(btree,btreeTest));
    }
}
