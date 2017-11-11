package RotateList_61;

/**
 * Given a list, rotate the list to the right by k places, where k is non-negative.
 *
 * For example:
 * Given 1->2->3->4->5->NULL and k = 2,
 * return 4->5->1->2->3->NULL.
 */

import SinglyList.ListNode;

public class Solution {

    private ListNode tail = null;

    // Second session
    public ListNode rotateRightII(ListNode head, int k){
        // first we assume 0<=k
        // if k larger than the length of list,
        // we rotate the number of k%length
        int len = countAndTail(head);
        if(len==0) return head;
        k%=len;
        if(k==0) return head;
        ListNode dummy = new ListNode(-1), p = dummy;
        dummy.next = head;
        int counter = len-k;
        while(counter>0){
            p = p.next;
            --counter;
        }
        // reconnect
        dummy.next = p.next;
        p.next = null;
        tail.next = head;
        return dummy.next;
    }

    private int countAndTail(ListNode head){
        ListNode cur = head;
        int count = 0;
        while(cur!=null){
            tail = cur;
            cur = cur.next;
            ++count;
        }
        return count;
    }

    // better solution
    // make it a ring, and count and detach.
    public ListNode rotateRightII2(ListNode head, int k){
        if(head==null) return head;
        ListNode tm = head;
        int count = 1;
        while(tm.next!=null){
            tm = tm.next;
            ++count;
        }
        tm.next = head;
        for(int i=0;i<(count-k%count);i++) tm = tm.next;

        head = tm.next;
        tm.next = null;
        return head;
    }

    public static ListNode rotateRight(ListNode head, int k) {
        if(head==null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode tail = head;
        int count = 1;
        while(tail.next!=null) {
            tail = tail.next;
            count++;
        }
        ListNode pivot = head;
        int diff = k%count; // the difference from pivot to the tail
        if(diff==0) return head;
        for (int i = 0; i < count-diff-1; i++) pivot = pivot.next;
        ListNode newHead = pivot.next;
        dummy.next = newHead;
        tail.next = head;
        pivot.next = null;
        return newHead;
    }

    // more subtle solution
    public static ListNode rotateRight2(ListNode head, int k){
        if(head==null) return head;
        ListNode tail = head;
        int count = 1;
        while(tail.next!=null) {
            tail = tail.next;
            count++;
        }
        tail.next = head;   // make the list a ring
        int step = count - k%count;
        for (int i = 0; i < step; i++) tail = tail.next;
        ListNode newHead = tail.next;
        tail.next = null;
        return newHead;
    }
}
