package PartitionList_86;

/**
 * Given a linked list and a value x,
 * partition it such that all nodes less than x come
 * before nodes greater than or equal to x.
 *
 * You should preserve the original relative order of the nodes
 * in each of the two partitions.
 *
 * For example,
 * Given 1->4->3->2->5->2 and x = 3,
 * return 1->2->2->4->3->5.
 */
public class Solution {
    public static ListNode partition(ListNode head, int x) {
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;
        ListNode first = dummy,last = dummy; // first is the last node smaller than x
        while(last.next!=null){
            if(last.next.val<x){
                if(last!=first){
                    ListNode next = last.next;
                    last.next = next.next;
                    next.next = first.next;
                    first.next = next;
                }else{
                    last = last.next;
                }
                first = first.next;
            }else{
                last = last.next;
            }
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode l = new ListNode("143252");
        l = partition(l,3);
        showListNode(l);

        l = new ListNode("111112275694653333");
        l = partition(l,4);
        showListNode(l);

        l = new ListNode("8811465756222112122111");
        l = partition(l,3);
        showListNode(l);
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
