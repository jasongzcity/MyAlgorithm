package TreeLinkedNode;

import java.util.ArrayDeque;
import java.util.Queue;

public class TreeLinkNode {
    public int val;
    public TreeLinkNode left, right, next;
    public TreeLinkNode(int x) { val = x; }

    // Initialize with an array
    public TreeLinkNode(Integer[] a) {
        TreeLinkNode temp = this;
        int i = 0;
        temp.val = a[i++];
        Queue<TreeLinkNode> q = new ArrayDeque<>();
        q.add(temp);
        while(true){
            TreeLinkNode root = q.poll();
            if(i<a.length){
                if(a[i]!=null) {
                    root.left = new TreeLinkNode(a[i]);
                    q.add(root.left);
                }
                i++;
            }else break;
            if(i<a.length){
                if(a[i]!=null){
                    root.right = new TreeLinkNode(a[i]);
                    q.add(root.right);
                }
                i++;
            }else break;
        }
    }
}
