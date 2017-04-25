package ReverseLinkedListII_92;

import SinglyList.ListNode;

/**
 * Reverse a linked list from position m to n. Do it in-place and in one-pass.
 *
 * For example:
 * Given 1->2->3->4->5->NULL, m = 2 and n = 4,
 *
 * return 1->4->3->2->5->NULL.
 *
 * Note:
 * Given m, n satisfy the following condition:
 * 1 ≤ m ≤ n ≤ length of list.
 */
public class Solution {
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pp = dummy;
        for(int i=1;i<m;i++) pp = pp.next; // node position before the position m
        ListNode current = pp.next,next = current,pre = null;
        for(int i=m;i<=n;i++){
            next = current.next;
            current.next = pre;
            pre = current;
            current = next;
        }
        pp.next.next = current;
        pp.next = pre;
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode l = new ListNode(3);
        l = reverseBetween(l,1,1);
        l.displayList();
        l = new ListNode("367890214");
        l = reverseBetween(l,3,8);
        l.displayList();
    }
}
