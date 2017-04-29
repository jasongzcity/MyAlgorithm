package BalancedBinTree_110;

import BinaryTree.TreeNode;

/**
 * Given a binary tree, determine if it is height-balanced.
 *
 * For this problem, a height-balanced binary tree is defined as a binary tree
 * in which the depth of the two subtrees of every node never differ by more than 1.
 */
public class Solution {
    public static boolean isBalanced(TreeNode root) {
        return chechSubTree(root,new int[1]);
    }

    // This will be much more elegant if Java support reference in parameters.
    private static boolean chechSubTree(TreeNode root,int[] height){
        if(root==null) return true;
        int[] leftHeight = new int[1],rightHeight = new int[1];
        boolean left = chechSubTree(root.left,leftHeight);
        boolean right = chechSubTree(root.right,rightHeight);
        height[0] = Math.max(leftHeight[0],rightHeight[0])+1;
        return left&&right&&Math.abs(leftHeight[0]-rightHeight[0])<2;
    }

    public static boolean isBalanced2(TreeNode root){
        return chechSubTree2(root)!=-1;
    }

    // special use of -1.
    private static int chechSubTree2(TreeNode root){
        if(root==null) return 0;
        int left = chechSubTree2(root.left);
        if(left==-1) return -1;
        int right = chechSubTree2(root.right);
        if(right==-1) return -1;
        return Math.abs(left-right)<2?Math.max(left,right)+1:-1;
    }
}
