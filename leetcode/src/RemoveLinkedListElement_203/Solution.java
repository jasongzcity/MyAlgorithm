package RemoveLinkedListElement_203;

import SinglyList.ListNode;

/**
 * Remove all elements from a linked list of integers that have value val.
 *
 * Example
 * Given: 1 --> 2 --> 6 --> 3 --> 4 --> 5 --> 6, val = 6
 * Return: 1 --> 2 --> 3 --> 4 --> 5
 */
public class Solution {
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(-1),prev = dummy,cur = head;
        dummy.next = head;
        while(cur!=null){
            if(cur.val==val){
                cur = cur.next;
                prev.next = cur;
            }else{
                prev = cur;
                cur = cur.next;
            }
        }
        return dummy.next;
    }
}
