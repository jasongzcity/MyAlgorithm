package MostFrequentSubtreeSum_508;

import BinaryTree.TreeNode;

import java.util.*;

/**
 * Given the root of a tree,
 * you are asked to find the most frequent subtree sum.
 * The subtree sum of a node is defined as the sum of
 * all the node values formed by the subtree rooted at that node
 * (including the node itself).
 * So what is the most frequent subtree sum value?
 * If there is a tie, return all the values with the highest frequency in any order.
 *
 * Examples 1
 * Input:
 *      5
 *    /  \
 *   2   -3
 * return [2, -3, 4], since all the values happen only once,
 * return all of them in any order.
 *
 * Examples 2
 * Input:
 *     5
 *   /  \
 *  2   -5
 * return [2], since 2 happens twice, however -5 only occur once.
 *
 * Note: You may assume the sum of values
 * in any subtree is in the range of 32-bit signed integer.
 */
public class Solution {
    public int[] findFrequentTreeSum(TreeNode root) {
        List<Integer> l = new ArrayList<>();
        Map<Integer,Integer> map = new HashMap<>();
        walk(root,map);
        for(Map.Entry e:map.entrySet())
            if((Integer)e.getValue()==max)
                l.add((Integer)e.getKey());
        int[] rs = new int[l.size()];
        int p = 0;
        for(Integer i:l) rs[p++] = i;
        return rs;
    }

    private int max = 0;

    private int walk(TreeNode root,Map<Integer,Integer> map){
        if(root==null) return 0;
        int sum = walk(root.left,map)+walk(root.right,map)+root.val;
        Integer count = map.get(sum);
        if(count==null) count = 0;
        map.put(sum,count+1);
        if(count+1>max) max = count+1;
        return sum;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        Integer[] a = {5,2,-5};
        TreeNode t = new TreeNode(a);
        System.out.println(Arrays.toString(s.findFrequentTreeSum(t)));
    }
}
