package InsertionSortList_147;

import SinglyList.ListNode;

/**
 * Sort a linked list using insertion sort.
 */
public class Solution {
    public ListNode insertionSortList(ListNode head) {
        if(head==null||head.next==null) return head;
        ListNode dummy = new ListNode(0),prev,cur,target = head.next,next;
        dummy.next = head;
        head.next = null;
        while(target!=null){
            next = target.next;
            cur = dummy.next;
            prev = dummy;
            while(cur!=null&&cur.val<=target.val) {
                prev = cur;
                cur = cur.next;
            }
            prev.next = target;
            target.next = cur;
            target = next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        ListNode list = new ListNode("556622112");
        list = s.insertionSortList(list);
    }
}
