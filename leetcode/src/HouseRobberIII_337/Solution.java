package HouseRobberIII_337;

import BinaryTree.TreeNode;

import java.util.Map;

/**
 * The thief has found himself a new place for his thievery again.
 * There is only one entrance to this area, called the "root."
 * Besides the root, each house has one and only one parent house.
 * After a tour, the smart thief realized that "all houses
 * in this place forms a binary tree".
 * It will automatically contact the police
 * if two directly-linked houses were broken into on the same night.
 *
 * Determine the maximum amount of money the thief can rob tonight
 * without alerting the police.
 *
 * Example 1:
 *        3
 *       / \
 *      2   3
 *       \   \
 *        3   1
 * Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
 *
 * Example 2:
 *       3
 *      / \
 *     4   5
 *    / \   \
 *   1   3   1
 * Maximum amount of money the thief can rob = 4 + 5 = 9.
 */
public class Solution {
    // Very clear and detailed solution and explanation on leetcode:
    // https://discuss.leetcode.com/topic/39834/step-by-step-tackling-of-the-problem
    // I am trying to redo the whole thought.

    // naive solution
    public int rob(TreeNode root) {
        int[] rs = helper(root);
        return Math.max(rs[0],rs[1]);
    }

    // improvement: we compute the money of the "grandchild" too early,
    // more naturally, we should leave it to the child node.
    // In the return array, the first element is the largest amount of money
    // we can rob while we don't rob the current root,
    // the second element is the largest amount of money we can rob while we
    // have robbed the current root.
    private int[] helper(TreeNode root){
        int[] rs = new int[2];
        if(root==null) return rs;

        int[] left = helper(root.left);
        int[] right = helper(root.right);

        rs[1] = root.val+left[0]+right[0];
        rs[0] = Math.max(left[1],left[0])+Math.max(right[1],right[0]);
        return rs;
    }

    // return the largest amount of money you can rob
    // from this tree
    // It runs slowly, because it recompute overlapped subproblems.
    // when you rob root.left and root.right, you obviously recompute
    // their left and right subtree which you have computed before....
    private int naive(TreeNode root){
        if(root==null) return 0;

        int val = root.val;
        if(root.left!=null) val+=naive(root.left.left)+naive(root.left.right);
        if(root.right!=null) val+=naive(root.right.left)+naive(root.right.right);
        return Math.max(val,naive(root.left)+naive(root.right));
    }

    // another naive improvement is using a map to record the subtree's
    // value(so we don't compute again)
    // more like some top-down dp, huh?
    private int naive2(TreeNode root,Map<TreeNode,Integer> map){
        if(root==null) return 0;
        Integer value = map.get(root);
        if(value!=null) return value;

        int val = root.val;
        if(root.left!=null) val+=naive2(root.left.left,map)+naive2(root.left.right,map);
        if(root.right!=null) val+=naive2(root.right.left,map)+naive2(root.right.right,map);
        int rs = Math.max(val,naive2(root.left,map)+naive2(root.right,map));
        map.put(root,rs);
        return rs;
    }
}
