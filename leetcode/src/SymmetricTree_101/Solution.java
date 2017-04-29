package SymmetricTree_101;

import BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Given a binary tree, check whether it is a mirror of itself
 * (ie, symmetric around its center).
 *
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric.
 * Note:
 * Bonus points if you could solve it both recursively and iteratively.
 */
public class Solution {
    public static boolean isSymmetric(TreeNode root) {
        if(root==null) return true;
        return symmetricTrees(root.left,root.right);
    }

    private static boolean symmetricTrees(TreeNode t1,TreeNode t2){
        if(t1==null&&t2==null) return true;
        if((t1==null^t2==null)||t1.val!=t2.val) return false;
        return symmetricTrees(t1.left,t2.right)&&symmetricTrees(t1.right,t2.left);
    }

    // Iterative method, use 2 queues to do level traverse
    // it would be much conciser if ArrayDeque support null elements
    public static boolean isSymmetric2(TreeNode root){
        if(root==null) return true;
        Queue<TreeNode> q1 = new ArrayDeque<>(),q2 = new ArrayDeque<>();
        if(root.right!=null^root.left!=null) return false;
        if(root.left==root.right) return true; // both null
        q1.add(root.left);
        q2.add(root.right);
        while(!q1.isEmpty()){
            TreeNode t1 = q1.poll(),t2 = q2.poll();
            if(t1.val!=t2.val) return false;
            if((t1.left!=null^t2.right!=null)||
                    (t1.right!=null^t2.left!=null)) return false;
            if(t1.left!=t2.right){ // not null
                q1.add(t1.left);
                q2.add(t2.right);
            }
            if(t1.right!=t2.left){ // not null
                q1.add(t1.right);
                q2.add(t2.left);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Integer[] a1 = new Integer[]{1,2,2,3,4,4,3,5,6,7,8,8,7,6,5};
        Integer[] a2 = new Integer[]{1,2,2,3,4,4,3,null,null,5,6,null,null,6,5};
        TreeNode t1 = new TreeNode(a1);
        TreeNode t2 = new TreeNode(a2);
        System.out.println(isSymmetric2(t1));
        System.out.println(isSymmetric2(t2));
    }
}
