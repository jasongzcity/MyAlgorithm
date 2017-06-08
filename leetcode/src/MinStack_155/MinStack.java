package MinStack_155;

import java.util.Stack;

/**
 * Design a stack that supports push, pop, top,
 * and retrieving the minimum element in constant time.
 *
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 */

// most voted solution on leetcode
public class MinStack {

    private long min = 0L;
    private Stack<Long> stack = new Stack<>();

    public MinStack() {}

    public void push(int x) {
        if(stack.isEmpty()){
            stack.push(0l);
            min = x;
        }else{
            stack.push(x-min);
            if(x-min<0) min = x;
        }
    }

    public void pop() {
        if(stack.isEmpty()) return;
        long pop = stack.pop();
        if(pop<0) min-=pop;
    }

    public int top() {
        long top = stack.peek();
        if(top>0) return (int)(top+min);
        else return (int)min;
    }

    public int getMin() {
        return (int)min;
    }
}