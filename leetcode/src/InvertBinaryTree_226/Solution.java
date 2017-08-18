package InvertBinaryTree_226;

import BinaryTree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Invert a binary tree.
 *
 *        4
 *      /   \
 *     2     7
 *    / \   / \
 *   1   3 6   9
 * to
 *        4
 *      /   \
 *     7     2
 *    / \   / \
 *   9   6 3   1
 * Trivia:
 * This problem was inspired by this original tweet by Max Howell:
 * Google: 90% of our engineers use the software you wrote (Homebrew),
 * but you can’t invert a binary tree on a whiteboard so fuck off.
 */
public class Solution {
    public TreeNode invertTree(TreeNode root) {
        if(root==null) return null;
        TreeNode temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);
        return root;
    }

    // Iterative
    public TreeNode invertTree2(TreeNode root) {
        if (root == null) return null;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
        return root;
    }
}
