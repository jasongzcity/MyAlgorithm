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
public class NestedIterator implements Iterator<Integer> {

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

    // Unaccepted solution.
    // We have to search for the next integer in the hasNext() method
//    private LinkedList<List<NestedInteger>> stack = new LinkedList<>();
//    private LinkedList<Integer> numStack = new LinkedList<>();
//
//    public NestedIterator(List<NestedInteger> nestedList) {
//        if(nestedList.size()==0) return;
//        NestedInteger i = nestedList.get(0);
//        stack.add(nestedList);
//        numStack.add(0);
//        while(!i.isInteger()){
//            List<NestedInteger> l = i.getList();
//            stack.add(l);
//            numStack.add(0);
//            i = l.get(0);
//        }
//    }
//
//    @Override
//    public boolean hasNext() {
//        return !stack.isEmpty();
//    }
//
//    @Override
//    public Integer next() {
//        List<NestedInteger> l = stack.pollLast();
//        Integer cur = numStack.pollLast();
//        int rs = l.get(cur++).getInteger();
//        while(true){
//            if(cur==l.size()){
//                if(numStack.isEmpty()) return rs;
//                cur = numStack.pollLast()+1;
//                l = stack.pollLast();
//            }else{
//                if(l.get(cur).isInteger()) break;
//                stack.add(l);
//                numStack.add(cur);
//                l = l.get(cur).getList();
//                cur = 0;
//            }
//        }
//        stack.add(l);
//        numStack.add(cur);
//        return rs;
//    }

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
