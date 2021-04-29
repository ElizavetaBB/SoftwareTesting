package btreeNew;

public class Node {
    private Integer[] keys;
    private final Node[] children;
    private Node parent;
    private int countKeys;
    private boolean leaf;

    public Node(int t){
        this.keys=new Integer[2*t];
        this.children=new Node[2*t+1];
        this.parent=null;
        this.countKeys=0;
        this.leaf=true;
        for (int  i=0;i<2*t;i++){
            children[i]=null;
            if (i!=2*t-1) keys[i]=null;
        }
    }

    public Node getParent(){
        return parent;
    }

    public int getCountKeys(){
        return countKeys;
    }

    public boolean isLeaf(){
        return leaf;
    }

    public void setKey(int k) throws ArrayIndexOutOfBoundsException{
        if (this.countKeys>=keys.length) throw
                new ArrayIndexOutOfBoundsException("Количество ключей максимально");
        this.keys[countKeys]=k;
        countKeys++;
    }

    public Integer getKeyFromPosition(int i) throws ArrayIndexOutOfBoundsException{
        if (i>=keys.length) throw new ArrayIndexOutOfBoundsException("Ключей максимально 4");
        return keys[i];
    }

    public void setKeyOnPosition(Integer key,int i) throws ArrayIndexOutOfBoundsException{
        if (i>=keys.length) throw new ArrayIndexOutOfBoundsException("Позиция ключа максимально 3");
        this.keys[i]=key;
    }

    public Node getChildFromPosition(int i) throws ArrayIndexOutOfBoundsException{
        if (i>=children.length) throw new ArrayIndexOutOfBoundsException("Позиция ребенка максимально 4");
        return children[i];
    }

    public void setKeysUpToPosition(Integer[] keys,int countKeys,int startPosition)
            throws ArrayIndexOutOfBoundsException{
        if (keys==null) return;
        if (countKeys>=this.keys.length) throw
                new ArrayIndexOutOfBoundsException("Количество ключей максимально");
        if (countKeys+startPosition-1>=keys.length) throw
                new ArrayIndexOutOfBoundsException("Нет таких ключей");
        for (int i=0;i<countKeys;i++){
            this.keys[i]=keys[i+startPosition];
        }
        this.countKeys=countKeys;
    }

    public Integer[] getKeys(){
        return keys;
    }

    public void setChildrenUpToPosition(Node[] children,int countChildren,int startPosition)
            throws ArrayIndexOutOfBoundsException{
        if (children==null) return;
        if (countChildren>=this.children.length) throw
                new ArrayIndexOutOfBoundsException("Количество детей максимально");
        if (countChildren+startPosition-1>=children.length) throw
                new ArrayIndexOutOfBoundsException("Нет таких детей");
        for (int i=0;i<countChildren;i++){
            this.children[i]=children[i+startPosition];
            this.children[i].setParent(this);
        }
        leaf=false;
    }

    public void setParent(Node parent){
        this.parent=parent;
    }

    public Node[] getChildren(){
        return children;
    }

    public void setLeaf(boolean leaf){
        this.leaf=leaf;
    }

    public void setChildOnPosition(Node child,int i) throws ArrayIndexOutOfBoundsException{
        if (i>=children.length) throw
                new ArrayIndexOutOfBoundsException("Количество детей меньше");
        this.children[i]=child;
    }

    public void setCountKeys(int countKeys){
        this.countKeys=countKeys;
    }

    public void setKeys(Integer[] keys){
        this.keys=keys;
        countKeys=keys.length;
    }

}
