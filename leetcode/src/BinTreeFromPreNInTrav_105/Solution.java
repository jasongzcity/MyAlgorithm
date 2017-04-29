package BinTreeFromPreNInTrav_105;

import BinaryTree.TreeNode;

import java.util.Stack;

/**
 * Given preorder and inorder traversal of a tree, construct the binary tree.
 *
 * Note:
 * You may assume that duplicates do not exist in the tree.
 */
public class Solution {
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildSubtreeByRange(preorder,0,preorder.length,inorder,0,inorder.length);
    }

    // Notice: pe(pre end) and ie(in end) are exclusive
    private static TreeNode buildSubtreeByRange(int[] pre,int pb,int pe,
                                                int[] in,int ib,int ie){
        if(pb==pe) return null;
        if(pb+1==pe) return new TreeNode(pre[pb]);
        int target = ib,targetval = pre[pb];
        while(in[target]!=targetval) ++target; // waste most of the time here.

        // now target the index of the root element in the inorder array
        TreeNode root = new TreeNode(pre[pb]);
        int leftnums = target-ib;
        root.left = buildSubtreeByRange(pre,pb+1,pb+1+leftnums,in,ib,target);
        root.right = buildSubtreeByRange(pre,pb+1+leftnums,pe,in,target+1,ie);
        return root;
    }

    public static void main(String[] args) {
        int[] pre = new int[]{1,2,5,8,3,6,10,11,7,12};
        int[] in = new int[]{2,8,5,1,10,6,11,3,7,12};
        TreeNode root = buildTree(pre,in);
        TreeNode root2 = buildTree2(pre,in);
    }

    // most voted iterative solution on leetcode
    public static TreeNode buildTree2(int[] pre,int[] in){
        if(pre.length==0) return null;
        int pp = 1,ip = 0;
        Stack<TreeNode> s = new Stack<>();
        TreeNode t = new TreeNode(pre[0]),root = t;
        while(pp<pre.length){
            if(t.val!=in[ip]){
                t.left = new TreeNode(pre[pp++]);
                s.push(t);
                t = t.left;
            }else{
                ++ip;
                while(!s.empty()&&s.peek().val==in[ip]){
                    t = s.pop(); // no right children, keep finding
                    ++ip;
                }
                t = t.right = new TreeNode(pre[pp++]);
            }
        }
        return root;
    }
}
