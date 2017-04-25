package SinglyList;

/**
 * Singly list for tests.
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x){this.val = x;}

    public ListNode(String str) {
        //ListNode temp = new ListNode(Integer.parseInt(str.substring(0,1)));
        this.val = Integer.parseInt(str.substring(0,1));
        ListNode temp = this;
        for(int i=1;i<str.length();i++) {
            ListNode next = new ListNode(Integer.parseInt(str.substring(i,i+1)));
            temp.next = next;
            temp = temp.next;
        }
    }

    public void displayList(){
        ListNode l = this;
        while(l!=null){
            System.out.print(String.valueOf(l.val)+" ");
            l = l.next;
        }
        System.out.println();
    }
}
