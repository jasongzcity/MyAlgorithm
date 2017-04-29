package BinTreeInorderTrav_94;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, return the inorder traversal of its nodes' values.
 *
 * For example:
 * Given binary tree [1,null,2,3],
 *
 * return [1,3,2].
 *
 * Note: Recursive solution is trivial, could you do it iteratively?
 */
public class Solution {
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> rs = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode n = root;
        while(true) {
            while (n != null) {
                stack.push(n);
                n = n.left;
            }
            if(stack.empty()) break;
            n = stack.pop();
            rs.add(n.val);
            n = n.right;
        }
        return rs;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode("1234567");
        List<Integer> l = inorderTraversal(root);
        System.out.println(l.toString());
    }
}
