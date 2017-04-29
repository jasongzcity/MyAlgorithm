package MinDepthOfBinTree_111;

import BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Given a binary tree, find its minimum depth.
 *
 * The minimum depth is the number of nodes along the shortest path from the root node
 * down to the nearest leaf node.
 */
public class Solution {
    // Should use level traverse to get the minimum depth faster.
    // Notice: this solution is not accepted, consider tree
    // [1,2,3,4,null,null,5]
    // this solution will return 2 but the OJ thinks it should return 3.
//    public static int minDepth(TreeNode root) {
//        if(root==null) return 0;
//        int level = 1;
//        Queue<TreeNode> q = new ArrayDeque<>();
//        if(root.left!=null) q.add(root.left);
//        if(root.right!=null) q.add(root.right);
//        while(!q.isEmpty()){
//            ++level;
//            int levelSize = q.size();
//            while(levelSize-->0){
//                TreeNode t = q.poll();
//                if(t.left!=null) q.add(t.left);
//                else return level;
//                if(t.right!=null) q.add(t.right);
//                else return level;
//            }
//        }
//        return level;
//    }

    // But we could use the same idea.
    public static int minDepth(TreeNode root) {
        if(root==null) return 0;
        int level = 0;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        while(!q.isEmpty()){
            ++level;
            int levelSize = q.size();
            while(levelSize-->0){
                TreeNode t = q.poll();
                if(t.left==t.right) return level; // both null, the leaf node got!
                if(t.left!=null) q.add(t.left);
                if(t.right!=null) q.add(t.right);
            }
        }
        return level;
    }

    // Straight forward recursive solution
    // one of the tricky part is to avoid
    // deal with null child, because its not
    // useful.
    // Its a DFS. Because we are looking for the leaves.
    public static int minDepth2(TreeNode root){
        if(root==null) return 0;
        return getDepth(root,0);
    }

    private static int getDepth(TreeNode root,int level){
        if(root.left==root.right) return ++level;
        int rs = 0;
        if(root.left!=null&&root.right!=null){
            rs = Math.min(getDepth(root.left,level+1),
                    getDepth(root.right,level+1));
        }else if(root.left!=null){
            rs = getDepth(root.left,level+1);
        }else{
            rs = getDepth(root.right,level+1);
        }
        return rs;
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{2,1};
        TreeNode t = new TreeNode(a);
        System.out.println(minDepth2(t));
    }
}
