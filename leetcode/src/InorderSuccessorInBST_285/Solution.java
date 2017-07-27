package InorderSuccessorInBST_285;

import BinaryTree.TreeNode;

/**
 * Given a binary search tree and a node in it,
 * find the in-order successor of that node in the BST.
 *
 * Note: If the given node has no in-order successor in the tree, return null.
 */
public class Solution {

    private TreeNode prev = null;

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if(p.right!=null){
            TreeNode rs = p.right;
            while(rs.left!=null) rs = rs.left;
            return rs;
        }else{
            inorder(root,p);
            return p.right;
        }
    }

    private void inorder(TreeNode root,TreeNode p){
        if(root==null) return;
        inorderSuccessor(root.left,p);
        if(prev!=null&&prev.right==null) prev.right = root;
        prev = root;
        inorderSuccessor(root.right,p);
    }

    // Brilliant BST solution
    public TreeNode inorderSuccessor2(TreeNode root, TreeNode p){
        TreeNode cur = root,rs = null;
        while(cur!=null){
            if(p.val<cur.val){
                rs = cur;
                cur = cur.left;
            }else{
                cur = cur.right;
            }
        }
        return rs;
    }
}
