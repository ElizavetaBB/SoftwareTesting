package btreeNew;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Btree {

    private final int t;
    private Node root;
    private List<String> history;

    public Btree(int t){
        this.t=t;
        this.root=new Node(t);
        history=new ArrayList<>();
    }

    private void insert_to_node(int key, Node node){
        node.setKey(key);
        history.add("Add a key "+key+" ");
        sort(node);
    }

    private void sort(Node node){
        int m;
        for (int i=0; i<2*t-1; i++){
            for (int j=i+1; j<2*t; j++){
                if (node.getKeyFromPosition(i)!=null && node.getKeyFromPosition(j)!=null){
                    if (node.getKeyFromPosition(i) > node.getKeyFromPosition(j)) {
                        m=node.getKeyFromPosition(i);
                        node.setKeyOnPosition(node.getKeyFromPosition(j),i);
                        node.setKeyOnPosition(m,j);
                    }
                } else break;
            }
        }
    }

    public void insert(int key){
        if (root==null){
            root=new Node(2);
            root.setKey(key);
            root.setLeaf(true);
            root.setParent(null);
            history.add("Add a key ");
        }else{
            Node ptr = root;
            while (!ptr.isLeaf()) {
                for (int i = 0; i < 2 * t - 2; i++) {
                    if (ptr.getKeyFromPosition(i) != null) {
                        if (key <= ptr.getKeyFromPosition(i)) {
                            ptr = ptr.getChildFromPosition(i);
                            break;
                        }
                        if (ptr.getKeyFromPosition(i + 1) == null) {
                            ptr = ptr.getChildFromPosition(i + 1);
                            break;
                        }
                    } else break;
                }
            }
            insert_to_node(key, ptr);
            sort(ptr);
            while (ptr.getCountKeys() > 2 * t - 2) {
                if (ptr == root) {
                    restruct(ptr);
                    break;
                } else {
                    restruct(ptr);
                    ptr = ptr.getParent();
                }
            }
        }
    }

    private void restruct(Node node){
        if (node.getCountKeys()<2*t-1) return;
        //первый сын
        Node child1 = new Node(t);
        child1.setKeysUpToPosition(node.getKeys(),t-1,0);
        if(node.getChildren()[0]!=null){
            child1.setChildrenUpToPosition(node.getChildren(),t,0);
        } else {
            child1.setLeaf(true);
        }
        //второй сын
        Node child2 = new Node(t);
        child2.setKeysUpToPosition(node.getKeys(),t-1,t);
        if(node.getChildren()[0]!=null){
            child2.setChildrenUpToPosition(node.getChildren(),t,t);
        } else {
            child2.setLeaf(true);
        }
        if (node.getParent()==null){
            node.setKeyOnPosition(node.getKeyFromPosition(t-1),0);
            for(int j=1; j<2*t; j++) node.setKeyOnPosition(null,j);
            node.setChildOnPosition(child1,0);
            node.setChildOnPosition(child2,1);
            for(int i=2; i<2*t; i++) node.setChildOnPosition(null,i);
            node.setParent(null);
            node.setLeaf(false);
            node.setCountKeys(1);
            child1.setParent(node);
            child2.setParent(node);
        } else {
            insert_to_node(node.getKeyFromPosition(t-1),node.getParent());
            //sort(node.getParent());//sort(node.parent);
            for (int i=0; i<2*t; i++){
                if (node.getParent().getChildFromPosition(i)==node) node.getParent().setChildOnPosition(null,i);
            }
            for (int i=0;i<2*t; i++){
                if (node.getParent().getChildFromPosition(i)==null) {
                    for (int j=2*t-1; j>i+1; j--) node.getParent().setChildOnPosition(node.getParent().getChildFromPosition(j-1),j);
                    node.getParent().setChildOnPosition(child2,i+1);
                    node.getParent().setChildOnPosition(child1,i);
                    break;
                }
            }
            child1.setParent(node.getParent());
            child2.setParent(node.getParent());
            node.getParent().setLeaf(false);
        }
        history.add("Split ");
    }

    public boolean search(int key){
        return searchKey(key, root)!=null;
    }

    private AbstractMap.SimpleEntry<Integer, Node> searchKey(int key, Node node){
        if (node!=null){
            if (!node.isLeaf()){
                int i;
                for (i=0; i<2*t; i++){
                    if (node.getKeyFromPosition(i)!=null) {
                        if(key==node.getKeyFromPosition(i)) return new AbstractMap.SimpleEntry<>(i,node);
                        if (key<node.getKeyFromPosition(i)){
                            return searchKey(key, node.getChildFromPosition(i));
                        }
                    } else break;
                }
                return searchKey(key, node.getChildFromPosition(i));
            } else {
                for(int j=0; j<2*t; j++)
                    if (node.getKeyFromPosition(j)!=null && key==node.getKeyFromPosition(j)) return new AbstractMap.SimpleEntry<>(j,node);
                return null;
            }
        } else return null;
    }

    private void removeFromNode(int key, Node node){
        for (int i=0; i<node.getCountKeys(); i++){
            if (node.getKeyFromPosition(i)==key){
                for (int j=i; j<node.getCountKeys(); j++) {
                    node.setKeyOnPosition(node.getKeyFromPosition(j+1),j);
                    node.setChildOnPosition(node.getChildFromPosition(j+1),j);
                }
                node.setKeyOnPosition(null,node.getCountKeys()-1);
                node.setChildOnPosition(node.getChildFromPosition(node.getCountKeys()),node.getCountKeys()-1);
                node.setChildOnPosition(null, node.getCountKeys());
                break;
            }
        }
        node.setCountKeys(node.getCountKeys()-1);
        history.add("Remove a key "+key+" ");
    }

    private void lconnect(Node node, Node othernode){
        if (node==null) return;
        for (int i=0; i<othernode.getCountKeys(); i++){
            node.setKeyOnPosition(othernode.getKeyFromPosition(i),node.getCountKeys());
            node.setChildOnPosition(othernode.getChildFromPosition(i),node.getCountKeys());
            node.setCountKeys(node.getCountKeys()+1);
        }
        node.setChildOnPosition(othernode.getChildFromPosition(othernode.getCountKeys()),node.getCountKeys());
        for (int j=0; j<node.getCountKeys()+1; j++){
            if (node.getChildFromPosition(j)==null) break;
            node.getChildFromPosition(j).setParent(node);
        }
        history.add("Merge ");
    }
    
    private void repair(Node node){
        if (node==root && node.getCountKeys()==0){
            if (root.getChildFromPosition(0)!=null){
                root.getChildFromPosition(0).setParent(null);
                root=root.getChildFromPosition(0);
            }
            return;
        }
        Node ptr=node;
        int positionSon=-1;
        Node parent=ptr.getParent();
        for (int j=0; j<parent.getCountKeys()+1; j++){
            if (parent.getChildFromPosition(j)==ptr){
                positionSon=j;
                break;
            }
        }
        if (positionSon==0 || positionSon!=parent.getCountKeys()){
            insert_to_node(parent.getKeyFromPosition(positionSon), ptr);
            lconnect(ptr, parent.getChildFromPosition(positionSon+1));
            history.add("right ");
            parent.setChildOnPosition(ptr,positionSon+1);
            parent.setChildOnPosition(null,positionSon);
            removeFromNode(parent.getKeyFromPosition(positionSon), parent);
        } else {
            if (positionSon==parent.getCountKeys()){
                insert_to_node(parent.getKeyFromPosition(positionSon-1), parent.getChildFromPosition(positionSon-1));
                lconnect(parent.getChildFromPosition(positionSon-1), ptr);
                history.add("left ");
                parent.setChildOnPosition(parent.getChildFromPosition(positionSon-1),positionSon);
                parent.setChildOnPosition(null,positionSon-1);
                removeFromNode(parent.getKeyFromPosition(positionSon-1), parent);
            }
        }
        if (parent.getCountKeys()<t-1) repair(parent);
    }

    private void removeLeaf(int key, Node node){
        if (node==root && node.getCountKeys()==1){
            removeFromNode(key, node);
            root.setChildOnPosition(null,0);
            root=null;
            return;
        }
        Node ptr=node;
        int k1,k2;
        int positionSon=-1;
        Node parent=ptr.getParent();
        for (int j=0; j<parent.getCountKeys()+1; j++){
            if (parent.getChildFromPosition(j)==ptr){
                positionSon=j;
                break;
            }
        }
        if (positionSon==0){
            if (parent.getChildFromPosition(positionSon+1).getCountKeys()>t-1){
                k1=parent.getChildFromPosition(positionSon+1).getKeyFromPosition(0);
                k2=parent.getKeyFromPosition(positionSon);
                insert_to_node(k2, ptr);
                removeFromNode(key, ptr);
                parent.setKeyOnPosition(k1,positionSon);
                removeFromNode(k1, parent.getChildFromPosition(positionSon+1));
            } else {
                removeFromNode(key, ptr);
                if (ptr.getCountKeys()<t-1) repair(ptr);
            }
        } else {
            if (positionSon==parent.getCountKeys() || parent.getChildFromPosition(positionSon+1).getCountKeys()<t){
                if (parent.getChildFromPosition(positionSon-1).getCountKeys()>t-1){
                    Node temp=parent.getChildFromPosition(positionSon-1);
                    k1=temp.getKeyFromPosition(temp.getCountKeys()-1);
                    k2=parent.getKeyFromPosition(positionSon-1);
                    insert_to_node(k2, ptr);
                    removeFromNode(key, ptr);
                    parent.setKeyOnPosition(k1,positionSon-1);
                    removeFromNode(k1, temp);
                } else {
                    removeFromNode(key, ptr);
                    if (ptr.getCountKeys()<t-1) repair(ptr);
                }
            } else {
                k1=parent.getChildFromPosition(positionSon+1).getKeyFromPosition(0);
                k2=parent.getKeyFromPosition(positionSon);
                insert_to_node(k2, ptr);
                removeFromNode(key, ptr);
                parent.setKeyOnPosition(k1,positionSon);
                removeFromNode(k1, parent.getChildFromPosition(positionSon+1));
            }
        }
    }

    private void remove(int key, Node node){
        Node ptr=node;
        int position=-1;
        for (int i=0; i<node.getCountKeys(); i++){
            if (key==node.getKeyFromPosition(i)) {
                position=i;
                break;
            }
        }
        ptr=ptr.getChildFromPosition(position+1);
        int newkey;
        while (!ptr.isLeaf()) ptr=ptr.getChildFromPosition(0);
        if (ptr.getCountKeys()>t-1) {
            newkey=ptr.getKeyFromPosition(0);
            removeFromNode(newkey, ptr);
            node.setKeyOnPosition(newkey,position);
        } else {
            ptr=node;
            ptr=ptr.getChildFromPosition(position);
            while (!ptr.isLeaf()) ptr=ptr.getChildFromPosition(ptr.getCountKeys());
            newkey=ptr.getKeyFromPosition(ptr.getCountKeys()-1);
            node.setKeyOnPosition(newkey,position);
            if (ptr.getCountKeys()>t-1) removeFromNode(newkey, ptr);
            else {
                removeLeaf(newkey, ptr);
            }
        }
    }
    
    public void remove(int key){
        Node ptr=this.root;
        int i;
        if (!search(key)) {
            return;
        } else {
            for (i=0; i<ptr.getCountKeys(); i++){
                if (ptr.getKeyFromPosition(0)!=null) {
                    if(key==ptr.getKeyFromPosition(i)) {
                        break;
                    } else {
                        if (key<ptr.getKeyFromPosition(i)){
                            ptr=ptr.getChildFromPosition(i);
                            i=-1;
                        } else {
                            if (i==ptr.getCountKeys()-1) {
                                ptr=ptr.getChildFromPosition(i+1);
                                i=-1;
                            }
                        }
                    }
                } else break;
            }
        }
        if (ptr.isLeaf()) {
            if (ptr.getCountKeys()>t-1) removeFromNode(key,ptr);
            else removeLeaf(key, ptr);
        } else remove(key, ptr);
    }

    public void clear(){
        root=new Node(t);
    }

    public boolean show(){
        if (root==null) return false;
        if (root.getCountKeys()==0) return false;
        else{
            int j=0;
            show(root,j);
            return true;
        }
    }

    private void show(Node node, int j){
        System.out.print("Узел уровня №"+j+". ");
        for (int i=0;i<node.getCountKeys();i++){
            System.out.print(node.getKeyFromPosition(i)+" ");
        }
        System.out.println();
        j++;
        if (!node.isLeaf()){
            for (int i=0;i<node.getCountKeys()+1;i++){
                show(node.getChildFromPosition(i),j);
            }
        }
    }

    public Node getRoot(){
        return root;
    }

    public List<String> getHistory(){
        return history;
    }
}
