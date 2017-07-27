package ImplementQueueUsingStack_232;

import java.util.LinkedList;

/**
 * Implement the following operations of a queue using stacks.
 *
 * push(x) -- Push element x to the back of queue.
 * pop() -- Removes the element from in front of queue.
 * peek() -- Get the front element.
 * empty() -- Return whether the queue is empty.
 *
 * Notes:
 * You must use only standard operations of a stack --
 * which means only push to top, peek/pop from top, size,
 * and is empty operations are valid.
 *
 * Depending on your language, stack may not be supported natively.
 * You may simulate a stack by using a list or deque (double-ended queue),
 * as long as you use only standard operations of a stack.
 *
 * You may assume that all operations are valid
 * (for example, no pop or peek operations will be called on an empty queue).
 */
public class MyQueue {

    // use linked list-backed stack.
    // add as push, removeLast as pop, getLast as peek
    private LinkedList<Integer> input = new LinkedList<>();
    private LinkedList<Integer> output = new LinkedList<>();

    /** Initialize your data structure here. */
    public MyQueue(){}

    /** Push element x to the back of queue. */
    public void push(int x) {
        input.add(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        if(output.size()==0) inputToOutput();
        return output.removeLast();
    }

    /** Get the front element. */
    public int peek() {
        if(output.size()==0) inputToOutput();
        return output.getLast();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return input.size()==0&&output.size()==0;
    }

    private void inputToOutput(){
        while(input.size()!=0) output.add(input.removeLast());
    }
}
