package MinStack_155;

/**
 * Design a stack that supports push, pop, top,
 * and retrieving the minimum element in constant time.
 *
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 */

// much better solution
public class MinStack2 {

    private Node head;

    public MinStack2() {}

    public void push(int x) {
        if(head==null) head = new Node(x,x,null);
        else head = new Node(x,Math.min(x,head.min),head);
    }

    public void pop() {
        if(head==null) return;
        head = head.next;
    }

    public int top() { return head.val; }

    public int getMin() { return head.min; }

    class Node{
        int val;
        int min;
        Node next;

        Node(int v,int m,Node n){
            val = v;
            min = m;
            next = n;
        }
    }
}
