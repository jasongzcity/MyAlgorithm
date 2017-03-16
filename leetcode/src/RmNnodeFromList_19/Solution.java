package RmNnodeFromList_19;

/**
 * Given a linked list, remove the nth node from the end
 * of list and return its head.
 * Given n will always be valid.
 * Try to do this in one pass.
 */
public class Solution
{
    public static ListNode removeNthFromEnd(ListNode head, int n)
    {
        int count = 0; // number of nodes
        ListNode pt = head;
        //ListNode
        while(pt!=null)
        {
            count++;
            pt = pt.next;
        }
        ListNode p=head,pre=head;
        for(int i=1;i<count-n+1;i++)
        {
            pre = p;
            p = p.next;
        }
        //got target now
        if(pre==p) // p is head
            head = p.next;
        else
            pre.next = p.next;
        return head;
    }

    // one pass solution from leetcode discussion.
    public static ListNode removeNthFromEnd2(ListNode head, int n)
    {
        ListNode temp = new ListNode(0);// use sentinel to deal corner cases.
        ListNode slow = temp,fast = temp;
        temp.next = head;
        for(int i=0;i<n+1;i++)
            fast = fast.next;
        // so now the slow node is n+1 behind fast.
        // push fast to the last.
        while(fast!=null)
        {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return temp.next;
    }

    // for tests
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

    public static void main(String[] args)
    {
        ListNode l = new ListNode("12");
        l = removeNthFromEnd(l,2);
    }
}
