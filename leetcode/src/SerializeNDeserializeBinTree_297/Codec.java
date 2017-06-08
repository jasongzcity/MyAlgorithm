package SerializeNDeserializeBinTree_297;

import BinaryTree.TreeNode;

import java.util.*;

/**
 * Serialization is the process of converting a data structure or
 * object into a sequence of bits so that it can be stored in a file or memory buffer,
 * or transmitted across a network connection link to be reconstructed later
 * in the same or another computer environment.
 *
 * Design an algorithm to serialize and deserialize a binary tree.
 * There is no restriction on how your
 * serialization/deserialization algorithm should work.
 * You just need to ensure that a binary tree can be serialized to
 * a string and this string can be deserialized to the original tree structure.
 *
 * For example, you may serialize the following tree
 *        1
 *       / \
 *      2   3
 *     / \
 *    4   5
 *
 * as "[1,2,3,null,null,4,5]",
 * just the same as how LeetCode OJ serializes a binary tree.
 * You do not necessarily need to follow this format,
 * so please be creative and come up with different approaches yourself.
 *
 * Note: Do not use class member/global/static variables to store states.
 * Your serialize and deserialize algorithms should be stateless.
 */
public class Codec {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if(root==null) return "";
        // use a queue to do level traverse
        Queue<TreeNode> q = new ArrayDeque<>(36);
        StringBuilder sb = new StringBuilder(36);
        sb.append(root.val).append('|');
        q.add(root);
        while(!q.isEmpty()){
            int nums = q.size();
            for(int i=0;i<nums;i++){
                TreeNode pop = q.poll();
                if(pop.left!=null) {
                    sb.append(pop.left.val);
                    q.add(pop.left);
                }else sb.append('#');
                sb.append('|');
                if(pop.right!=null) {
                    sb.append(pop.right.val);
                    q.add(pop.right);
                }else sb.append('#');
                sb.append('|');
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        // notice the data string must end with a '|' so we don't
        // constantly check out of bound
        int len = data.length();
        if(len==0) return null;
        Queue<TreeNode> q = new ArrayDeque<>(36);
        int end = 0,begin = 0;
        while(data.charAt(end)!='|') end++;
        TreeNode root = new TreeNode(Integer.parseInt(data.substring(begin,end)));
        begin = end+1;
        end+=2;
        if(begin==len) return root;
        q.add(root);
        // we can ensure that until q is empty, the ptr will not out of bound
        while(!q.isEmpty()){
            int size = q.size();
            for(int i=0;i<size;i++){
                TreeNode n = q.poll();
                if(data.charAt(begin)=='#') n.left = null;
                else {
                    while (data.charAt(end) != '|') ++end;
                    n.left = new TreeNode(Integer.parseInt(data.substring(begin,end)));
                    q.add(n.left);
                }
                begin = end+1;
                end+=2;
                if(data.charAt(begin)=='#') n.right = null;
                else {
                    while (data.charAt(end) != '|') ++end;
                    n.right = new TreeNode(Integer.parseInt(data.substring(begin,end)));
                    q.add(n.right);
                }
                begin = end+1;
                end+=2;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{1,2,3,null,4,null,null,5,6};
        TreeNode t = new TreeNode(a);
        Codec c = new Codec();
        String data = c.serialize2(t);
        TreeNode rs = c.deserialize2(data);
    }

    private static final char SPLIT = '|';
    private static final char NULL = '#';

    // preorder recursive solution
    // This solution is super fast.
    // But using a class member "begin" maybe violating
    // the question's restriction.
    // So we may use a StringTokenizer instead.
    public String serialize2(TreeNode root){
        StringBuilder sb = new StringBuilder(32);
        buildString(root,sb);
        return sb.toString();
    }

    private void buildString(TreeNode root,StringBuilder sb){
        if(root==null){
            sb.append(NULL).append(SPLIT);
            return;
        }
        sb.append(root.val).append(SPLIT);
        buildString(root.left,sb);
        buildString(root.right,sb);
    }

    public TreeNode deserialize2(String s){
        begin = 0;
        return buildTree(s);
    }

    private int begin = 0;

    private TreeNode buildTree(String s){
        if(s.charAt(begin)==NULL){
            begin+=2;
            return null;
        }
        int end = s.indexOf(SPLIT,begin);
        TreeNode root = new TreeNode(Integer.parseInt(s.substring(begin,end)));
        begin = end+1;
        root.left = buildTree(s);
        root.right = buildTree(s);
        return root;
    }
}
