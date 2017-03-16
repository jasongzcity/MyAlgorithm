package ReverseSinglyList_206;

/**
 * Created by Administrator on 2017/3/10.
 */
public class Solution
{
    public static ListNode reverseList(ListNode head)
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
        String s = "0123456789";
        System.out.println("input:");
        ListNode l = new ListNode(s);
        showListNode(l);
        System.out.println("output:");
        showListNode(reverseList(l));
    }
}
