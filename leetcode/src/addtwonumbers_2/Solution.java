package addtwonumbers_2;

import SinglyList.ListNode;

/**
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero,
 * except the number 0 itself.
 *
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 */
public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int current = 0;
        int add = 0;
        ListNode lr = new ListNode(0);// the sentinel.
        ListNode backup = lr;
        while(l1!=null||l2!=null||add!=0) {
            if(l1!=null) {
                current += l1.val;
                l1 = l1.next;
            }else
                current+=0;
            if(l2!=null){
                current+=l2.val;
                l2 = l2.next;
            }else
                current+=0;
            current += add;
            add = 0;// Set 0 to check for next digit.
            if(current>9){
                add = 1;
                current -= 10;
            }
            lr.next = new ListNode(current);
            current = 0;
            lr = lr.next;
        }
        return backup.next;
    }
}
