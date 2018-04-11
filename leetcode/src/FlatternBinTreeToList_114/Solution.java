package FlatternBinTreeToList_114;

import BinaryTree.TreeNode;

import java.util.Stack;

/**
 * Given a binary tree, flatten it to a linked list in-place.
 * Hints:
 * If you notice carefully in the flattened tree,
 * each node's right child points to the next node of a pre-order traversal.
 *
 * Example:
 *       1
 *      / \
 *     2   3
 *    / \   \
 *   4   5   6
 *
 * Into :
 *
 * 1
 *  \
 *   2
 *    \
 *     4
 *      \
 *       5
 *        \
 *         3
 *          \
 *           6
 */
public class Solution {

    // third session
    // do it in recursive way
    // notice this is not easy to do for inorder -> list
    private TreeNode prev2 = null;

    public void flattenIII(TreeNode root){
        dfsIII(root);
    }

    private void dfsIII(TreeNode root){
        if(root==null) return;
        dfsIII(root.right);
        dfsIII(root.left);
        root.left = null;
        root.right = prev2;
        prev2 = root;
    }

    // second session
    // try to do it in-place
    public void flattenII(TreeNode root){
        if(root==null) return;
        pre(root);
    }

    // This is very alike the onsite question I have met!
    // check BST tree Iterator(#173) for more information
    // however it's not looking that complicated..
    private TreeNode pre(TreeNode r){
        TreeNode right = r.right, tm;
        if(r.left!=null){
            tm = pre(r.left);
            r.right = r.left;
            r.left = null;
        }else{
            tm = r;
        }
        tm.right = right;
        if(right!=null) return pre(right);
        return tm;
    }

    // Notice: this solution is traditional but pretty slow....
    public static void flatten2(TreeNode root) {
        Stack<TreeNode> s = new Stack<>();
        TreeNode prev = new TreeNode(0);
        while(true){
            while(root!=null){
                if(root.right!=null) s.push(root.right);
                prev.right = root;
                prev.left = null;
                prev = root;
                root = root.left;
            }
            if(s.empty()) break;
            root = s.pop();
        }
    }

    public static void main(String[] args) {
        Integer[] a1 = new Integer[]{1,2,5,3,4,null,6,null,null,7,8,null,null};
        TreeNode root = new TreeNode(a1);
        flatten(root);
    }

    // Non-recursive, no stack solution...
    // This is the smartest solution..
    public static void flatten(TreeNode root){
        while(root!=null){
            if(root.left!=null&&root.right!=null){ // put right into left subtree
                TreeNode left = root.left;
                while(left.right!=null) left = left.right;
                left.right = root.right;
            }
            if(root.left!=null){ // turn left tree to right
                root.right = root.left;
                root.left = null;
            }
            root = root.right;
        }
    }

    // most voted recursive solution
    // the smartest part of this solution is that
    // it uses the reverse order of preorder traverse(notice its not postorder traverse)
    // root->left->right and so this solution traverse in order:
    // right->left->root
    public void flatten3(TreeNode root) {
        if (root == null) return;
        flatten3(root.right);
        flatten3(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }

    private TreeNode prev = null;
}
