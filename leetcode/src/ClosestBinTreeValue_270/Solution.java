package ClosestBinTreeValue_270;

import BinaryTree.TreeNode;

import java.util.LongSummaryStatistics;

/**
 * Given a non-empty binary search tree and a target value,
 * find the value in the BST that is closest to the target.
 *
 * Note:
 * Given target value is a floating point.
 * You are guaranteed to have only one unique value in the BST that is
 * closest to the target.
 */
public class Solution {
    public int closestValue(TreeNode root, double target) {
        TreeNode n = root;
        long lo = Long.MIN_VALUE,hi = Long.MAX_VALUE;
        while(n!=null){
            if(n.val>target){
                hi = n.val;
                n = n.left;
            }else{
                lo = n.val;
                n = n.right;
            }
        }
        if(lo==Long.MIN_VALUE) return (int)hi;
        else if(hi==Long.MAX_VALUE) return (int)lo;
        else return (int)(hi-target>target-lo?lo:hi);
    }

    // recursive solution
    // Return the closest value to the target in the subtree.
    public int closestValue2(TreeNode root, double target){
        int val = root.val;
        TreeNode child = val<target?root.right:root.left;
        if(child==null) return val;
        int childVal = closestValue2(child,target);
        return Math.abs((int)(childVal-target))>Math.abs((int)(val-target))?val:childVal;
    }
}
