package PathSumII_113;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a binary tree and a sum,
 * find all root-to-leaf paths where each path's sum equals the given sum.
 * For example:
 * Given the binary tree [5,4,8,11,null,13,4,7,2,null,null,5,1] and sum = 22,
 * return
 * [
 *  [5,4,11,2],
 *  [5,8,4,5]
 * ]
 */
public class Solution {
    public static List<List<Integer>> pathSum(TreeNode root, int sum){
        List<Integer> l = new ArrayList<>();
        List<List<Integer>> rs = new ArrayList<>();
        dfs(root,sum,0,l,rs);
        return rs;
    }

    private static void dfs(TreeNode root,int target,int sum,
                            List<Integer> l,List<List<Integer>>rs){
        if(root==null) return;
        l.add(root.val);
        int current = sum+root.val;
        if(root.left==root.right&&current==target) {
            rs.add(new ArrayList<>(l));
            l.remove(l.size()-1);
            return;
        }
        dfs(root.left,target,current,l,rs);
        dfs(root.right,target,current,l,rs);
        l.remove(l.size()-1);
    }

    public static void main(String[] args) {
        TreeNode t = null;
        List<List<Integer>> rs = pathSum(t,0);
        System.out.println(rs.toString());

        Integer[] a = new Integer[]{5,4,8,11,null,13,4,7,2,null,null,5,1};
        t = new TreeNode(a);
        rs = pathSum(t,22);
        System.out.println(rs.toString());
    }
}
