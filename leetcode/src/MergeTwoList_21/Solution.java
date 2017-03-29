package MergeTwoList_21;

/**
 * Merge two sorted linked lists and return it as a new list.
 * The new list should be made by splicing together the nodes of
 * the first two lists.
 */
public class Solution
{
    public ListNode mergeTwoLists(ListNode l1, ListNode l2)
    {
        ListNode p1 = l1,p2 = l2;
        ListNode start = new ListNode(0);
        ListNode head = start;
        while(p1!=null&&p2!=null)
        {
            if(p1.val<p2.val)
            {
                head.next = p1;
                p1 = p1.next;
            }
            else
            {
                head.next = p2;
                p2 = p2.next;
            }
            head = head.next;
        }
        head.next = p1==null?p2:p1;
        return start.next;
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
}
