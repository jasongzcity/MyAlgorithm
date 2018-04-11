package BSTIterator_173;

import BinaryTree.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Implement an iterator over a binary search tree (BST).
 * Your iterator will be initialized with the root node of a BST.
 *
 * Calling next() will return the next smallest number in the BST.
 *
 * Note: next() and hasNext() should run in average O(1) time
 * and uses O(h) memory, where h is the height of the tree.
 */
public class BSTIterator {

    // session three
    // transform Binary tree to doubly list, in-place, inorder traversal order

    public BSTIterator(){} // dummy

    public TreeNode transformIII(TreeNode root){
        TreeNode[] tm = new TreeNode[2];
        inorderIII(root,tm);
        return tm[0];
    }

    // divide and conquer
    // Suppose we have put the left subtree and right subtree in
    // correct order, we should get the rightmost node from the left subtree, and
    // the leftmost node from the right subtree, and connect them with the current node.
    // also, the return result can be reuse!
    private void inorderIII(TreeNode root, TreeNode[] rs){
        if(root==null) return;
        TreeNode[] left = new TreeNode[2], right = new TreeNode[2];
        inorderIII(root.left, left);
        inorderIII(root.right, right);
        root.left = left[1];
        if(left[1]!=null) left[1].right = root;
        root.right = right[0];
        if(right[0]!=null) right[0].left = root;
        rs[0] = left[0] == null ? root : left[0];
        rs[1] = right[1] == null ? root : right[1];
    }

    // 1. transforming it to a list.
    // 2. using a stack

    // Second session
    private LinkedList<TreeNode> s = new LinkedList<>();

    public BSTIterator(TreeNode root, boolean dummy) {
        TreeNode t = root;
        while(t!=null){
            s.push(t);
            t = t.left;
        }
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext2() {
        return !s.isEmpty();
    }

    /** @return the next smallest number */
    public int next2() {
        TreeNode n = s.pop(), right = n.right;
        while(right!=null){
            s.push(right);
            right = right.left;
        }
        return n.val;
    }

    // Transform!
    // notice this is also an onsite question from microsoft and facebook
    // next mission: recover the BST from this list!
    public TreeNode transform(TreeNode root){
        return inorder(root, false);
    }

    private TreeNode inorder(TreeNode root, boolean left){
        if(root==null) return null;
        TreeNode l = inorder(root.left, true), r = inorder(root.right, false);
        if(l!=null){
            root.left = l;
            l.right = root;
        }
        if(r!=null){
            root.right = r;
            r.left = root;
        }
        TreeNode rs = root;
        if(left){
            while(rs.right!=null) rs = rs.right;
        }else{
            while(rs.left!=null) rs = rs.left;
        }
        return rs;
    }

    TreeNode head;

    public BSTIterator(TreeNode root, int dummy) {
        head = transform(root);
    }

    public boolean hasNext3() {
        return head!=null;
    }

    /** @return the next smallest number */
    public int next3() {
        int val = head.val;
        head = head.right;
        return val;
    }

    // Recover
    // notice that we now only have an inorder sequence of the
    // tree, so we cannot recover the exactly as the given tree.
    public TreeNode recover(TreeNode root){
        if(root==null) return null;
        int count = 0;
        TreeNode t = root;
        while(t!=null){
            ++count;
            t = t.right;
        }
        return recoverSub(root, count);
    }

    private TreeNode recoverSub(TreeNode t, int count){
        if(count==0) return null;
        if(count==1){
            t.left = t.right = null;
            return t;
        }
        int mid = count/2;
        TreeNode tm = t;
        for(int i=0;i<mid;i++) tm = tm.right;
        tm.left = recoverSub(t, mid);
        tm.right = recoverSub(tm.right,count-mid-1);
        return tm;
    }

    public static void main(String[] args) {
        Integer[] a = {1,2,3,4,5,6,7,null,100,9,8,null,101,null,null,null,null,102};
        BSTIterator s = new BSTIterator();
        TreeNode t = new TreeNode(a);
        t = s.transformIII(t);
//        TreeNode t = new TreeNode(a);
//        BSTIterator s = new BSTIterator(t, 3);
//        TreeNode tm = s.head;
//        while(tm.right!=null){
//            System.out.print(tm.val+" ");
//            tm = tm.right;
//        }
//        System.out.println(tm.val);
//        while(tm.left!=null){
//            System.out.print(tm.val+" ");
//            tm = tm.left;
//        }
//        System.out.println(tm.val);
//        t = s.recover(s.head);
    }

    // easiest one, do an inorder traverse and change sequences
    // Accepted
    TreeNode dummy = new TreeNode(-1), p = dummy;

    private void inorder2(TreeNode r){
        if(r==null) return;
        inorder2(r.left);
        p.right = r;
        p = r;
        inorder2(r.right);
    }

    // ============== First session ===================//
    // most intuitive way
    private Deque<TreeNode> stack = new LinkedList<>();

    public BSTIterator(TreeNode root) {
        while(root!=null){
            stack.add(root);
            root = root.left;
        }
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /** @return the next smallest number */
    public int next() {
        TreeNode n = stack.pollLast(),next = n.right;
        while(next!=null){
            stack.add(next);
            next = next.left;
        }
        return n.val;
    }
}
