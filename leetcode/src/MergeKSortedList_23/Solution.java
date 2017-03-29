package MergeKSortedList_23;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Merge k sorted linked lists and return it as one sorted list.
 * Analyze and describe its complexity.
 */
public class Solution
{
    public static ListNode mergeKLists(ListNode[] lists)
    {
        ListNode head = new ListNode(Integer.MIN_VALUE);
        ListNode tail = head;
        PriorityQueue pq = new PriorityQueue(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(((ListNode)o1).val<((ListNode)o2).val)
                    return -1;
                else if(((ListNode)o1).val==((ListNode)o2).val)
                    return 0;
                else
                    return 1;
            }
        });

        for(int i=0;i<lists.length;i++)
            if(lists[i]!=null)
                pq.add(lists[i]);
        while(!pq.isEmpty())
        {
            ListNode s = (ListNode)pq.poll();
            tail.next = s;
            if(s.next!=null)
                pq.add(s.next);
            tail = tail.next;
        }
        return head.next;
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
