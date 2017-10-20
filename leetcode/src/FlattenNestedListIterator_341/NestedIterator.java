package FlattenNestedListIterator_341;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import NestedInteger.NestedInteger;

/**
 * Given a nested list of integers, implement an iterator to flatten it.
 *
 * Each element is either an integer, or a list --
 * whose elements may also be integers or other lists.
 *
 * Example 1:
 * Given the list [[1,1],2,[1,1]],
 *
 * By calling next repeatedly until hasNext returns false,
 * the order of elements returned by next should be: [1,1,2,1,1].
 *
 * Example 2:
 * Given the list [1,[4,[6]]],
 *
 * By calling next repeatedly until hasNext returns false,
 * the order of elements returned by next should be: [1,4,6].
 */
/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer,
 *     rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds,
 *     if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds,
 *     if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class NestedIterator implements Iterator<Integer> {

    private LinkedList<Iterator<NestedInteger>> st = new LinkedList<>();
    private NestedInteger nextone;

    // second session
    // First thought: stack
    public Integer next2(){
        return nextone.getInteger();
    }

    public boolean hasNext2(){
//        while(st.size()!=0&&!st.getFirst().hasNext()){
//            st.poll();
//        }
//        if(st.size()==0) return false;
//        NestedInteger ni = st.getFirst().next();
//        while()
        // code above will have bugs
        // the next next nested Integer may be an empty list!
        // so you must find the next nestedInteger which is an integer.
        // there are corner cases that the list may be empty
        while(st.size()>0){
            if(!st.getFirst().hasNext()) st.poll();
            else if((nextone = st.getFirst().next()).isInteger()) return true;
            else st.addFirst(nextone.getList().iterator());
        }
        return false;
    }

    public NestedIterator(List<NestedInteger> nestedList, boolean voidVar){
        st.addFirst(nestedList.iterator());
    }



    // optimal solution on leetcode
    NestedInteger nextInt;
    LinkedList<Iterator<NestedInteger>> stack = new LinkedList<>();

    public NestedIterator(List<NestedInteger> nestedList) {
        stack.push(nestedList.iterator());
    }

    @Override
    public Integer next() {
        return nextInt != null ? nextInt.getInteger() : null; //Just in case
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty()) {
            if (!stack.peek().hasNext()) stack.pop();
            else if ((nextInt = stack.peek().next()).isInteger()) return true;
            else stack.push(nextInt.getList().iterator());
        }
        return false;
    }
}
