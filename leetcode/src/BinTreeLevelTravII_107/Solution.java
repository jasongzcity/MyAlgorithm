package BinTreeLevelTravII_107;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree,
 * return the bottom-up level order traversal of its nodes' values.
 * (ie, from left to right, level by level from leaf to root).
 *
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *  /  \
 * 15   7
 * return its bottom-up level order traversal as:
 * [
 *  [15,7],
 *  [9,20],
 *  [3]
 * ]
 */
public class Solution {
    public static List<List<Integer>> levelOrderBottom(TreeNode root){
        List<List<Integer>> rs = new LinkedList<>();
        preorder(root,rs,1);
        return rs;
    }

    private static void preorder(TreeNode root,List<List<Integer>> rs,int level){
        if(root==null) return;
        List<Integer> list;
        if(rs.size()<level){ // first came to this level
            list = new ArrayList<>();
            rs.add(0,list);
        }else{
            list = rs.get(rs.size()-level);
        }
        list.add(root.val);
        preorder(root.left,rs,level+1);
        preorder(root.right,rs,level+1);
    }
}
