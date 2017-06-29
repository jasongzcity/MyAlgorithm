package BoundaryOfBinTree_545;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a binary tree, return the values of its boundary in
 * anti-clockwise direction starting from root.
 * Boundary includes left boundary, leaves, and right boundary in order
 * without duplicate nodes.
 *
 * Left boundary is defined as the path from root to the left-most node.
 * Right boundary is defined as the path from root to the right-most node.
 * If the root doesn't have left subtree or right subtree,
 * then the root itself is left boundary or right boundary.
 * Note this definition only applies to the input binary tree,
 * and not applies to any subtrees.
 *
 * The left-most node is defined as a leaf node you could reach when
 * you always firstly travel to the left subtree if exists.
 * If not, travel to the right subtree. Repeat until you reach a leaf node.
 *
 * The right-most node is also defined
 * by the same way with left and right exchanged.
 *
 * Example 1
 * Input:
 *        1
 *         \
 *          2
 *         / \
 *        3   4
*
 * Ouput:
 * [1, 3, 4, 2]
*
 * Explanation:
 * The root doesn't have left subtree, so the root itself is left boundary.
 * The leaves are node 3 and 4.
 * The right boundary are node 1,2,4.
 * Note the anti-clockwise direction means you should output reversed right boundary.
 * So order them in anti-clockwise without duplicates and we have [1,3,4,2].
 *
 * Example 2
 * Input:
 *       ____1_____
 *      /          \
 *     2            3
 *    / \          /
 *   4   5        6
 *      / \      / \
 *     7   8    9  10
 *
 * Ouput:
 * [1,2,4,7,8,9,10,6,3]
 *
 * Explanation:
 * The left boundary are node 1,2,4. (4 is the left-most node according to definition)
 * The leaves are node 4,7,8,9,10.
 * The right boundary are node 1,3,6,10. (10 is the right-most node).
 * So order them in anti-clockwise without
 * duplicate nodes we have [1,2,4,7,8,9,10,6,3].
 */
public class Solution {
    private boolean leftmostFound = false;
    private boolean rightmostFound = false;

    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> leftmost = new ArrayList<>(36);
        if(root==null) return leftmost;
        List<Integer> rightmost = new ArrayList<>(36);
        leftmost.add(root.val);
        leftmostFound = false;
        rightmostFound = false;
        left(root.left,leftmost);
        right(root.right,rightmost);
        for(int i=rightmost.size()-1;i>-1;i--) leftmost.add(rightmost.get(i));
        return leftmost;
    }

    private void left(TreeNode root,List<Integer> leftmost){
        if(root==null) return;
        if(!leftmostFound){
            leftmost.add(root.val);
            if(root.left==root.right){
                // root is the leftmost node
                leftmostFound = true;
                return;
            }
        }else if(root.left==root.right){
            // leaves found
            leftmost.add(root.val);
            return;
        }
        left(root.left,leftmost);
        left(root.right,leftmost);
    }

    private void right(TreeNode root,List<Integer> rightmost){
        if(root==null) return;
        if(!rightmostFound){
            rightmost.add(root.val);
            if(root.left==root.right){
                rightmostFound = true;
                return;
            }
        }else if(root.left==root.right){
            rightmost.add(root.val);
            return;
        }
        right(root.right,rightmost);
        right(root.left,rightmost);
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{1,null,2,3,4};
        TreeNode t = new TreeNode(a);
        Solution s = new Solution();
        List<Integer> l = s.boundaryOfBinaryTree(t);
        Integer[] aa = new Integer[]{1,2,3,4,5,6,null,null,null,7,8,9,10};
        t = new TreeNode(aa);
        l = s.boundaryOfBinaryTree(t);
    }
}
