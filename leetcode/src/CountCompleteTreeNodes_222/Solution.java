package CountCompleteTreeNodes_222;

import BinaryTree.TreeNode;

/**
 * Given a complete binary tree, count the number of nodes.
 *
 * Definition of a complete binary tree from Wikipedia:
 * In a complete binary tree every level, except possibly the last,
 * is completely filled, and all nodes in the last level are as far left as possible.
 * It can have between 1 and 2^h nodes inclusive at the last level h.
 */
public class Solution {
    // brute force using dfs is totally unnecessary
    public int countNodes2(TreeNode root) {
        int level = 0;
        TreeNode n = root;
        while(n!=null){
            n = n.left;
            ++level;
        }
        if(level<2) return level;
        int levelBack = level,lo = 1,hi = (1<<(level-1))+1; // hi exclusive
        n = root;
        // binary search
        while(level!=2){
            TreeNode tm = n.right;
            int mid = (lo+hi)>>1;
            for(int i=0;i<level-2;i++) tm = tm.left;
            if(tm==null){
                n = n.left;
                hi = mid;
            }else{
                n = n.right;
                lo = mid;
            }
            --level;
        }
        if(n.right!=null) return (1<<(levelBack-1))+lo;
        else return (1<<(levelBack-1))-1+lo;
    }

    // another smart solution
    // optimal on leetcode
    public int countNodes(TreeNode root){
        int count = 0,height = getHeight(root);
        while(root!=null){
            int rightHeight = getHeight(root.right);
            count+=1<<rightHeight; // tricky!
            if(rightHeight==height-1) root = root.right;
            else root = root.left;
            --height;
        }
        return count;
    }

    private int getHeight(TreeNode root){
        int height = 0;
        while(root!=null){
            root = root.left;
            ++height;
        }
        return height;
    }
}
