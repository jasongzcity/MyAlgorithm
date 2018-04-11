package BinTreeVerticalOrderTraversal_314;

import BinaryTree.TreeNode;

import java.util.*;

/**
 * Given a binary tree, return the vertical order traversal of its nodes' values.
 * (ie, from top to bottom, column by column).
 *
 * If two nodes are in the same row and column, the order should be from left to right.
 *
 * Examples:
 *
 * Given binary tree [3,9,20,null,null,15,7],
 *             3
 *           /   \
 *          9    20
 *              /  \
 *            15    7
 * return its vertical order traversal as:
 * [
 * [9],
 * [3,15],
 * [20],
 * [7]
 * ]
 *
 * Given binary tree [3,9,8,4,0,1,7],
 *            3
 *         /   \
 *        9     8
 *      /  \   / \
 *     4   0  1   7
 * return its vertical order traversal as:
 * [
 * [4],
 * [9],
 * [3,0,1],
 * [8],
 * [7]
 * ]
 *
 * Given binary tree [3,9,8,4,0,1,7,null,null,null,2,5]
 * (0's right child is 2 and 1's left child is 5),
 *                 3
 *                /\
 *               /  \
 *              9   8
 *             /\  /\
 *            /  \/  \
 *            4  01   7
 *              / \
 *             /   \
 *            5     2
 * return its vertical order traversal as:
 * [
 * [4],
 * [9,5],
 * [3,0,1],
 * [8,2],
 * [7]
 * ]
 */
public class Solution {

    // Second session
    // thought:
    // one key point is that we should use level traversal,
    // or in the last example, the 2 will be in the column list before 8
    // should we first do a dfs to find out how many columns are there in the tree?
    // nah! You can actually do that during the traversal procedure
    // by maintaining the smallest and highest
    // actually you can also use map to map the column number and the array.
    public List<List<Integer>> verticalOrderII(TreeNode root){
        List<List<Integer>> rs = new LinkedList<>();
        if(root==null) return rs;
        // use these two queue to do level traversal
        Queue<TreeNode> nodes = new LinkedList<>();
        Queue<Integer> cols = new LinkedList<>();
        int collow = 1, colhigh = 0;    // this is actually the tricky part, how to define initial value?
        nodes.add(root);
        cols.add(0);
        while(nodes.size()!=0){
            TreeNode cur = nodes.poll();
            Integer col = cols.poll();
            List<Integer> l;
            if(col<collow){
                l = new ArrayList<>();
                rs.add(0, l);
                collow = col;
            }else if(col>colhigh){
                l = new ArrayList<>();
                rs.add(l);
                colhigh = col;
            }else{
                l = rs.get(col-collow);
            }
            l.add(cur.val);
            if(cur.left!=null){
                nodes.add(cur.left);
                cols.add(col-1);
            }
            if(cur.right!=null){
                nodes.add(cur.right);
                cols.add(col+1);
            }
        }
        return rs;
    }

    // use level traverse
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> rs = new LinkedList<>();
        if(root==null) return rs;
        Queue<TreeNode> q = new LinkedList<>();
        Queue<Integer> columns = new LinkedList<>();
        int clow = 1,chigh = 0;
        columns.add(0);
        q.add(root);
        while(q.size()!=0){
            int size = q.size();
            for(int i=0;i<size;i++){
                int column = columns.poll();
                TreeNode n = q.poll();
                List<Integer> l;
                if(column<clow){
                    l = new ArrayList<>(16);
                    rs.add(0,l);
                    clow = column;
                }else if(column>chigh){
                    l = new ArrayList<>(16);
                    rs.add(l);
                    chigh = column;
                }else{
                    l = rs.get(column-clow);
                }
                l.add(n.val);
                if(n.left!=null){
                    q.add(n.left);
                    columns.add(column-1);
                }
                if(n.right!=null){
                    q.add(n.right);
                    columns.add(column+1);
                }
            }
        }
        return rs;
    }

    public static void main(String[] args) {
        Integer[] a = {3,9,8,4,0,1,7,null,null,null,2,5};
        TreeNode t = new TreeNode(a);
        Solution s = new Solution();
        System.out.println(s.verticalOrder(t).toString());
    }
}
