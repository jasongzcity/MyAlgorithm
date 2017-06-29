package ConstructBinTreeFromStr_536;

import BinaryTree.TreeNode;

/**
 * You need to construct a binary tree from a string
 * consisting of parenthesis and integers.
 *
 * The whole input represents a binary tree.
 * It contains an integer followed by zero, one or two pairs of parenthesis.
 * The integer represents the root's value and a pair of
 * parenthesis contains a child binary tree with the same structure.
 *
 * You always start to construct the left child node of the parent first if it exists.
 *
 * Example:
 * Input: "4(2(3)(1))(6(5))"
 * Output: return the tree root node representing the following tree:
 *
 *       4
 *     /   \
 *    2     6
 *   / \   /
 *  3   1 5
 * Note:
 * There will only be '(', ')', '-' and '0' ~ '9' in the input string.
 * An empty tree is represented by "" instead of "()".
 */
public class Solution {

    private int begin = 0;
    private int end = -1;

    public TreeNode str2tree(String s) {
        if(s.isEmpty()) return null;
        end=-1;
        return buildTree(s.toCharArray());
    }

    private TreeNode buildTree(char[] s){
        begin = ++end;
        if(s[begin]==')') return null; // empty left child

        // search for the end of number
        TreeNode root;
        if(s[begin]=='-'){
            end = ++begin;
            root = new TreeNode(-parseint(s));
        }
        else root = new TreeNode(parseint(s));

        if(end==s.length||s[end]==')') return root; // no child

        root.left = buildTree(s);
        ++end;
        if(end<s.length&&s[end]=='('){
            // right child exists
            root.right = buildTree(s);
            ++end;
        }
        return root;
    }

    // I just check leetcode I think my solution is optimal,
    // but to be more efficient(perform better on OJ),
    // I should try to parse integer myself.
    private int parseint(char[] s){
        int rs = 0;
        while(end<s.length&&s[end]>='0'){
            rs*=10;
            rs+=s[end++]-'0';
        }
        return rs;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        TreeNode t = s.str2tree("14(12(13)(11))(16(15))");
        t = s.str2tree("14(12(13))(16(15)(17))");
        t = s.str2tree("14(12(13))(-15)");
    }
}
