package BinTreePreorderTrav_144;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, return the preorder traversal of its nodes' values.
 *
 * For example:
 * Given binary tree {1,#,2,3},
 *    1
 *    \
 *    2
 *   /
 *  3
 *
 * return [1,2,3].
 *
 * Note: Recursive solution is trivial, could you do it iteratively?
 */
public class Solution {
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> rs = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        TreeNode node = root;
        while(true){
            while(node!=null){
                if(node.right!=null) s.push(node.right);
                rs.add(node.val);
                node = node.left;
            }
            if(s.empty()) break;
            node = s.pop();
        }
        return rs;
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{1,2,4,100,800,7,8};
        TreeNode tree = new TreeNode(a);
        System.out.println(preorderTraversal(tree).toString());
        System.out.println(preorderTraversal(null).toString());
    }
}
