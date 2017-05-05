package NextRightPointer_116;

import TreeLinkedNode.TreeLinkNode;

/**
 * Given a binary tree.
 * Populate each next pointer to point to its next right node.
 * If there is no next right node, the next pointer should be set to NULL.
 *
 * Initially, all next pointers are set to NULL.
 * Note:
 * You may only use constant extra space.
 * You may assume that it is a perfect binary tree
 * (ie, all leaves are at the same level, and every parent has two children).
 */
public class Solution {
    public static void connect(TreeLinkNode root) {
        if(root==null||root.left==root.right) return;
        root.left.next = root.right;
        if(root.next!=null) root.right.next = root.next.left;
        connect(root.left);
        connect(root.right);
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        TreeLinkNode t = new TreeLinkNode(a);
        connect(t);
    }
}
