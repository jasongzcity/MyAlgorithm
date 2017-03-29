package SwapNodesInPairs_24;

/**
 * Given a linked list,
 * swap every two adjacent nodes and return its head.
 *
 * Your algorithm should use only constant space.O(1)
 * You may not modify the values in the list, only nodes itself can be changed.
 */
public class Solution
{
    public static ListNode swapPairs(ListNode head)
    {
        if(head==null||head.next==null) return head;
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;
        ListNode prepre = dummy,pre = head,next = head.next;
        while(true)
        {
            ListNode nextnext = next.next;
            pre.next = nextnext;
            prepre.next = next;
            next.next = pre;

            prepre = pre;
            if(nextnext==null||nextnext.next==null) break;
            pre = nextnext;
            next = nextnext.next;
        }
        return dummy.next;
    }

    static class ListNode
    {
        int val;
        ListNode next;
        ListNode(int x){this.val = x;}
        /* Must assure str contains with number character */
        ListNode(String str)
        {
            //ListNode temp = new ListNode(Integer.parseInt(str.substring(0,1)));
            this.val = Integer.parseInt(str.substring(0,1));
            ListNode temp = this;
            for(int i=1;i<str.length();i++)
            {
                ListNode next = new ListNode(Integer.parseInt(str.substring(i,i+1)));
                temp.next = next;
                temp = temp.next;
            }
        }
    }

    public static void showListNode(ListNode l)
    {
        while(l!=null)
        {
            System.out.print(String.valueOf(l.val)+" ");
            l = l.next;
        }
        System.out.println();
    }
}
