package LowestCommonAncestorInBST_235;

import BinaryTree.TreeNode;

/**
 * Given a binary search tree (BST),
 * find the lowest common ancestor (LCA) of two given nodes in the BST.
 *
 * According to the definition of LCA on
 * Wikipedia: “The lowest common ancestor is defined between two nodes
 * v and w as the lowest node in T that has both v and w as descendants
 * (where we allow a node to be a descendant of itself).”
 *
 * _______6______
 * /              \
 * ___2__          ___8__
 * /      \        /      \
 * 0      _4       7       9
 *       /  \
 *      3   5
 *
 * For example, the lowest common ancestor (LCA) of nodes 2 and 8 is 6.
 * Another example is LCA of nodes 2 and 4 is 2
 */
public class Solution {
    // iterative solution
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while((p.val-root.val)*(q.val-root.val)>0)
            root = root.val>p.val?root.left:root.right;
        return root;
    }

    // recursive solution
    public TreeNode lowestCommonAncestor2(TreeNode root,TreeNode p, TreeNode q) {
        if((p.val-root.val)*(q.val-root.val)<=0) return root;
        return root.val>p.val?lowestCommonAncestor2(root.left,p,q):
                lowestCommonAncestor2(root.right,p,q);
    }
}
