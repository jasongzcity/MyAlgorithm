package LinkedListCycleII_142;

import SinglyList.ListNode;

/**
 * Given a linked list, return the node where the cycle begins.
 * If there is no cycle, return null.
 *
 * Note: Do not modify the linked list.
 *
 * Follow up:
 * Can you solve it without using extra space?
 */
public class Solution {
    // This problem can be easily solved with a set.
    public static ListNode detectCycle(ListNode head) {
        if(head==null||head.next==null) return null;
        ListNode slow = head, fast = head.next;
        int count = 1;
        while(fast!=slow){
            if(fast.next==null||fast.next.next==null) return null;
            fast = fast.next.next;
            slow = slow.next;
            ++count;
        }
        // check from the count-th node the begin of the cycle.

        ListNode begin = slow.next,check = head;
        while(begin!=slow){
            check = head;
            while(check!=slow){
                if(check==begin) return begin;
                check = check.next;
            }
            begin = begin.next;
        }
        return begin;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode("3204");
        ListNode one = head.next;
        ListNode tail = head.next.next.next;
        tail.next = one;
        ListNode cycle = detectCycle(head);
    }

    public static ListNode detectCycle2(ListNode head){
        ListNode slow = head,fast = head;
        while(slow!=null&&fast!=null){
            slow = slow.next;
            fast = fast.next;
            if(fast==null) break;
            fast = fast.next;
            if(fast==slow){
                fast = head;
                while(fast!=slow){
                    fast = fast.next;
                    slow = slow.next;
                }
                return fast;
            }
        }
        return null;
    }

}
