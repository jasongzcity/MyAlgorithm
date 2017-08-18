package NestedListWeightSum_339;

import NestedInteger.NestedInteger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a nested list of integers,
 * return the sum of all integers in the list weighted by their depth.
 *
 * Each element is either an integer,
 * or a list -- whose elements may also be integers or other lists.
 *
 * Example 1:
 * Given the list [[1,1],2,[1,1]], return 10. (four 1's at depth 2, one 2 at depth 1)
 *
 * Example 2:
 * Given the list [1,[4,[6]]], return 27.
 * (one 1 at depth 1, one 4 at depth 2, and one 6 at depth 3; 1 + 4*2 + 6*3 = 27)
 */
public class Solution {
    public int depthSum2(List<NestedInteger> nestedList) {
        LinkedList<Iterator<NestedInteger>> stack = new LinkedList<>();
        int level = 1,sum = 0;
        stack.push(nestedList.iterator());
        while(!stack.isEmpty()){
            if (!stack.peek().hasNext()){
                stack.pop();
                --level;
            }else{
                NestedInteger i = stack.peek().next();
                if(i.isInteger()) sum+=i.getInteger()*level;
                else{
                    stack.push(i.getList().iterator());
                    ++level;
                }
            }
        }
        return sum;
    }

    // recursive solution
    public int depthSum(List<NestedInteger> nestedList){
        return getSum(nestedList,1);
    }

    private int getSum(List<NestedInteger> i,int depth){
        int sum = 0;
        for(NestedInteger ni:i){
            if(ni.isInteger()) sum+=depth*ni.getInteger();
            else sum+=getSum(ni.getList(),depth+1);
        }
        return sum;
    }
}
