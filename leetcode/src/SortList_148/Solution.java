package SortList_148;

import SinglyList.ListNode;

/**
 * Sort a linked list in O(n log n) time using constant space complexity.
 */
public class Solution {
    // just implement merge sort version of singly list.
    public ListNode sortList(ListNode head) {
        ListNode temp = head;
        int count = 0;
        while(temp!=null){
            ++count;
            temp = temp.next;
        }
        return mergeSort(head,count);
    }

    private ListNode mergeSort(ListNode head,int size){
        if(size<2) return head;
        int half = size>>1;
        ListNode newHead = head;
        for(int i=0;i<half;i++) newHead = newHead.next;
        ListNode first = mergeSort(head,half),second = mergeSort(newHead,size-half);
        return merge(first,half,second,size-half);
    }

    // merge the second list into the first list.
    private ListNode merge(ListNode first,int firstSize,ListNode second,int secondSize){
        ListNode next = null,dummy = new ListNode(0),prev = dummy;
        dummy.next = first;
        while(firstSize>0&&secondSize>0){
            if(second.val<first.val){
                --secondSize;
                next = second.next;
                prev.next = second;
                second.next = first;
                prev = second;
                second = next;
            }else{
                --firstSize;
                prev = first;
                first = first.next;
            }
        }
        if(secondSize>0) prev.next = second;
        else{
            while(firstSize-->0) prev = prev.next;
            prev.next = next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        ListNode list = new ListNode("45328987546321");
        list = s.sortList(list);
        list = s.sortList(null);
        list = s.sortList(new ListNode(3));
    }
}
