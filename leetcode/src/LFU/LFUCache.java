package LFU;

import java.util.HashMap;
import java.util.Map;

/**
 * Design and implement a data structure for Least Frequently Used (LFU) cache.
 * It should support the following operations: get and put.
 *
 * get(key) - Get the value (will always be positive) of the key
 * if the key exists in the cache, otherwise return -1.
 *
 * put(key, value) - Set or insert the value if the key is not already present.
 * When the cache reaches its capacity, it should invalidate the least frequently
 * used item before inserting a new item. For the purpose of this problem,
 * when there is a tie (i.e., two or more keys that have the same frequency),
 * the least recently used key would be evicted.
 *
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 *
 * Example:
 * LFUCache cache = new LFUCache( 2 );
 *
 * cache.put(1, 1);
 * cache.put(2, 2);
 * cache.get(1);       // returns 1
 * cache.put(3, 3);    // evicts key 2
 * cache.get(2);       // returns -1 (not found)
 * cache.get(3);       // returns 3.
 * cache.put(4, 4);    // evicts key 1.
 * cache.get(1);       // returns -1 (not found)
 * cache.get(3);       // returns 3
 * cache.get(4);       // returns 4
 */
public class LFUCache{
    public LFUCache(int capacity) {
        this.capacity = capacity;
        first.next = last;
        last.prev = first;
        orderMap = new HashMap<>(capacity<<2);
        touchesMap = new HashMap<>(capacity<<2);
        touchesMap.put(0,last); // for new entries
    }

    public int get(int key){
        Entry e = orderMap.get(key);
        if(e==null) return -1;
        updateTouchMap(e,false);
        return e.value;
    }

    public void put(int key, int value) {
        if(capacity==0) return;
        Entry e = orderMap.get(key);
        if(e!=null){
            e.value = value;
            updateTouchMap(e,false);
        }else{
            if(size==capacity){
                --size;
                Entry remove = last.prev;
                orderMap.remove(remove.key);
                if(touchesMap.get(remove.touches)==remove) touchesMap.remove(remove.touches);
                removeFromList(remove);
            }
            e = new Entry(key,value);
            orderMap.put(key,e);
            updateTouchMap(e,true);
            ++size;
        }
    }

    private void updateTouchMap(Entry e,boolean isNew){
        Entry next = e.next;

        Entry plusOne = touchesMap.get(++e.touches);
        if(plusOne==null) plusOne = touchesMap.get(e.touches-1);
        if(plusOne!=e){
            if (!isNew) removeFromList(e);
            addEntryBetween(e, plusOne, plusOne.prev);
        }
        touchesMap.put(e.touches,e);

        if(touchesMap.get(e.touches-1)==e) {
            if (next.touches == e.touches-1) touchesMap.put(e.touches-1, next);
            else touchesMap.remove(e.touches-1);
        }
    }

    class Entry{
        int key;
        int value;
        Entry prev;
        Entry next;
        int touches = 0; // init with 0
        Entry(){}
        Entry(int k,int v){ this.key = k; this.value = v; }
    }

    private Map<Integer,Entry> orderMap;
    private Map<Integer,Entry> touchesMap;

    private int capacity;
    private int size = 0;

    private Entry first = new Entry();
    private Entry last = new Entry();

    private void removeFromList(Entry e){
        e.prev.next = e.next;
        e.next.prev = e.prev;
    }

    private void addEntryBetween(Entry e,Entry next,Entry prev){
        prev.next = e;
        e.next = next;
        next.prev = e;
        e.prev = prev;
    }

    public static void main(String[] args){
//        LFUCache cache = new LFUCache( 2 /* capacity */ );
//
//        cache.put(1, 1);
//        cache.put(2, 2);
//        System.out.println(cache.get(1));       // returns 1
//        cache.put(3, 3);    // evicts key 2
//        System.out.println(cache.get(2));       // returns -1 (not found)
//        System.out.println(cache.get(3));       // returns 3.
//        cache.put(4, 4);    // evicts key 1.
//        System.out.println(cache.get(1));       // returns -1 (not found)
//        System.out.println(cache.get(3));       // returns 3
//        System.out.println(cache.get(4));       // returns 4
        LFUCache cache = new LFUCache( 1 /* capacity */ );

        cache.put(0, 0);
        System.out.println(cache.get(0));

    }
}
