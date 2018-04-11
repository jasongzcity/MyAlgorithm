package SubtreeOfAnotherTree_572;

import BinaryTree.TreeNode;

/*
 * Given two non-empty binary trees s and t,
 * check whether tree t has exactly the same structure and node values with a subtree of s.
 * A subtree of s is a tree consists of a node in s and all of this node's descendants.
 * The tree s could also be considered as a subtree of itself.
 *
 * Example 1:
 * Given tree s:
 *
 *      3
 *     / \
 *    4   5
 *   / \
 *  1   2
 * Given tree t:
 *    4
 *   / \
 *  1   2
 * Return true, because t has the same structure and node values with a subtree of s.
 *
 * Example 2:
 * Given tree s:
 *
 *      3
 *     / \
 *    4   5
 *   / \
 *  1   2
 *     /
 *    0
 *
 * Given tree t:
 *    4
 *   / \
 *  1   2
 * Return false.
 */
public class Solution {
    public boolean isSubtree2(TreeNode s, TreeNode t){
        boolean flag = isSame(s, t);
        if(s!=null) flag |= isSubtree2(s.left, t)||isSubtree2(s.right, t);
        return flag;
    }

    private boolean isSame(TreeNode s, TreeNode t){
        if(s==null&&t==null) return true;
        if(s==null^t==null) return false;
        return s.val==t.val&&isSame(s.left, t.left)&&isSame(s.right, t.right);
    }

    // Optimal
    public boolean isSubtree(TreeNode s, TreeNode t) {
        return s==null ? t==null: t != null
                && (s.val == t.val && helper(s.left, t.left) &&
                helper(s.right, t.right) || (isSubtree(s.left, t) || isSubtree(s.right, t)));
    }
    public boolean helper(TreeNode s, TreeNode t) {
        return s == null?t==null:t!= null&&s.val==t.val&&isSubtree(s.left, t.left)&&isSubtree(s.right, t.right);
    }

//    public static void main(String[] args) {
//        Solution s = new Solution();
//    }
}
