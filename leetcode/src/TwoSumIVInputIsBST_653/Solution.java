package TwoSumIVInputIsBST_653;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a Binary Search Tree and a target number,
 * return true if there exist two elements in the BST such that their sum
 * is equal to the given target.
 *
 * Example 1:
 * Input:
 *     5
 *    / \
 *   3   6
 *  / \   \
 * 2   4   7
 * Target = 9
 * Output: True
 *
 * Example 2:
 * Input:
 *       5
 *      / \
 *     3   6
 *    / \   \
 *   2   4   7
 * Target = 28
 * Output: False
 */
public class Solution {
    // Solution without using map or set.
    // O(nlogn) solution
    public boolean findTarget2(TreeNode root, int k) {
        return walk(root,root,k);
    }

    private boolean walk(TreeNode cur,TreeNode root,int k) {
        if(cur==null) return false;
        return searchIn(root,cur,k - cur.val)
                ||walk(cur.left,root,k)||walk(cur.right,root,k);
    }

    private boolean searchIn(TreeNode root,TreeNode cur,int target){
        if(root==null) return false;
        if(root.val==target) return root!=cur;
        if(root.val<target) return searchIn(root.right,cur,target);
        else return searchIn(root.left,cur,target);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        Integer[] a = new Integer[]{2,0,3,-4,1};
        TreeNode t = new TreeNode(a);
        System.out.println(s.findTarget(t,-1));
    }

    private void inorder(TreeNode root,List<Integer> l){
        if(root==null) return;
        inorder(root.left,l);
        l.add(root.val);
        inorder(root.right,l);
    }

    public boolean findTarget(TreeNode root, int k) {
        List<Integer> l = new ArrayList<>();
        inorder(root,l);
        // two pointer solution to solve two sum
        int left = 0,right = l.size()-1;
        while(left<right){
            int leftval = l.get(left),rightval = l.get(right);
            if(leftval+rightval==k) return true;
            if(leftval+rightval<k) ++left;
            else --right;
        }
        return false;
    }
}
