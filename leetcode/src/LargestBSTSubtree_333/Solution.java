package LargestBSTSubtree_333;

import BinaryTree.TreeNode;

/**
 * Given a binary tree, find the largest subtree which is a Binary Search Tree (BST),
 * where largest means subtree with largest number of nodes in it.
 *
 * Note:
 * A subtree must include all of its descendants.
 * Here's an example:
 *        10
 *       / \
 *      5  15
 *     / \   \
 *    1   8   7
 * The Largest BST Subtree in this case is the highlighted one(518).
 * The return value is the subtree's size, which is 3.
 * Follow up:
 * Can you figure out ways to solve it with O(n) time complexity?
 */
public class Solution {
    private int max = 0;

    public int largestBSTSubtree(TreeNode root) {
        walk(root,new int[3]);
        return max;
    }

    private boolean walk(TreeNode root,int[] info){
        if(root==null){
            info[0] = Integer.MAX_VALUE;
            info[1] = Integer.MIN_VALUE;
            info[2] = 0;
            return true;
        }
        int[] rtInfo = new int[3];
        int leftlow = 0,lefthi = 0,leftnum = 0,rightlow = 0,righthi = 0,rightnum = 0;
        boolean left,right;
        if(left = walk(root.left,rtInfo)){
            leftlow = rtInfo[0];
            lefthi = rtInfo[1];
            leftnum = rtInfo[2];
        }
        if(right = walk(root.right,rtInfo)){
            rightlow = rtInfo[0];
            righthi = rtInfo[1];
            rightnum = rtInfo[2];
        }
        if(left&&right&&root.val>lefthi&&root.val<rightlow){
            int num = leftnum+rightnum+1;
            max = Math.max(max,num);
            info[0] = leftnum==0?root.val:leftlow;
            info[1] = rightnum==0?root.val:righthi;
            info[2] = num;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        Integer[] a = {3,1,null,2,null,null,4};
        TreeNode t = new TreeNode(a);
        System.out.println(s.largestBSTSubtree(t));
    }
}
