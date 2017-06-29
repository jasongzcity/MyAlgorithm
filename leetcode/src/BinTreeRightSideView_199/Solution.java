package BinTreeRightSideView_199;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a binary tree, imagine yourself standing on the right side of it,
 * return the values of the nodes you can see ordered from top to bottom.
 *
 * For example:
 * Given the following binary tree,
 *       1            <---
 *     /   \
 *    2     3         <---
 *     \     \
 *      5     4       <---
 * You should return [1, 3, 4].
 */
public class Solution {
    // using level traverse
    public List<Integer> rightSideView2(TreeNode root) {
        List<Integer> rs = new ArrayList<>();
        if(root==null) return rs;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            int size = q.size();
            TreeNode n = root; // set root to avoid null pointer compiler check
            for(int i=0;i<size;i++){
                n = q.poll();
                if(n.left!=null) q.add(n.left);
                if(n.right!=null) q.add(n.right);
            }
            rs.add(n.val);
        }
        return rs;
    }

    // better recursive solution
    public List<Integer> rightSideView(TreeNode root){
        List<Integer> rs = new LinkedList<>();
        helper(root,rs,1);
        return rs;
    }

    private void helper(TreeNode root,List<Integer> rs,int level){
        if(root==null) return;
        if(level>rs.size()) rs.add(root.val);
        helper(root.right,rs,level+1);
        helper(root.left,rs,level+1);
    }
}
