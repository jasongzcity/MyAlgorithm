package BinTreeLevelTrav_102;

import BinaryTree.TreeNode;

import java.util.*;

/**
 * Given a binary tree, return the level order traversal of its nodes' values.
 * (ie, from left to right, level by level).
 *
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 *
 * return its level order traversal as:
 *
 * [
 *  [3],
 *  [9,20],
 *  [15,7]
 * ]
 */
public class Solution {
    public static List<List<Integer>> levelOrder(TreeNode root) {
        if(root==null) return new ArrayList<>();
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        List<List<Integer>> rs = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        int currentCount = 1,nextCount = 0;
        while(!q.isEmpty()){
            TreeNode n = q.poll();
            current.add(n.val);
            if(n.left!=null){
                ++nextCount;
                q.add(n.left);
            }
            if(n.right!=null){
                ++nextCount;
                q.add(n.right);
            }
            if(--currentCount==0){ // get ready for next level
                rs.add(new ArrayList<>(current));
                current.clear();
                currentCount = nextCount;
                nextCount = 0;
            }
        }
        return rs;
    }

    public static void main(String[] args) {
        Integer[] a1 = new Integer[]{3,9,20,null,null,15,7};
        TreeNode root = new TreeNode(a1);
        List<List<Integer>> rs = levelOrder(root);
        for(List<Integer> l:rs)
            System.out.println(Arrays.toString(l.toArray()));
    }
}
