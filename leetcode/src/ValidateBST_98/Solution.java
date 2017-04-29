package ValidateBST_98;

import BinaryTree.TreeNode;

/**
 * Given a binary tree, determine if it is a valid binary search tree (BST).
 *
 * Assume a BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 */
public class Solution {
    public static boolean isValidBST(TreeNode root) {
        if(root==null) return true;
        return isSubTreeValid(root.left,Long.MIN_VALUE,root.val)&&
                isSubTreeValid(root.right,root.val,Long.MAX_VALUE);
    }

    private static boolean isSubTreeValid(TreeNode child,long lowerbound,long upperbound){
        if(child==null) return true;
        return child.val<upperbound&&child.val>lowerbound&&
                isSubTreeValid(child.left,lowerbound,child.val)&&
                isSubTreeValid(child.right,child.val,upperbound);
    }

    //
//    private static boolean isSubTreeValid(TreeNode child,int parent,
//                                          long grandparent,int flag){
//        if(child==null) return true;
//        boolean rs = true;
//        if(flag==1||flag==3){ // child is parent's left child
//            rs &= child.val<parent;
//            if(flag==1) rs &= child.val<grandparent; // grandparent's left child
//            else rs &= child.val>grandparent;        // grandparent's right child
//            rs &= isSubTreeValid(child.left,child.val,parent,1)&&
//                    isSubTreeValid(child.right,child.val,parent,2);
//        }else{ // child is parent's right child
//            rs &= child.val>parent;
//            if(flag==2) rs &= child.val<grandparent;
//            else rs &= child.val>grandparent;
//            rs &= isSubTreeValid(child.left,child.val,parent,3)&&
//                    isSubTreeValid(child.right,child.val,parent,4);
//        }
//        return rs;
//    }
}
