package addtwonumbers_2;

/**
 * Created by Administrator on 2017/3/7.
 */
public class Solution
{
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2)
    {
        int current = 0;
        int add = 0;
        ListNode lr = new ListNode(0);// the sentinel.
        ListNode backup = lr;
        while(l1!=null||l2!=null||add!=0)
        {
            if(l1!=null) {
                current += l1.val;
                l1 = l1.next;
            }
            else
                current+=0;
            if(l2!=null){
                current+=l2.val;
                l2 = l2.next;
            }
            else
                current+=0;
            current += add;
            add = 0;// Set 0 to check for next digit.
            if(current>9)
            {
                add = 1;
                current -= 10;
            }
            lr.next = new ListNode(current);
            current = 0;
            lr = lr.next;
        }
        return backup.next;
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
    }

    public static void main(String[] args)
    {
        ListNode l1 = new ListNode("11119");
        ListNode l2 = new ListNode("9999199");
        showListNode(addTwoNumbers(l1,l2));
    }
}
