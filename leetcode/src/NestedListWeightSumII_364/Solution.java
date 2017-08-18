package NestedListWeightSumII_364;

import NestedInteger.NestedInteger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a nested list of integers,
 * return the sum of all integers in the list weighted by their depth.
 *
 * Each element is either an integer,
 * or a list -- whose elements may also be integers or other lists.
 *
 * Different from the previous question where weight is increasing
 * from root to leaf, now the weight is defined from bottom up. i.e.,
 * the leaf level integers have weight 1,
 * and the root level integers have the largest weight.
 *
 * Example 1:
 * Given the list [[1,1],2,[1,1]], return 8. (four 1's at depth 1, one 2 at depth 2)
 *
 * Example 2:
 * Given the list [1,[4,[6]]], return 17.
 * (one 1 at depth 3, one 4 at depth 2, and one 6 at depth 1; 1*3 + 4*2 + 6*1 = 17)
 */
public class Solution {
    public int depthSumInverse2(List<NestedInteger> nestedList) {
        List<List<Integer>> levels = new ArrayList<>();
        dfs(nestedList,0,levels);
        int weight = levels.size(),sum = 0;
        for(List<Integer> l:levels){
            for(Integer i:l) sum+=i*weight;
            weight--;
        }
        return sum;
    }

    private void dfs(List<NestedInteger> l,int index,List<List<Integer>> levels){
        if(index==levels.size()) levels.add(new ArrayList<>());
        List<Integer> list = levels.get(index);

        for(NestedInteger ni:l){
            if(ni.isInteger()) list.add(ni.getInteger());
            else dfs(ni.getList(),index+1,levels);
        }
    }

    // tricky solution
    // most voted and optimal
    // BFS
    public int depthSumInverse(List<NestedInteger> nestedList){
        Queue<NestedInteger> q = new LinkedList<>();
        int weightedSum = 0,sum = 0;
        q.addAll(nestedList);
        while(!q.isEmpty()){
            int size = q.size(),levelSum = 0;
            while(size-->0){
                NestedInteger ni = q.poll();
                if(ni.isInteger()) levelSum+=ni.getInteger();
                else q.addAll(ni.getList());
            }
            // This is tricky. Use a special variable weightedSum
            // to add previous level sum implicitly.
            weightedSum+=levelSum;
            sum+=weightedSum;
        }
        return sum;
    }
}
