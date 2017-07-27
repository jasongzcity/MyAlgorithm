package ClosestBSTValue_272;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a non-empty binary search tree and a target value,
 * find k values in the BST that are closest to the target.
 *
 * Note:
 * Given target value is a floating point.
 * You may assume k is always valid, that is: k ≤ total nodes.
 * You are guaranteed to have only one unique set of k values in the BST
 * that are closest to the target.
 * Follow up:
 * Assume that the BST is balanced,
 * could you solve it in less than O(n) runtime (where n = total nodes)?
 */
public class Solution {
    private LinkedList<Integer> smaller = new LinkedList<>();
    private LinkedList<Integer> larger = new LinkedList<>();

    public List<Integer> closestKValues2(TreeNode root, double target, int k){
        inorder(root,target);
        List<Integer> rs = new ArrayList<>(k);
        while(k>0){
            if(smaller.size()==0)
                while(k-->0) rs.add(larger.pollLast());
            else if(larger.size()==0)
                while(k-->0) rs.add(smaller.pollLast());
            else{
                if(larger.peekLast()-target>target-smaller.peekLast()) rs.add(smaller.pollLast());
                else rs.add(larger.pollLast());
                --k;
            }
        }
        return rs;
    }

    private void inorder(TreeNode root,double target){
        if(root==null) return;
        inorder(root.left,target);
        if(root.val<target) smaller.addLast(root.val);
        else larger.addFirst(root.val);
        inorder(root.right,target);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        TreeNode t = new TreeNode("21");
        System.out.println(s.closestKValues(t,2147483647.0,1));
    }

    // One-pass solution
    // Accepted
    public List<Integer> closestKValues(TreeNode root, double target, int k){
        LinkedList<Integer> deque = new LinkedList<>();
        inorder2(root,target,deque,k);
        return deque;
    }

    private void inorder2(TreeNode root,double target,LinkedList<Integer> deque,int k){
        if(root==null) return;
        inorder2(root.left,target,deque,k);
        if(deque.size()<k) deque.addLast(root.val);
        else{
            if(Math.abs(target-deque.peekFirst())>Math.abs(target-root.val)){
                deque.pollFirst();
                deque.addLast(root.val);
            }else return;
        }
        inorder2(root.right,target,deque,k);
    }
}
