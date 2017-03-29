package ReverseListKGroup_25;

/**
 * Given a linked list, reverse the nodes of a linked list
 * k at a time and return its modified list.
 *
 * k is a positive integer and is less than or equal to
 * the length of the linked list. If the number of
 * nodes is not a multiple of k then left-out nodes in the
 * end should remain as it is.
 */
public class Solution
{
    public static ListNode reverseKGroup(ListNode head, int k)
    {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode p = dummy;
        p.next = reverseK(p,k);
        return dummy.next;
    }

    public static ListNode reverseK(ListNode p,int k)
    {
        ListNode pre = p,pnext = p.next,pl = pnext;
        if(pnext==null) return null; // base
        for(int i=0;i<k;i++)
        {
            pl = pnext;
            if(pl==null)    // not enough k node,pre is the last not null node.
            {               // and we have reversed i nodes.
                pl = pre;
                pre = null;
                while(pnext!=p)
                {
                    pnext = pl.next;
                    pl.next = pre;
                    pre = pl;
                    pl = pnext;
                }
                return pre;
            }
            pnext = pl.next;
            pl.next = pre;
            pre = pl;
        }
        p.next.next = pnext;
        p.next.next = reverseK(p.next,k);
        return pl;
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