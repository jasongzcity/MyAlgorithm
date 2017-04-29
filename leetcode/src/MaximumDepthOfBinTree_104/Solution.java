package MaximumDepthOfBinTree_104;

import BinaryTree.TreeNode;

/**
 * Given a binary tree, find its maximum depth.
 *
 * The maximum depth is the number of nodes along the longest path from
 * the root node down to the farthest leaf node.
 */
public class Solution {
    // recursive solution is trivial, lol
    public static int maxDepth(TreeNode root) {
        return checkDepth(root,0);
    }

    private static int checkDepth(TreeNode n,int depth){
        if(n==null) return depth;
        return Math.max(checkDepth(n.left,depth+1),checkDepth(n.right,depth+1));
    }
}
