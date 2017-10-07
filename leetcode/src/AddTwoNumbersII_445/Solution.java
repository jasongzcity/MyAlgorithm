package AddTwoNumbersII_445;

import SinglyList.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * You are given two non-empty linked lists representing two non-negative integers.
 * The most significant digit comes first and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero,
 * except the number 0 itself.
 *
 * Follow up:
 * What if you cannot modify the input lists?
 * In other words, reversing the lists is not allowed.
 *
 * Example:
 * Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 8 -> 0 -> 7
 */
public class Solution {
    // This solution is not accepted!
    // In some test cases it will cause overflow while summing up.
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        // non negetive, so we got two number,
        // add them up and produce a list.
        int num1 = 0,num2 = 0;
        ListNode pointer = l1;
        while(pointer!=null){
            num1 = num1*10+pointer.val;
            pointer = pointer.next;
        }
        pointer = l2;
        while(pointer!=null){
            num2 = num2*10+pointer.val;
            pointer = pointer.next;
        }
        int sum = num1+num2; // we assume it won't overflow...
        ListNode rs = null;
        while(sum!=0){
            ListNode tm = new ListNode(sum%10);
            tm.next = rs;
            rs = tm;
            sum/=10;
        }
        return rs==null?new ListNode(0):rs;
    }

    // The input l1 and l2 should be in the same length
    // or the result will be unpredictable
    private ListNode sum(ListNode l1,ListNode l2){
        if(l1==null) return null;
        ListNode lower = sum(l1.next,l2.next);
        ListNode rs = new ListNode(l1.val+l2.val);
        if(lower!=null&&lower.val>=10){
            lower.val-=10;
            rs.val++;
        }
        rs.next = lower;
        return rs;
    }

    public ListNode addTwoNumbers3(ListNode l1, ListNode l2){
        int len1 = 0,len2 = 0,alen,blen;
        ListNode counter = l1,shorter,longer,rs = null;
        while(counter!=null){
            len1++;
            counter = counter.next;
        }
        counter = l2;
        while(counter!=null){
            len2++;
            counter = counter.next;
        }
        if(len1>=len2){
            longer = l1;
            shorter = l2;
            alen = len1-len2;
            blen = len2;
        }else{
            longer = l2;
            shorter = l1;
            alen = len2-len1;
            blen = len1;
        }
        // use this list as stack
        List<ListNode> list = new ArrayList<>((len1+len2)<<1);
        l1 = longer;
        l2 = shorter;
        for(int i=0;i<alen;i++){
            list.add(l1);
            l1 = l1.next;
        }
        for(int i=0;i<blen;i++){
            list.add(l1);
            list.add(l2);
            l1 = l1.next;
            l2 = l2.next;
        }
        boolean plus1 = false;
        for(int i=0;i<blen;i++){
            l1 = list.remove(list.size()-1);
            l2 = list.remove(list.size()-1);
            ListNode tm;
            if(plus1) tm = new ListNode(l1.val+l2.val+1);
            else tm = new ListNode(l1.val+l2.val);
            if(tm.val>=10){
                tm.val-=10;
                plus1 = true;
            }else plus1 = false;
            tm.next = rs;
            rs = tm;
        }
        for(int i=0;i<alen;i++){
            l1 = list.remove(list.size()-1);
            ListNode tm;
            if(plus1){
                tm = new ListNode(l1.val+1);
                if(tm.val>=10){
                    plus1 = true;
                    tm.val-=10;
                }else plus1 = false;
            }
            else tm = new ListNode(l1.val);
            tm.next = rs;
            rs = tm;
        }
        if(plus1){
            ListNode newHead = new ListNode(1);
            newHead.next = rs;
            rs = newHead;
        }
        return rs;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode("399999999999");
        ListNode l2 = new ListNode("7");
        Solution s = new Solution();
        l1 = s.addTwoNumbers(l1,l2);
        l1.displayList();
    }

    // solution above is too slow and too long...
    // leetcode optimal solution
    public ListNode addTwoNumbers(ListNode l1, ListNode l2){
        // use them as stack
        ArrayList<Integer> s1 = new ArrayList<>(36);
        ArrayList<Integer> s2 = new ArrayList<>(36);

        while(l1!=null){
            s1.add(l1.val);
            l1 = l1.next;
        }
        while(l2!=null){
            s2.add(l2.val);
            l2 = l2.next;
        }
        ListNode rs = null;
        int sum = 0;
        while(s1.size()!=0||s2.size()!=0){
            if(s1.size()!=0) sum+=s1.remove(s1.size()-1);
            if(s2.size()!=0) sum+=s2.remove(s2.size()-1);
            ListNode newHead = new ListNode(sum%10);
            newHead.next = rs;
            rs = newHead;
            sum/=10;
        }
        if(sum==1){
            ListNode newHead = new ListNode(1);
            newHead.next = rs;
            rs = newHead;
        }
        return rs;
    }

    // second session!
    public ListNode addTwoNumbers4(ListNode l1, ListNode l2) {
        ListNode longer, shorter;
        int count1 = count(l1), count2 = count(l2), diff = Math.abs(count1-count2);
        if(count1>count2){
            longer = l1;
            shorter = l2;
        }else {
            longer = l2;
            shorter = l1;
        }
        if(add(longer, shorter, diff)){
            ListNode n = new ListNode(1);
            n.next = longer;
            return n;
        }else{
            return longer;
        }
    }

    private boolean add(ListNode longer, ListNode shorter, int diff){
        if(longer==null) return false;
        boolean plus1;
        int sum;
        if(diff > 0){
            plus1 = add(longer.next, shorter, diff-1);
            sum = longer.val;
        }else{
            plus1 = add(longer.next, shorter.next, diff);
            sum = longer.val+shorter.val;
        }
        if(plus1) ++sum;
        if(sum>=10){
            plus1 = true;
            longer.val = sum-10;
        }else{
            plus1 = false;
            longer.val = sum;
        }
        return plus1;
    }

    private int count(ListNode head){
        int count = 0;
        while(head!=null){
            ++count;
            head = head.next;
        }
        return count;
    }
}
