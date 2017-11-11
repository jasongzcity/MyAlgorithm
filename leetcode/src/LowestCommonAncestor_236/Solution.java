package LowestCommonAncestor_236;

import BinaryTree.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Given a binary tree, find the lowest common ancestor (LCA) of
 * two given nodes in the tree.
 *
 * According to the definition of LCA on Wikipedia:
 * “The lowest common ancestor is defined between two nodes
 * v and w as the lowest node in T that has both v and w as descendants
 * (where we allow a node to be a descendant of itself).”
 *
 *        _______3______
 *       /              \
 *    ___5__          ___1__
 *   /      \        /      \
 *   6      2       0       8
 *        /  \
 *       7   4
 * For example, the lowest common ancestor (LCA) of nodes 5 and 1 is 3.
 * Another example is LCA of nodes 5 and 4 is 5
 */
public class Solution {

    // Second session
    public TreeNode lowestCommonAncestorII(TreeNode root, TreeNode p, TreeNode q){
        return find(root,p,q);
    }

    private TreeNode find(TreeNode root, TreeNode p, TreeNode q){
        if(root==null) return null;
        if(root==p||root==q) return root;
        TreeNode left = find(root.left,p,q), right = find(root.right,p,q);
        if(left!=null&&right!=null) return root;
        if(left==right) return null; // both child not found
        return left==null?right:left;
    }

    // iterative solution
    // may look ugly but it got accepted :-))))
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (p == q) return p;
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode n = root, target;
        while (true) {
            while (n != null) {
                stack.push(n);
                n = n.left;
            }
            n = stack.pop();
            if (n == p || n == q) {
                if (n == p) target = q;
                else target = p;
                break;
            }
            n = n.right;
        }
        // find target in right trees.
        TreeNode prev, rs = n;
        n = n.right;
        Deque<TreeNode> stack2 = new LinkedList<>();
        while (true) {
            while (n != null) {
                stack2.push(n);
                n = n.left;
            }
            if (stack2.isEmpty()) {
                do {
                    prev = rs;
                    rs = stack.pop();
                } while (rs.right == prev);
                n = rs;
            } else {
                n = stack2.pop();
            }
            if (n == target) return rs;
            n = n.right;
        }
    }

    // most voted solution on leetcode
    // recursive solution
    // This solution is super fast
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        return left == null ? right : (right == null ? left : root);
    }
}
