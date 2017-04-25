package RmDuplicatesFromSortedListII_82;

/**
 * Given a sorted linked list, delete all nodes that have duplicate numbers,
 * leaving only distinct numbers from the original list.
 *
 * For example,
 * Given 1->2->3->3->4->4->5, return 1->2->5.
 * Given 1->1->1->2->3, return 2->3.
 */
public class Solution {
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy,first = head,last = head;
        int count = 0;
        while(last!=null){
            last = last.next;
            count = 1;
            while(last!=null&&last.val==first.val) {
                ++count;
                last = last.next;
            }

            if(count>1) prev.next = last;
            else prev = first;

            first = last;
        }
        return dummy.next;
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

    public static void main(String[] args) {
        ListNode l = new ListNode("12334455");
        l = deleteDuplicates(l);
        showListNode(l);

        l = new ListNode("11111111");
        l = deleteDuplicates(l);
        showListNode(l);

        l = deleteDuplicates(null);
        showListNode(l);

        l = new ListNode("11123445");
        l = deleteDuplicates(l);
        showListNode(l);
    }
}
