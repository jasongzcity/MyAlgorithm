package FindLargestValueInEachTreeRow_515;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * You need to find the largest value in each row of a binary tree.
 *
 * Example:
 * Input:
 *      1
 *     / \
 *    3   2
 *   / \   \
 *  5   3   9
*
 * Output: [1, 3, 9]
 */
public class Solution {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> rs = new ArrayList<>();
        walk(root,rs,0);
        return rs;
    }

    private void walk(TreeNode root,List<Integer> rs,int level){
        if(root==null) return;
        if(level==rs.size()) rs.add(root.val);
        else if(root.val>rs.get(level)) rs.set(level,root.val);
        walk(root.left,rs,level+1);
        walk(root.right,rs,level+1);
    }
}
