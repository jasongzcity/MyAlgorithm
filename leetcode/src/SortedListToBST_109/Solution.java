package SortedListToBST_109;

import BinaryTree.TreeNode;
import SinglyList.ListNode;

/**
 * Given a singly linked list where elements are sorted in ascending order,
 * convert it to a height balanced BST.
 */
public class Solution {
    // this would be slow...
    public static TreeNode sortedListToBST(ListNode head) {
        ListNode tm = head;
        int count = 0;
        while(tm!=null) {
            count++;
            tm = tm.next;
        }
        return getSubTree(head,count);
    }

    private static TreeNode getSubTree(ListNode head,int count){
        if (count==0) return null;
        if (count==1) return new TreeNode(head.val);
        int mid = count>>1;
        ListNode tm = head;
        for(int i=0;i<mid;i++) tm = tm.next;
        TreeNode root = new TreeNode(tm.val);
        root.left = getSubTree(head,mid);
        root.right = getSubTree(tm.next,count-mid-1);
        return root;
    }

    public static void main(String[] args) {
        ListNode l = new ListNode("358");
        TreeNode t = sortedListToBST(l);
    }
}
