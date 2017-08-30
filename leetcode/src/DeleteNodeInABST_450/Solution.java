package DeleteNodeInABST_450;

import BinaryTree.TreeNode;

/**
 * Given a root node reference of a BST and a key,
 * delete the node with the given key in the BST.
 * Return the root node reference (possibly updated) of the BST.
 *
 * Basically, the deletion can be divided into two stages:
 *
 * Search for a node to remove.
 * If the node is found, delete the node.
 * Note: Time complexity should be O(height of tree).
 *
 * Example:
 *
 * root = [5,3,6,2,4,null,7]
 * key = 3
 *
 *      5
 *     / \
 *    3   6
 *   / \   \
 *  2   4   7
 * Given key to delete is 3. So we find the node with value 3 and delete it.
 * One valid answer is [5,4,6,2,null,null,7], shown in the following BST.
 *     5
 *    / \
 *   4   6
 *  /     \
 * 2       7
 *
 * Another valid answer is [5,2,6,null,4,null,7].
 *     5
 *    / \
 *   2   6
 *   \   \
 *    4   7
 */
public class Solution {
    private TreeNode prev = null;
    private TreeNode cur = null;

    public TreeNode deleteNode2(TreeNode root, int key){
        searchIn(root,key);
        if(cur==null) return root;
        while(cur.left!=cur.right) diveIn();
        if(prev==null) return null;
        if(prev.left==cur) prev.left = null;
        else prev.right = null;
        return root;
    }

    private void searchIn(TreeNode root,int key){
        cur = root;
        while(true){
            if(cur==null||cur.val==key) return;
            prev = cur;
            if(cur.val<key) cur = cur.right;
            else cur = cur.left;
        }
    }

    private void diveIn(){
        TreeNode n = cur; // backup
        prev = cur;
        if(cur.right==null){
            cur = cur.left;
            while(cur.right!=null){
                prev = cur;
                cur = cur.right;
            }
        }else{
            cur = cur.right;
            while(cur.left!=null){
                prev = cur;
                cur = cur.left;
            }
        }
        n.val = cur.val;
    }

    // optimal recursive solution
    public TreeNode deleteNode(TreeNode root, int key){
        if(root==null) return null;
        // delete this node
        if(root.val==key){
            if(root.right==null) return root.left;
            if(root.left==null) return root.right;
            // MOST TRICKY PART HERE!!!
            TreeNode tm = root.left;
            while(tm.right!=null) tm = tm.right;
            tm.right = root.right.left;
            root.right.left = root.left;
            return root.right;
        }else{
            if(root.val>key) root.left = deleteNode(root.left,key);
            else root.right = deleteNode(root.right,key);
            return root;
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        TreeNode t = new TreeNode("42513");
        t = s.deleteNode(t,4);
    }
}
