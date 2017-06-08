package RandomizedSetO1_380;

import java.util.*;

/**
 * Design a data structure that supports
 * all following operations in average O(1) time.
 *
 * insert(val): Inserts an item val to the set if not already present.
 * remove(val): Removes an item val from the set if present.
 * getRandom: Returns a random element from current set of elements.
 * Each element must have the same probability of being returned.
 */
public class RandomizedSet {

    private List<Integer> list = new ArrayList<>(128);
    private Map<Integer,Integer> positions = new HashMap<>(128);
    private Random rand = new Random(System.currentTimeMillis());

    /** Initialize your data structure here. */
    public RandomizedSet(){}

    /** Inserts a value to the set.
     *  Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        Integer prev = positions.putIfAbsent(val,list.size());
        if(prev!=null) return false; // already contains
        list.add(val);
        return true;
    }

    /** Removes a value from the set.
     *  Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        Integer pos = positions.remove(val);
        if(pos==null) return false;
        if(pos<list.size()-1){
            int last = list.get(list.size()-1);
            list.set(pos,last);
            positions.put(last,pos);
        }
        list.remove(list.size()-1);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        return list.get(rand.nextInt(list.size()));
    }

    public static void main(String[] args) {
        RandomizedSet set = new RandomizedSet();
        set.insert(0);
        set.insert(1);
        set.remove(0);
        set.insert(2);
        set.remove(1);
        System.out.println(set.getRandom());
    }
}
