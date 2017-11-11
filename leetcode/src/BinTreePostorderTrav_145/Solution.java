package BinTreePostorderTrav_145;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, return the postorder traversal of its nodes' values.
 *
 * For example:
 * Given binary tree {1,#,2,3},
 *
 *  1
 *   \
 *    2
 *   /
 *  3
 *
 * return [3,2,1].
 *
 * Note: Recursive solution is trivial, could you do it iteratively?
 */
public class Solution {

    // great conclusion!
    // https://discuss.leetcode.com
    // /topic/30632/preorder-inorder-and-postorder-iteratively-summarization
    // to be more specific, it's just you add mid->right->left
    // to the list, and reverse the whole list.

    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> rs = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        TreeNode n = root,prev = null;
        while(n!=null){
            s.push(n);
            n = n.left;
        }
        while(!s.empty()){
            TreeNode top = s.peek();
            if(prev==top.right||top.right==null){
                n = s.pop();
                rs.add(n.val);
                prev = n;
            }else{
                n = top.right;
                while(n!=null){
                    s.push(n);
                    n = n.left;
                }
            }
        }
        return rs;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode("1234567");
        System.out.println(postorderTraversal(root).toString());
        System.out.println(postorderTraversal2(root).toString());
    }

    // another solution:
    // traverse in order root->right->left, and reverse the result list.
    public static List<Integer> postorderTraversal2(TreeNode root){
        List<Integer> rs = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        TreeNode n = root;
        while(true){
            while(n!=null){
                if(n.left!=null) s.push(n.left);
                rs.add(n.val);
                n = n.right;
            }
            if(s.empty()) break;
            n = s.pop();
        }
        Collections.reverse(rs);
        return rs;
    }
}
