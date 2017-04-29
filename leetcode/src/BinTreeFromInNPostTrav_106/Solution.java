package BinTreeFromInNPostTrav_106;

import BinaryTree.TreeNode;

import java.util.Stack;

/**
 * Given inorder and postorder traversal of a tree, construct the binary tree.
 *
 * Note:
 * You may assume that duplicates do not exist in the tree.
 */
public class Solution {
    public static TreeNode buildTree2(int[] inorder, int[] postorder) {
        return buildSubTree(postorder,0,postorder.length,inorder,0,inorder.length);
    }

    private static TreeNode buildSubTree(int[] post,int pb,int pe,
                                         int[] in,int ib,int ie){
        if(pb==pe) return null;
        if(pb+1==pe) return new TreeNode(post[pb]);

        int target = ib;
        while(in[target]!=post[pe-1]) target++;

        int rightnum = ie-target-1;
        TreeNode root = new TreeNode(post[pe-1]);
        root.right = buildSubTree(post,pe-1-rightnum,pe-1,in,target+1,ie);
        root.left = buildSubTree(post,pb,pe-1-rightnum,in,ib,target);
        return root;
    }

    // same idea as in #105
    public static TreeNode buildTree(int[] in,int[] post){
        if(in.length==0) return null;
        int pp = post.length-1, ip = in.length-1;
        Stack<TreeNode> s = new Stack<>();
        TreeNode root = new TreeNode(post[pp--]),cur = root;
        while(pp>-1){
            if(cur.val!=in[ip]){
                cur.right = new TreeNode(post[pp--]);
                s.push(cur);
                cur = cur.right;
            }else{
                --ip;
                while(!s.empty()&&s.peek().val==in[ip]){
                    cur = s.pop();
                    --ip;
                }
                cur = cur.left = new TreeNode(post[pp--]);
            }
        }
        return root;
    }
}
