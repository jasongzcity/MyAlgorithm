package MaximumBinTree_654;

import BinaryTree.TreeNode;

/**
 * Given an integer array with no duplicates.
 * A maximum tree building on this array is defined as follow:
 *
 * The root is the maximum number in the array.
 * The left subtree is the maximum tree constructed
 * from left part subarray divided by the maximum number.
 * The right subtree is the maximum tree constructed
 * from right part subarray divided by the maximum number.
 *
 * Construct the maximum tree by the given array and output the root node of this tree.
 * Example 1:
 * Input: [3,2,1,6,0,5]
 * Output: return the tree root node representing the following tree:
 *      6
 *    /   \
 *   3     5
 *   \    /
 *    2  0
 *     \
 *      1
 * Note:
 * The size of the given array will be in the range [1,1000].
 */
public class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return build(nums,0,nums.length-1);
    }

    // if tree node distributes averagely it's O(nlogn) time,
    // worst case O(n^2) time
    private TreeNode build(int[] a,int begin,int end){
        if(begin==end) return new TreeNode(a[begin]);
        if(begin>end) return null;
        int max = Integer.MIN_VALUE, maxposi = -1;
        for(int i=begin;i<=end;i++){
            if(a[i]>max){
                maxposi = i;
                max = a[i];
            }
        }
        TreeNode root = new TreeNode(max);
        root.left = build(a,begin,maxposi-1);
        root.right = build(a,maxposi+1,end);
        return root;
    }
}
