package TwoSumIII_DSDesign_170;

import java.util.*;

/**
 * Design and implement a TwoSum class.
 * It should support the following operations: add and find.
 *
 * add - Add the number to an internal data structure.
 * find - Find if there exists any pair of numbers which sum is equal to the value.
 *
 * For example,
 * add(1); add(3); add(5);
 * find(4) -> true
 * find(7) -> false
 */
public class TwoSum {

    private TreeSet<Integer> set = new TreeSet<>();
    private Set<Integer> sums = new HashSet<>(64);

    /** Initialize your data structure here. */
    public TwoSum(){}

    /** Add the number to an internal data structure.. */
    public void add2(int number) {
        // O(logn) time complexity
        // for using treeset
        if(set.contains(number)) sums.add(number<<1);
        else set.add(number);
    }

    /** Find if there exists any pair of numbers which sum is equal to the value. */
    public boolean find2(int value) {
        if(set.isEmpty()) return false;
        if(sums.contains(value)) return true;
        Iterator<Integer> ascIter = set.iterator();
        Iterator<Integer> descIter = set.descendingIterator();
        int low = ascIter.next(),hi = descIter.next();
        while(low!=hi&&low+hi!=value){
            if(low+hi<value) low = ascIter.next();
            else hi = descIter.next();
        }
        return low!=hi;
    }

    // optimal solution on leetcode
    // use another method to deal with duplicate input elements
    private List<Integer> list  = new ArrayList<>(64);
    private Map<Integer,Integer> map = new HashMap<>(64);
    // use min and max to accelerate find
    private int min = Integer.MAX_VALUE,max = Integer.MIN_VALUE;

    public void add(int number) {
        if(number>max) max = number;
        if(number<min) min = number;
        map.put(number,list.size());
        list.add(number);
    }

    public boolean find(int value){
        if(value>max<<1||value<min<<1) return false; // shortcut
        for(int i=0;i<list.size();i++){
            int tar = value-list.get(i);
            Integer v = map.get(tar);
            if(v!=null&&v!=i) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        TwoSum ts = new TwoSum();
        ts.add(1);
        ts.add(3);
        ts.add(5);
        System.out.println(ts.find(4));
    }
}
