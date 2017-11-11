package MergeKSortedList_23;

import SinglyList.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Merge k sorted linked lists and return it as one sorted list.
 * Analyze and describe its complexity.
 */
public class Solution
{
    // Second session
    // using heap to solve this problem would be trivial.
    // that would be a O(Nlogk) algorithm
    // divide and conquer
    public static ListNode mergeKListsII(ListNode[] lists){
        if(lists.length==0) return null;
        divideAndMerge(lists, 0, lists.length-1);
        return lists[0];
    }

    public static void divideAndMerge(ListNode[] l, int lo, int hi){
        if(lo==hi) return;
        int mid = (lo+hi)/2;
        divideAndMerge(l, lo, mid);
        divideAndMerge(l, mid+1, hi);
        merge(l, lo, mid+1);
    }

    public static void merge(ListNode[] l, int left, int right){
        ListNode dummy = new ListNode(-1), a = l[left], b = l[right], back = dummy;
        while(a!=null||b!=null){
            if(b==null||(a!=null&&a.val<=b.val)){
                dummy = dummy.next = a;
                a = a.next;
            } else {
                dummy = dummy.next = b;
                b = b.next;
            }
        }
        l[left] = back.next;
    }


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
}
