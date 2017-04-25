package RmDupsInSortedList_83;

/**
 * Given a sorted linked list, delete all duplicates such that
 * each element appear only once.
 *
 * For example,
 * Given 1->1->2, return 1->2.
 * Given 1->1->2->3->3, return 1->2->3.
 */
public class Solution {
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode first = head,last = head;
        while(first!=null){
            last = first.next;
            while(last!=null&&last.val==first.val) last = last.next;
            first.next = last;
            first = last;
        }
        return head;
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

    public static void showListNode(ListNode l) {
        while(l!=null) {
            System.out.print(String.valueOf(l.val)+" ");
            l = l.next;
        }
        System.out.println();
    }
}
