package PathSumIII_437;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * You are given a binary tree in which each node contains an integer value.
 * Find the number of paths that sum to a given value.
 * The path does not need to start or end at the root or a leaf,
 * but it must go downwards (traveling only from parent nodes to child nodes).
 * The tree has no more than 1,000 nodes and the values are in the
 * range -1,000,000 to 1,000,000.
 *
 * Example:
 * Given tree = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
 * Return 3. The paths that sum to 8 are:
 * 1.  5 -> 3
 * 2.  5 -> 2 -> 1
 * 3. -3 -> 11
 */
public class Solution {

    private int count = 0;

    // This is slow
    public int pathSum2(TreeNode root, int sum) {
        List<Integer> l = new ArrayList<>(100);
        dfs(root,sum,0,l);
        return count;
    }

    private void dfs(TreeNode root,int target,int sum,List<Integer> l){
        if(root==null) return;
        int current = sum+root.val;
        l.add(root.val);
        if(current==target) ++count;
        for(int i=0;i<l.size()-1;i++){
            current -= l.get(i);
            if(current==target) ++count;
        }
        dfs(root.left,target,sum+root.val,l);
        dfs(root.right,target,sum+root.val,l);
        l.remove(l.size()-1);
    }

    // Another solution is to call from top to leaves recursively to check
    // if any path from the node met the requirement.
    // This is also slow.......
    public int pathSum(TreeNode root, int sum){
        dfs(root,sum);
        return count;
    }

    private void dfs(TreeNode root,int target){
        if(root==null) return;
        dfsSum(root,target,0);

        dfs(root.left,target);
        dfs(root.right,target);
    }

    private void dfsSum(TreeNode root,int target,int sum){
        if(root==null) return;
        int current = sum+root.val;
        if(current==target) ++count;
        dfsSum(root.left,target,current);
        dfsSum(root.right,target,current);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        Integer[] a = new Integer[]{10,5,-3,3,2,null,11,3,-2,null,1};
        TreeNode t = new TreeNode(a);
        System.out.println(s.pathSum3(t,8));

        Integer[] a1 = new Integer[]{1,null,2,null,3,null,4,null,5};
        t = new TreeNode(a1);
        s.count = 0;
        System.out.println(s.pathSum3(t,3));
//        System.out.println(s.pathSum2(t,8));
    }

    // A much faster O(n) solution
    // most voted solution on leetcode
    // use a map to store the sum of the sum
    public int pathSum3(TreeNode root, int sum) {
        HashMap<Integer, Integer> preSum = new HashMap<>();
        preSum.put(0,1);
        return helper(root, 0, sum, preSum);
    }

    public int helper(TreeNode root, int currSum, int target,
                      HashMap<Integer, Integer> preSum) {
        if (root == null) {
            return 0;
        }

        currSum += root.val;
        int res = preSum.getOrDefault(currSum - target, 0);
        preSum.put(currSum, preSum.getOrDefault(currSum, 0) + 1);

        res += helper(root.left, currSum, target, preSum) + helper(root.right, currSum, target, preSum);
        preSum.put(currSum, preSum.get(currSum) - 1);
        return res;
    }
}
