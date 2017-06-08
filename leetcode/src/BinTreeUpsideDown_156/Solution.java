package BinTreeUpsideDown_156;

import BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given a binary tree where all the right nodes are either
 * leaf nodes with a sibling (a left node that shares the same parent node) or empty,
 * flip it upside down and turn it into a tree where the original
 * right nodes turned into left leaf nodes. Return the new root.
 * For example:
 *
 * Given a binary tree {1,2,3,4,5},
 *     1
 *    / \
 *   2   3
 *  / \
 * 4   5
 *
 * return the root of the binary tree [4,5,2,#,#,3,1].
 *     4
 *    / \
 *   5   2
 *      / \
 *     3   1
 */
public class Solution {
    // O(n) space,iterative solution
    public TreeNode upsideDownBinaryTree(TreeNode root){
        if(root==null) return null;
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode r = root;
        while(r.left!=null){
            stack.push(r);
            r = r.left;
        }
        TreeNode newRoot = r,pop;
        while(!stack.isEmpty()){
            pop = stack.pop();
            r.right = pop;
            r.left = pop.right; // r's sibling
            r = pop;
        }
        r.left = r.right = null;
        return newRoot;
    }

    // better iterative solution
    public TreeNode upsideDownBinaryTree3(TreeNode root){
        TreeNode cur = root,prev = null,sib = null,left;
        while(cur!=null){
            left = cur.left;
            cur.left = sib;
            sib = cur.right;
            cur.right = prev;
            prev = cur;
            cur = left;
        }
        return prev;
    }

    private TreeNode nRoot = null;

    // recursive solution
    public TreeNode upsideDownBinaryTree2(TreeNode root){
        if(root==null) return null;
        walk(root,null);
        root.left = null;
        return nRoot;
    }

    private void walk(TreeNode cur,TreeNode parent){
        if(cur.left==null){
            nRoot = cur;
            cur.right = parent;
            return;
        }
        walk(cur.left,cur);
        cur.left.left = cur.right;
        cur.right = parent;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode("12");
        Solution s = new Solution();
        TreeNode nt = s.upsideDownBinaryTree2(root);
    }
}
