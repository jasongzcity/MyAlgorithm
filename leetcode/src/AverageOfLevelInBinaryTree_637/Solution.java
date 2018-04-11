package AverageOfLevelInBinaryTree_637;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a non-empty binary tree, return the average value of the nodes on each level in the form of an array.
 *
 * Example 1:
 * Input:
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * Output: [3, 14.5, 11]
 * Explanation:
 * The average value of nodes on level 0 is 3,  on level 1 is 14.5, and on level 2 is 11. Hence return [3, 14.5, 11].
 *
 * Note:
 * The range of node's value is in the range of 32-bit signed integer.
 */
public class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Integer> num = new ArrayList<>();
        List<Long> sum = new ArrayList<>();
        List<Double> rs = new ArrayList<>();

        dfs(root, sum, num, 0);
        for(int i=0;i<sum.size();i++){
            rs.add((double)sum.get(i)/num.get(i));
        }
        return rs;
    }

    private void dfs(TreeNode r, List<Long> sum, List<Integer> num, int level){
        if(r==null) return;
        if(sum.size()==level){
            sum.add((long)r.val);
            num.add(1);
        }else{
            sum.set(level, sum.get(level)+r.val);
            num.set(level, num.get(level)+1);
        }
        dfs(r.left, sum, num, level+1);
        dfs(r.right, sum, num, level+1);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        Integer[] a = {3,9,20,15,7};
        TreeNode t = new TreeNode(a);
        System.out.println(s.averageOfLevels(t));
    }
}
