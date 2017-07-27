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
    public static List<Integer> inorderTraversal2(TreeNode root) {
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

    // Morris Traversal
    // O(1) space O(n) time
    public static List<Integer> inorderTraversal(TreeNode root){
        List<Integer> rs = new ArrayList<>(64);
        TreeNode cur = root;
        while(cur!=null){
            if(cur.left!=null){
                TreeNode prev = cur.left;
                while(prev.right!=null&&prev.right!=cur) prev = prev.right;
                if(prev.right==null){
                    prev.right = cur;
                    cur = cur.left;
                }else{
                    // we have been here the second time
                    prev.right = null;
                    rs.add(cur.val);
                    cur = cur.right;
                }
            }else{
                rs.add(cur.val);
                cur = cur.right;
            }
        }
        return rs;
    }
}
