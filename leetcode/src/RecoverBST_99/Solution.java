package RecoverBST_99;

import BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Two elements of a binary search tree (BST) are swapped by mistake.
 * Recover the tree without changing its structure.
 * Note:
 * A solution using O(n) space is pretty straight forward.
 * Could you devise a constant space solution?
 */
public class Solution {
    // O(n) space complexity solution
    public static void recoverTree(TreeNode root) {
        List<TreeNode> ordered = new ArrayList<>();
        inorderTrav(root,ordered);

        boolean metflag = false;
        TreeNode fbefore = null,first=null,before=ordered.get(0);
        for(int i=1;i<ordered.size();i++){
            TreeNode current = ordered.get(i);
            if(current.val<before.val){
                if(!metflag){   // first met
                    fbefore = before;
                    first = current;
                    metflag = true;
                }else{          // second time
                    first = current;
                    break;
                }
            }
            before = current;
        }
        int temp = fbefore.val;
        fbefore.val = first.val;
        first.val = temp;
    }

    private static void inorderTrav(TreeNode t,List<TreeNode> list){
        if(t==null) return;

        inorderTrav(t.left,list);
        list.add(t);
        inorderTrav(t.right,list);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.right = new TreeNode(1);
        Solution s = new Solution();
        s.recoverTree2(root);
    }

    private TreeNode first = null;
    private TreeNode second = null;
    private TreeNode prev = new TreeNode(Integer.MIN_VALUE); // suppose negative infinity.

    // O(1) space solution
    public void recoverTree2(TreeNode root){
        inorderTrav2(root);

        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    private void inorderTrav2(TreeNode root){
        if(root==null) return;

        inorderTrav2(root.left);
        if(first==null&&prev.val>=root.val) first = prev;
        if(first!=null&&prev.val>=root.val) second = root;
        prev = root;
        inorderTrav2(root.right);
    }
}
