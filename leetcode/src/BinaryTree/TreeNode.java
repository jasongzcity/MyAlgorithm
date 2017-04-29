package BinaryTree;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * TreeNode for binary tree.
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int x) { val = x; }

    // construct the tree by level traverse
    // This is a fast route, to construct the tree more
    // precisely, use TreeNode(Integer[] a)
    public TreeNode(String s){
        TreeNode temp = this;
        temp.val = s.charAt(0) - '0';
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(temp);
        int i = 1;
        while(true){
            TreeNode n = q.poll(),left,right;
            if(i<s.length()) {
                left = new TreeNode(s.charAt(i++)-'0');
                n.left = left;
                q.add(left);
            }else
                break;
            if(i<s.length()) {
                right = new TreeNode(s.charAt(i++) - '0');
                n.right = right;
                q.add(right);
            }else
                break;
        }
    }

    // construct the tree by level traverse
    public TreeNode(Integer[] a){
        TreeNode temp = this;
        int i = 0;
        temp.val = a[i++];
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(temp);
        while(true){
            TreeNode root = q.poll();
            if(i<a.length){
                if(a[i]!=null) {
                    root.left = new TreeNode(a[i]);
                    q.add(root.left);
                }
                i++;
            }else break;
            if(i<a.length){
                if(a[i]!=null){
                    root.right = new TreeNode(a[i]);
                    q.add(root.right);
                }
                i++;
            }else break;
        }
    }

    // display tree by level traverse
    public void levelTraverse(){
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(this);
        int levelCount = 0;
        while(!q.isEmpty()){
            levelCount = q.size();
            while(levelCount-->0){
                TreeNode n = q.poll();
                System.out.print(n.val+" ");
                if(n.left!=null) q.add(n.left);
                if(n.right!=null) q.add(n.right);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void morrisTraverse(){
        TreeNode n = this;
        while(n!=null){
            if(n.left==null){
                System.out.print(n.val+" ");
                n = n.right;
            }else{
                TreeNode t = n.left;
                while(t.right!=null&&t.right!=n) t = t.right;
                if(t.right==null){  // first time here
                    t.right = n;
                    n = n.left;
                }else{              // backtracked!
                    System.out.print(n.val+" ");
                    t.right = null;
                    n = n.right;
                }
            }
        }
    }

    public static void main(String[] args) {
//        TreeNode root = new TreeNode("123456712345678");
//        root.levelTraverse();
//        root = new TreeNode("4261357");
//        root.morrisTraverse();
        Integer[] a = new Integer[]{
                1,2,3,null,null,null,4
        };
        TreeNode root = new TreeNode(a);
        root.levelTraverse();

        Integer[] a1 = new Integer[]{
                1,3,null,5,7
        };
        Integer[] a2 = new Integer[]{
                1,null,3,5,7
        };
        TreeNode t1 = new TreeNode(a1);
        TreeNode t2 = new TreeNode(a2);
    }
}
