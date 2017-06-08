package RandomizedSetDupO1_381;

import java.util.*;

/**
 * Follow up for #381, what if it allows duplicate elements.
 * Design a data structure that supports
 * all following operations in average O(1) time.
 * Note: Duplicate elements are allowed.
 *
 * insert(val): Inserts an item val to the collection.
 * remove(val): Removes an item val from the collection if present.
 * getRandom: Returns a random element from current collection of elements.
 * The probability of each element being returned is
 * linearly related to the number of same value the collection contains.
 *
 * Example:
 *
 * // Init an empty collection.
 * RandomizedCollection collection = new RandomizedCollection();
 *
 * // Inserts 1 to the collection. Returns true as the collection did not contain 1.
 * collection.insert(1);
 *
 * // Inserts another 1 to the collection.
 * // Returns false as the collection contained 1. Collection now contains [1,1].
 * collection.insert(1);
 *
 * // Inserts 2 to the collection, returns true. Collection now contains [1,1,2].
 * collection.insert(2);
 *
 * // getRandom should return 1 with the probability 2/3,
 * // and returns 2 with the probability 1/3.
 * collection.getRandom();
 *
 * // Removes 1 from the collection, returns true. Collection now contains [1,2].
 * collection.remove(1);
 *
 * // getRandom should return 1 and 2 both equally likely.
 * collection.getRandom();
 */
public class RandomizedCollection {
    // accepted.
    private List<Node> list = new ArrayList<>(128);
    private Map<Integer,Integer> map = new HashMap<>(128);
    private Random rand = new Random();

    /** Initialize your data structure here. */
    public RandomizedCollection(){}

    /** Inserts a value to the collection.
     *  Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        Node newNode = new Node(val,list.size());
        Integer i = map.put(val,list.size());
        if(i!=null) newNode.next = list.get(i);
        list.add(newNode);
        return i==null;
    }

    /** Removes a value from the collection.
     *  Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        Integer i = map.remove(val);
        if(i==null) return false;
        Node n = list.get(i);
        if(n.next!=null) map.put(val,n.next.index);
        int size = list.size();
        if(i!=size-1){
            Node delete = list.get(size-1);
            if(map.get(delete.val)==size-1) map.put(delete.val,i);
            list.set(i,delete);
            delete.index = i;
        }
        list.remove(size-1);
        return true;
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        return list.get(rand.nextInt(list.size())).val;
    }

    class Node{
        int val;
        int index;
        Node next;
        Node(int v,int i){ this.val = v; this.index = i; }
    }

    public static void main(String[] args) {
        RandomizedCollection c = new RandomizedCollection();
        c.insert(1);
        c.insert(1);
        c.insert(2);
        c.insert(2);
        c.insert(2);
        c.remove(1);
        c.remove(1);
        c.remove(2);
        c.insert(1);
        c.remove(2);
        System.out.println(c.getRandom());
    }
}
