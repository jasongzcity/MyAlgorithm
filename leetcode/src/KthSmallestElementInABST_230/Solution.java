package KthSmallestElementInABST_230;

import BinaryTree.TreeNode;

/**
 * Given a binary search tree,
 * write a function kthSmallest to find the kth smallest element in it.
 *
 * Note:
 * You may assume k is always valid, 1 <= k <= BST's total elements.
 *
 * Follow up:
 * What if the BST is modified (insert/delete operations)
 * often and you need to find the kth smallest frequently?
 * How would you optimize the kthSmallest routine?
 */
public class Solution {

    private int result = -1;
    private int count = 1;

    // naive inorder traverse
    public int kthSmallest2(TreeNode root, int k) {
        inorder(root,k);
        return result;
    }

    private void inorder(TreeNode root,int k){
        if(root==null) return;
        inorder(root.left,k);
        if(count++==k){
            result = root.val;
            return;
        }
        inorder(root.right,k);
    }

    // binary search
    public int kthSmallest(TreeNode root, int k){
        int count = countNodes(root.left);
        if(k<=count) return kthSmallest(root.left,k);
        else if(k>count+1) return kthSmallest(root.right,k-(count+1));
        else return root.val; // the node is exactly the kth smallest.
    }

    private int countNodes(TreeNode root){
        if(root==null) return 0;
        return 1+countNodes(root.left)+countNodes(root.right);
    }

    // the solution above do a lot of duplicate calculation which are
    // totally unnecessary.
    // So we can rebuild a tree which can store current count.
    // The idea is the same.
}
