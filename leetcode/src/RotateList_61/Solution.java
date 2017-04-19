package RotateList_61;

/**
 * Given a list, rotate the list to the right by k places, where k is non-negative.
 *
 * For example:
 * Given 1->2->3->4->5->NULL and k = 2,
 * return 4->5->1->2->3->NULL.
 */
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
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

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x){this.val = x;}
        /* Must assure str contains with number character */
        ListNode(String str) {
            //ListNode temp = new ListNode(Integer.parseInt(str.substring(0,1)));
            this.val = Integer.parseInt(str.substring(0,1));
            ListNode temp = this;
            for(int i=1;i<str.length();i++) {
                ListNode next = new ListNode(Integer.parseInt(str.substring(i,i+1)));
                temp.next = next;
                temp = temp.next;
            }
        }
    }

    public static void showListNode(ListNode l){
        while(l!=null){
            System.out.print(String.valueOf(l.val)+" ");
            l = l.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ListNode l = new ListNode("12");
        showListNode(rotateRight2(l,1));
    }
}
