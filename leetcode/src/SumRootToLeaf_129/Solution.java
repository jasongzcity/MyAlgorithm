package SumRootToLeaf_129;

import BinaryTree.TreeNode;

/**
 * Given a binary tree containing digits from 0-9 only,
 * each root-to-leaf path could represent a number.
 *
 * An example is the root-to-leaf path 1->2->3 which represents the number 123.
 *
 * Find the total sum of all root-to-leaf numbers.
 *
 * For example,
 *     1
 *    / \
 *   2   3
 *
 * The root-to-leaf path 1->2 represents the number 12.
 * The root-to-leaf path 1->3 represents the number 13.
 * Return the sum = 12 + 13 = 25.
 */
public class Solution {
    private int sum = 0;

    public int sumNumbers(TreeNode root) {
        helper(root,0);
        return sum;
    }

    private void helper(TreeNode root,int s){
        if(root==null) return;
        s = s*10+root.val;
        if(root.right==root.left) sum+=s; // leaf found
        helper(root.left,s);
        helper(root.right,s);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        TreeNode t = new TreeNode("123");
        System.out.println(s.sumNumbers(t));
    }
}
