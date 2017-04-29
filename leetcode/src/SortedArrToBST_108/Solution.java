package SortedArrToBST_108;

import BinaryTree.TreeNode;

/**
 * Given an array where elements are sorted in ascending order,
 * convert it to a height balanced BST.
 **/
public class Solution {
    public static TreeNode sortedArrayToBST(int[] nums) {
        return getSubTree(nums,0,nums.length);
    }

    // note: hi exclusive
    private static TreeNode getSubTree(int[] a,int lo,int hi){
        if(lo==hi) return null;
        if(lo+1==hi) return new TreeNode(a[lo]);
        int mid = (lo+hi)>>1;
        TreeNode root = new TreeNode(a[mid]);
        root.left = getSubTree(a,lo,mid);
        root.right = getSubTree(a,mid+1,hi);
        return root;
    }
}
