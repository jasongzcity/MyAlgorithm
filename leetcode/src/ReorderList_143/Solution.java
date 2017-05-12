package ReorderList_143;

import SinglyList.ListNode;

/**
 * Given a singly linked list L: L0→L1→…→Ln-1→Ln,
 * reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
 *
 * You must do this in-place without altering the nodes' values.
 *
 * For example,
 * Given {1,2,3,4}, reorder it to {1,4,2,3}.
 */
public class Solution {
    public static void reorderList(ListNode head) {
        if(head==null) return;
        ListNode slow = head,fast = head,p = slow;
        int count = -1;
        while(fast!=null){
            p = slow;
            slow = slow.next;
            fast = fast.next;
            if(fast==null) break;
            fast = fast.next;
        }
        p.next = null;
        // reverse half
        ListNode cur = slow,prev = null,next;
        while(cur!=null){
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        // now prev is the head of the reverse
        slow = head;
        fast = prev;
        ListNode next2;
        // reset slow and fast to the head of two list.
        while(fast!=null){
            next = slow.next;
            next2 = fast.next;
            slow.next = fast;
            fast.next = next;
            slow = next;
            fast = next2;
        }
    }

    public static void main(String[] args) {
        ListNode list = new ListNode("1234");
        reorderList(list);
    }
}
