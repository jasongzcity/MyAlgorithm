package MinimumAbsoluteDifferenceInBST_530;

import BinaryTree.TreeNode;

/**
 * Given a binary search tree with non-negative values,
 * find the minimum absolute difference between values of any two nodes.
 *
 * Example:
 * Input:
 *   1
 *    \
 *     3
 *    /
 *   2
 * Output:
 * 1
 * Explanation:
 * The minimum absolute difference is 1,
 * which is the difference between 2 and 1 (or between 2 and 3).
 * Note: There are at least two nodes in this BST.
 */
public class Solution {
    private int prev = -1;
    private int min = Integer.MAX_VALUE;

    public int getMinimumDifference(TreeNode root) {
        inorder(root);
        return min;
    }

    private void inorder(TreeNode root){
        if(root==null) return;
        inorder(root.left);
        if(prev!=-1&&root.val-prev<min) min = root.val-prev;
        prev = root.val;
        inorder(root.right);
    }
}
