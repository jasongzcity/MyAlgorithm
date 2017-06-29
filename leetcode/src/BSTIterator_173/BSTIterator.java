package BSTIterator_173;

import BinaryTree.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Implement an iterator over a binary search tree (BST).
 * Your iterator will be initialized with the root node of a BST.
 *
 * Calling next() will return the next smallest number in the BST.
 *
 * Note: next() and hasNext() should run in average O(1) time
 * and uses O(h) memory, where h is the height of the tree.
 */
public class BSTIterator {

    private Deque<TreeNode> stack = new LinkedList<>();

    public BSTIterator(TreeNode root) {
        while(root!=null){
            stack.add(root);
            root = root.left;
        }
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /** @return the next smallest number */
    public int next() {
        TreeNode n = stack.pollLast(),next = n.right;
        while(next!=null){
            stack.add(next);
            next = next.left;
        }
        return n.val;
    }
}
