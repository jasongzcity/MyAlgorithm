package PalindromicLinkedList_234;
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution
{
    // do it in O(n) time and O(1) space?
    public static boolean isPalindrome(ListNode head)
    {
        if(head==null||head.next==null) return true;
        ListNode twice = head;
        ListNode once = head;
        while(twice.next!=null&&twice.next.next!=null)
        {
            once = once.next;
            twice = twice.next.next;
        }
        // now once at the midpoint of the list, and reverse the next half
        once.next = reverseSinglyList(once.next);
        once = once.next;
        while(once!=null)
        {
            if(once.val!=head.val) return false;
            head = head.next;
            once = once.next;
        }
        return true;
    }

    /**
     * Reverse the singly list begin with head
     * @param head the header of the list
     * @return the head of the reversed list
     */
    public static ListNode reverseSinglyList(ListNode head)
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
    }

    public static void main(String[] args)
    {
        String s = "123444321";
        ListNode l = new ListNode(s);
        System.out.println("input: "+s+",output:"+isPalindrome(l));
    }
}