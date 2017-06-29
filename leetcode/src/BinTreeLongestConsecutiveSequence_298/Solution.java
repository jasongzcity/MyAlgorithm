package BinTreeLongestConsecutiveSequence_298;

import BinaryTree.TreeNode;

/**
 * Given a binary tree, find the length of the longest consecutive sequence path.
 *
 * The path refers to any sequence of nodes from some starting node to
 * any node in the tree along the parent-child connections.
 * The longest consecutive path need to be from
 * parent to child (cannot be the reverse).
 *
 * For example,
 *        1
 *         \
 *          3
 *         / \
 *        2   4
 *             \
 *              5
 * Longest consecutive sequence path is 3-4-5, so return 3.
 *
 *       2
 *        \
 *         3
 *        /
 *       2
 *      /
 *     1
 * Longest consecutive sequence path is 2-3,not3-2-1, so return 2.
 */
public class Solution {

    private int longest = 1;

    public int longestConsecutive(TreeNode root) {
        if(root==null) return 0;
        preorder(root,0,root.val-1);
        return longest;
    }

    private void preorder(TreeNode root,int curLength,int parentVal){
        if(root==null) return;
        if(root.val==parentVal+1){
            if(++curLength>longest) longest = curLength;
        }else{
            curLength = 1;
        }
        preorder(root.left,curLength,root.val);
        preorder(root.right,curLength,root.val);
    }
}
