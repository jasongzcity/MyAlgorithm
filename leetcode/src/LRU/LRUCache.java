package LRU;

import java.util.HashMap;
import java.util.Map;

/**
 * Design and implement a data structure for Least Recently Used (LRU) cache.
 * It should support the following operations: get and put.
 *
 * get(key) - Get the value (will always be positive)
 * of the key if the key exists in the cache, otherwise return -1.
 *
 * put(key, value) - Set or insert the value if the key is not already present.
 * When the cache reached its capacity,
 * it should invalidate the least recently used item before inserting a new item.
 *
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 *
 * Example:
 *
 * LRUCache cache = new LRUCache( 2 );
 * cache.put(1, 1);
 * cache.put(2, 2);
 * cache.get(1);       // returns 1
 * cache.put(3, 3);    // evicts key 2
 * cache.get(2);       // returns -1 (not found)
 * cache.put(4, 4);    // evicts key 1
 * cache.get(1);       // returns -1 (not found)
 * cache.get(3);       // returns 3
 * cache.get(4);       // returns 4
 */
public class LRUCache{
    public LRUCache(int capacity){
        this.capacity = capacity;
        this.size = 0;
        first.next = last;
        last.prev = first;
        map = new HashMap<>(capacity<<1);
    }

    public int get(int key){
        Entry e = map.get(key);
        if(e==null) return -1;
        moveEntryToFirst(e);
        return e.value;
    }

    public void put(int key, int value){
        if(map.containsKey(key)){
            Entry e = map.get(key);
            e.value = value;
            moveEntryToFirst(e);
            return;
        }
        ++size;
        Entry e = new Entry(key,value);
        addEntryAtFirst(e);
        map.put(key,e);
        if(size>capacity){
            size = capacity;
            Entry le = last.prev;
            removeFromList(le);
            map.remove(le.key);
        }

    }

    private int capacity;

    private int size;

    class Entry{
        int key;
        int value;
        Entry prev;
        Entry next;
        Entry(int k,int v){ this.key = k; this.value = v; }
        Entry(){}
    }

    Map<Integer,Entry> map;

    Entry first = new Entry();
    Entry last = new Entry();

    private void removeFromList(Entry e){
        e.prev.next = e.next;
        e.next.prev = e.prev;
    }

    private void moveEntryToFirst(Entry e){
        if(e.prev==first) return;
        removeFromList(e);
        addEntryAtFirst(e);
    }

    private void addEntryAtFirst(Entry e){
        Entry next = first.next;
        e.next = next;
        next.prev = e;
        first.next = e;
        e.prev = first;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));       // returns 1
        cache.put(3, 3);    // evicts key 2
        System.out.println(cache.get(2));       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        System.out.println(cache.get(1));       // returns -1 (not found)
        System.out.println(cache.get(3));       // returns 3
        System.out.println(cache.get(4));       // returns 4
    }
}
