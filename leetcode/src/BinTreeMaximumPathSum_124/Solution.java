package BinTreeMaximumPathSum_124;

import BinaryTree.TreeNode;

/**
 * Given a binary tree, find the maximum path sum.
 * For this problem, a path is defined as any sequence of nodes from
 * some starting node to any node in the tree along the parent-child connections.
 * The path must contain at least one node and does not need to go through the root.
 *
 * For example:
 * Given the below binary tree,
 *    1
 *   / \
 *  2   3
 *
 * Return 6.
 */
public class Solution {
    private int maxValue = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        int tmp = helper(root);
        return maxValue>tmp?maxValue:tmp;
    }

    private int helper(TreeNode root){
        int leftSum = 0,rightSum = 0;
        if(root.left!=null){
            leftSum = helper(root.left);
            if(leftSum>maxValue) maxValue = leftSum;
        }
        if(root.right!=null){
            rightSum = helper(root.right);
            if(rightSum>maxValue) maxValue = rightSum;
        }
        if(leftSum>0&&rightSum>0) maxValue = Math.max(maxValue,leftSum+root.val+rightSum);
        int maxChild = Math.max(leftSum,rightSum);
        return maxChild>0?maxChild+root.val:root.val;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        Integer[] a1 = new Integer[]{1,2,null,3};
        TreeNode t1 = new TreeNode(a1);
        System.out.println(s.maxPathSum2(t1));
    }

    // the solution below is better.
    private int m = Integer.MIN_VALUE;

    public int maxPathSum2(TreeNode root) {
        m = Integer.MIN_VALUE;
        maxPathDown(root);
        return m;
    }

    private int maxPathDown(TreeNode node) {
        if (node == null) return 0;
        int left = Math.max(0, maxPathDown(node.left));
        int right = Math.max(0, maxPathDown(node.right));
        m = Math.max(m, left + right + node.val);
        return Math.max(left, right) + node.val;
    }
}
