package FindModeInBST_501;

import BinaryTree.TreeNode;

import java.util.*;

/**
 * Given a binary search tree (BST) with duplicates,
 * find all the mode(s) (the most frequently occurred element) in the given BST.
 *
 * Assume a BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys
 * less than or equal to the node's key.
 * The right subtree of a node contains only nodes with keys
 * greater than or equal to the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 * For example:
 * Given BST [1,null,2,2],
 * return [2]
 *
 * Note: If a tree has more than one mode, you can return them in any order.
 *
 * Follow up: Could you do that without using any extra space?
 * (Assume that the implicit stack space incurred due to recursion does not count).
 */
public class Solution{
    // using map, O(n) space complexity
    public static int[] findMode2(TreeNode root) {
        Map<Integer,Integer> map = new HashMap<>();
        findModeInTree(root,map);
        List<Integer> list = new ArrayList<>();
        int maxtimes = -1;
        for(int i:map.values())
            if(i>maxtimes)
                maxtimes = i;
        for(Map.Entry e:map.entrySet()){
            if((Integer)e.getValue()==maxtimes){
                list.add((Integer)e.getKey());
            }
        }
        int[] a = new int[list.size()];
        for(int i=0;i<list.size();i++) a[i] = list.get(i);
        return a;
    }

    private static void findModeInTree(TreeNode root,Map<Integer,Integer> map){
        if(root==null) return;
        Integer times = map.getOrDefault(root.val,0);
        map.put(root.val,++times);
        findModeInTree(root.left,map);
        findModeInTree(root.right,map);
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{
                1,1,2
        };
        TreeNode root = new TreeNode(a);
        Solution s = new Solution();
        int[] rs = s.findMode(root);
    }

    int maxtimes = 0;
    int currenttimes = -1;
    int prev = Integer.MIN_VALUE;

    // traverse BST with inorder traverse
    public int[] findMode(TreeNode root){
        if(root==null) return new int[0];
        List<Integer> rs = new ArrayList<>();
        inoreder(root,rs);
        int[] a;
        if(currenttimes>maxtimes){
            a = new int[1];
            a[0] = prev;
        }else{
            int j=0;
            if(currenttimes==maxtimes){
                a = new int[rs.size()+1];
                a[j++] = prev;
            }else {
                a = new int[rs.size()];
            }
            for(Integer i:rs) a[j++] = i;
        }
        return a;
    }

    private void inoreder(TreeNode root,List<Integer> rs){
        if(root==null) return;
        inoreder(root.left,rs);
        if(root.val!=prev){
            if(currenttimes>maxtimes){
                rs.clear();
                rs.add(prev);
                maxtimes = currenttimes;
            }else if(currenttimes==maxtimes){
                rs.add(prev);
            }
            currenttimes = 1;
            prev = root.val;
        }else{
            currenttimes++;
        }
        inoreder(root.right,rs);
    }
}
