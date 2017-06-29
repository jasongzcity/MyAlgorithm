package ConstructStrFromBinTree_609;

import BinaryTree.TreeNode;

/**
 * You need to construct a string consists of parenthesis and
 * integers from a binary tree with the preorder traversing way.
 *
 * The null node needs to be represented by empty parenthesis pair "()".
 * And you need to omit all the empty parenthesis pairs that
 * don't affect the one-to-one mapping relationship between
 * the string and the original binary tree.
 *
 * Example 1:
 * Input: Binary tree: [1,2,3,4]
 *      1
 *    /   \
 *   2     3
 *  /
 * 4
 *
 * Output: "1(2(4))(3)"
 *
 * Explanation: Originallay it needs to be "1(2(4)())(3()())",
 * but you need to omit all the unnecessary empty parenthesis pairs.
 * And it will be "1(2(4))(3)".
 *
 * Example 2:
 * Input: Binary tree: [1,2,3,null,4]
 *       1
 *     /   \
 *    2     3
 *     \
 *      4
 *
 * Output: "1(2()(4))(3)"
 *
 * Explanation: Almost the same as the first example,
 * except we can't omit the first parenthesis pair to
 * break the one-to-one mapping relationship between the input and the output.
 */
public class Solution {
    public String tree2str(TreeNode t) {
        StringBuilder sb = new StringBuilder(128);
        buildStr(t,sb,true);
        sb.deleteCharAt(sb.length()-1).deleteCharAt(0);
        return sb.toString();
    }

    private void buildStr(TreeNode root,StringBuilder sb,boolean isLeft){
        if(root!=null){
            sb.append('(').append(root.val);
            if(root.left!=root.right){
                buildStr(root.left,sb,true);
                buildStr(root.right,sb,false);
            }
            sb.append(')');
        }else if(isLeft){
            sb.append("()");
        }
    }

    public static void main(String[] args) {
        TreeNode tree = new TreeNode("1234");
        Solution s = new Solution();
        System.out.println(s.tree2str(tree));
        Integer[] i = new Integer[]{1,2,3,null,4};
        tree = new TreeNode(i);
        System.out.println(s.tree2str(tree));
    }
}
