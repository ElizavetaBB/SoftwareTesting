package btreeNew;

public class Node {
    private Integer[] keys;
    private Node[] children;
    private Node parent;
    private int countKeys;
    private boolean leaf;

    public Node(int t){
        this.keys=new Integer[2*t];
        this.children=new Node[2*t+1];
        this.parent=null;
        this.countKeys=0;
        //this.countChildren=0;
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

    public void setKey(int k){
        this.keys[countKeys]=k;
        countKeys++;
    }

    public Integer getKeyFromPosition(int i){
        if (i>=keys.length) throw new NullPointerException();
        return keys[i];
    }

    public void setKeyOnPosition(Integer key,int i){
        if (i>=keys.length) throw new NullPointerException();
        this.keys[i]=key;
    }

    public Node getChildFromPosition(int i){
        if (i>=children.length) throw new NullPointerException();
        return children[i];
    }

    public void setKeysUpToPosition(Integer[] keys,int countKeys,int startPosition){
        for (int i=0;i<countKeys;i++){
            this.keys[i]=keys[i+startPosition];
        }
        this.countKeys=countKeys;
    }

    public Integer[] getKeys(){
        return keys;
    }

    public void setChildrenUpToPosition(Node[] children,int countChildren,int startPosition){
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

    public void setChildOnPosition(Node child,int i){
        this.children[i]=child;
    }

    public void setCountKeys(int countKeys){
        this.countKeys=countKeys;
    }

    public void setKeys(Integer keys[]){
        this.keys=keys;
        countKeys=keys.length;
    }

}
