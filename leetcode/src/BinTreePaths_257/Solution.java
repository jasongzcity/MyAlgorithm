package BinTreePaths_257;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a binary tree, return all root-to-leaf paths.
 *
 * For example, given the following binary tree:
 *
 *     1
 *   /   \
 *  2     3
 *   \
 *    5
 * All root-to-leaf paths are:
 *
 * ["1->2->5", "1->3"]
 */
public class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> rs = new ArrayList<>(32);
        StringBuilder sb = new StringBuilder(64);
        dfs(root,rs,sb);
        return rs;
    }

    private void dfs(TreeNode root,List<String> rs,StringBuilder sb){
        if(root==null) return;
        int curlen = sb.length();
        sb.append(root.val);
        if(root.right==root.left){
            // leaf
            rs.add(sb.toString());
        }else{
            sb.append("->");
            dfs(root.left,rs,sb);
            dfs(root.right,rs,sb);
        }
        sb.delete(curlen,sb.length());
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        Integer[] a = new Integer[]{1,2,3,4,null,6,7,null,8,11,12,null,
                13,null,null,null,null,null,null,14};
        TreeNode tree = new TreeNode(a);
        System.out.println(s.binaryTreePaths(tree).toString());
    }
}
