package SerializeAndDeserializeBST_449;

import BinaryTree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Serialization is the process of converting a data structure or object into
 * a sequence of bits so that it can be stored in a file or memory buffer,
 * or transmitted across a network connection link to be
 * reconstructed later in the same or another computer environment.
 *
 * Design an algorithm to serialize and deserialize a binary search tree.
 * There is no restriction on how your
 * serialization/deserialization algorithm should work.
 * You just need to ensure that a binary search tree can be
 * serialized to a string and this string can be deserialized to the
 * original tree structure.
 *
 * The encoded string should be as compact as possible.
 *
 * Note: Do not use class member/global/static variables to store states.
 * Your serialize and deserialize algorithms should be stateless.
 */
public class Codec {
    // The solution beats 98%
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder(32);
        buildString(root,sb);
        return sb.toString();
    }

    private void buildString(TreeNode root,StringBuilder sb){
        if(root==null) return;
        sb.append(root.val).append('|');
        buildString(root.left,sb);
        buildString(root.right,sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data.isEmpty()) return null;
        Queue<Integer> q = new LinkedList<>();
        int begin = 0,end = data.indexOf('|');
        while(true){
            q.add(Integer.parseInt(data.substring(begin,end)));
            begin = end+1;
            if(begin==data.length()) break;
            end = data.indexOf('|',begin);
        }
        return buildTree(q,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }

    private TreeNode buildTree(Queue<Integer> q,int lowBound,int highBound){
        if(q.isEmpty()) return null;
        int num = q.peek();
        if(num<lowBound||num>highBound) return null;
        TreeNode root = new TreeNode(q.poll());
        root.left = buildTree(q,lowBound,num);
        root.right = buildTree(q,num,highBound);
        return root;
    }

    public static void main(String[] args) {
        Codec c = new Codec();
        TreeNode root = new TreeNode("5472368");
        String data = c.serialize(root);
        TreeNode root2 = c.deserialize(data);
    }
}
