package UniqueBSTII_95;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an integer n, generate all structurally unique BST's (binary search trees)
 * that store values 1...n.
 *
 * Notice: its the solution of #96
 **/
public class Solution {
    // top-down
    public static List<TreeNode> generateTrees(int n) {
        if(n==0) return new ArrayList<>();
        return subTrees(1,n+1);
    }

    // generate subtrees and return in list
    // end not inclusive
    private static List<TreeNode> subTrees(int begin,int end){
        List<TreeNode> rs = new ArrayList<>();
        if(begin==end){ // null
            rs.add(null);
            return rs;
        }
        if(end==begin+1){
            rs.add(new TreeNode(begin));
            return rs;
        }
        for(int i=begin;i<end;i++){
            List<TreeNode> left = subTrees(begin,i);
            List<TreeNode> right = subTrees(i+1,end);
            for(TreeNode leftchild:left){
                for(TreeNode rightchild:right){
                    TreeNode root = new TreeNode(i);
                    root.left = leftchild;
                    root.right = rightchild;
                    rs.add(root);
                }
            }
        }
        return rs;
    }
}
