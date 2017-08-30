package CountUnivalueSubtrees_250;

import BinaryTree.TreeNode;

/**
 * Given a binary tree, count the number of uni-value subtrees.
 *
 * A Uni-value subtree means all nodes of the subtree have the same value.
 *
 * For example:
 * Given binary tree,
 *        5
 *       / \
 *      1   5
 *     / \   \
 *    5   5   5
 * return 4.
 */
public class Solution {
    // recursive solution
    private int count = 0;

    public int countUnivalSubtrees(TreeNode root) {
        walk(root,0);
        return count;
    }

    private boolean walk(TreeNode root,int parent){
        if(root==null) return true;
        boolean left = walk(root.left,root.val),right = walk(root.right,root.val);
        if(left&&right){
            ++count;
            return parent==root.val;
        }
        return false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        Integer[] a = new Integer[]{5,1,5,5,5,null,5};
        TreeNode t = new TreeNode(a);
        System.out.println(s.countUnivalSubtrees(t));
    }
}
