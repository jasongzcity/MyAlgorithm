package BinTreeZigZagLevelTrav_103;

import BinaryTree.TreeNode;

import java.util.*;

/**
 * Given a binary tree, return the zigzag level order traversal of its nodes' values.
 * (ie, from left to right, then right to left for the next level and alternate between).
 *
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 * return its zigzag level order traversal as:
 *
 * [
 *  [3],
 *  [20,9],
 *  [15,7]
 * ]
 */
public class Solution {
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if(root==null) return new ArrayList<>();
        Stack<TreeNode> s1 = new Stack<>(),s2 = new Stack<>();
        boolean rightLeft = false; // when true, push child from right to left into s1
        s1.push(root);
        int levelCount = 1,nextCount = 0;
        List<List<Integer>> rs = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        while(levelCount>0){
            if(rightLeft){
                while(levelCount-->0){
                    TreeNode n = s2.pop();
                    list.add(n.val);
                    if(n.right!=null){
                        s1.push(n.right);
                        ++nextCount;
                    }
                    if(n.left!=null){
                        s1.push(n.left);
                        ++nextCount;
                    }
                }
                rightLeft = false;
            }else{
                while(levelCount-->0){
                    TreeNode n = s1.pop();
                    list.add(n.val);
                    if(n.left!=null){
                        s2.push(n.left);
                        ++nextCount;
                    }
                    if(n.right!=null){
                        s2.push(n.right);
                        ++nextCount;
                    }
                }
                rightLeft = true;
            }
            rs.add(new ArrayList<>(list));
            list.clear();
            levelCount = nextCount;
            nextCount=0;
        }
        return rs;
    }

    public static void main(String[] args) {
        Integer[] a1 = new Integer[]{3,9,20,null,null,15,7};
        TreeNode root = new TreeNode(a1);
        List<List<Integer>> rs = zigzagLevelOrder2(root);
        for(List<Integer> l:rs)
            System.out.println(Arrays.toString(l.toArray()));
    }

    // most voted solution on leetcode
    // combine preorder traverse
    public static List<List<Integer>> zigzagLevelOrder2(TreeNode root){
        List<List<Integer>> rs = new ArrayList<>();
        preorder(root,0,rs);
        return rs;
    }

    private static void preorder(TreeNode root,int level,List<List<Integer>> rs){
        if(root==null) return;
        if(rs.size()==level) rs.add(new LinkedList<>()); // comes to this level at first time
        List<Integer> l = rs.get(level);
        if(level%2==0) l.add(root.val);
        else l.add(0,root.val);
        preorder(root.left,level+1,rs);
        preorder(root.right,level+1,rs);
    }
}
