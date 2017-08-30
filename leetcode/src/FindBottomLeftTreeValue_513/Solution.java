package FindBottomLeftTreeValue_513;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a binary tree, find the leftmost value in the last row of the tree.
 *
 * Example 1:
 * Input:
 *    2
 *   / \
 *  1   3
 * Output:
 * 1
 *
 * Example 2:
 * Input:
 *       1
 *      / \
 *     2   3
 *    /   / \
 *   4   5   6
 *      /
 *     7
 * Output:
 * 7
 *
 * Note: You may assume the tree (i.e., the given root node) is not NULL.
 */
public class Solution {
    // Use 2 queue to do level traverse or....
    public int findBottomLeftValue2(TreeNode root) {
        List<Integer> l = new ArrayList<>();
        dfs(root,l,0);
        return l.get(l.size()-1);
    }

    private void dfs(TreeNode root,List<Integer> l,int level){
        if(root==null) return;
        if(l.size()==level) l.add(root.val);
        dfs(root.left,l,level+1);
        dfs(root.right,l,level+1);
    }

    private int num;
    private int maxLevel;

    // we may even do it without a list.
    public int findBottomLeftValue(TreeNode root){
        walk(root,1);
        return num;
    }

    private void walk(TreeNode root,int level){
        if(root==null) return;
        if(level>maxLevel){
            maxLevel = level;
            num = root.val;
        }
        walk(root.left,level+1);
        walk(root.right,level+1);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        Integer[] a = {1,2,3,4,null,5,6,null,null,null,null,7};
        TreeNode t = new TreeNode(a);
        System.out.println(s.findBottomLeftValue(t));
    }
}
