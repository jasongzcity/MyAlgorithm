package LinkedListCycle_141;

import SinglyList.ListNode;

/**
 * Given a linked list, determine if it has a cycle in it.
 *
 * Follow up:
 * Can you solve it without using extra space?
 */
public class Solution {
    // this problem can be easily solved with map
    // but here I reverse the list and check if the
    // next will point to the head.
    public static boolean hasCycle(ListNode head) {
        ListNode cur = head,next,prev = null;
        while(cur!=null){
            next = cur.next;
            if(next==head) return true;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return false;
    }
}
