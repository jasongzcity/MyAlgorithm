package FindLeavesOfBinTree_366;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a binary tree, collect a tree's nodes as if you were doing this:
 * Collect and remove all leaves, repeat until the tree is empty.
 *
 * Example:
 * Given binary tree
 *      1
 *     / \
 *    2   3
 *   / \
 *  4   5
 * Returns [4, 5, 3], [2], [1].
 *
 * Explanation:
 * 1. Removing the leaves [4, 5, 3] would result in this tree:
 *    1
 *  /
 * 2
 *
 * 2. Now removing the leaf [2] would result in this tree:
 * 1
 *
 * 3. Now removing the leaf [1] would result in the empty tree:
 * []
 * Returns [4, 5, 3], [2], [1].
 */
public class Solution {
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> rs = new ArrayList<>();
        dfs(root,rs);
        return rs;
    }

    private int dfs(TreeNode root,List<List<Integer>> rs){
        if(root==null) return 0;
        int left = dfs(root.left,rs),right = dfs(root.right,rs);
        int max = Math.max(left,right);
        if(max==rs.size()) rs.add(new ArrayList<>());
        rs.get(max).add(root.val);
        return max+1;
    }
}
