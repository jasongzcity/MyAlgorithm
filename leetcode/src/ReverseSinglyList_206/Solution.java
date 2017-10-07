package ReverseSinglyList_206;

import SinglyList.ListNode;

/**
 * Created by Administrator on 2017/3/10.
 */
public class Solution
{
    public ListNode reverseList(ListNode head)
    {
        ListNode next = null;
        ListNode current = head;
        ListNode pre = null;
        while(current!=null)
        {
            next = current.next;
            current.next = pre;
            pre = current;
            current = next;
        }
        return pre;
    }

    // Second session!
    // recursive version
    public ListNode reverseList2(ListNode head){
        return head==null?null:helper(null, head);
    }

    public ListNode helper(ListNode prev, ListNode cur){
        ListNode r;
        if(cur.next!=null) r = helper(cur, cur.next);
        else r = cur;
        cur.next = prev;
        return r;
    }

    // iterative solution
    public ListNode reverseList3(ListNode head){
        ListNode cur = head, prev = null;
        while(cur!=null){
            ListNode backup = cur.next;
            cur.next = prev;
            prev = cur;
            cur = backup;
        }
        return prev;
    }
}
