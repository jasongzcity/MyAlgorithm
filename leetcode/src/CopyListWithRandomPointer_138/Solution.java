package CopyListWithRandomPointer_138;

import SinglyList.RandomListNode;

import java.util.HashMap;
import java.util.Map;

/**
 * A linked list is given such that each node contains an additional
 * random pointer which could point to any node in the list or null.
 *
 * Return a deep copy of the list.
 */
public class Solution {

    // straight forward solution
    // which is slow...
    public RandomListNode copyRandomList(RandomListNode head) {
        if(head==null) return null;
        Map<RandomListNode,RandomListNode> pairs = new HashMap<>();
        RandomListNode node = head;
        while(node!=null){
            pairs.put(node,new RandomListNode(node.label));
            node = node.next;
        }
        for(Map.Entry<RandomListNode,RandomListNode> e:pairs.entrySet()){
            e.getValue().next = pairs.get(e.getKey().next);
            e.getValue().random = pairs.get(e.getKey().random);
        }
        return pairs.get(head);
    }

    public static void main(String[] args) {
        Integer i = 1;
        System.out.println(i.hashCode());
        Object o = new Object();
        System.out.println(o.hashCode());
    }

    // iterative solution
    public RandomListNode copyRandomList2(RandomListNode head){
        // use 3-pass to copy a list
        RandomListNode cur = head,next,copy;
        // first pass, just copy
        while(cur!=null){
            next = cur.next;
            copy = new RandomListNode(cur.label);
            cur.next = copy;
            copy.next = next;
            cur = next;
        }

        // second pass to link the node's random
        cur = head;
        while(cur!=null){
            if(cur.random!=null) cur.next.random = cur.random.next;
            cur = cur.next.next;
        }

        // third pass, extract new nodes from the origin list.
        cur = head;
        RandomListNode dummy = new RandomListNode(0);
        copy = dummy;
        while(cur!=null){
            next = cur.next;
            cur.next = next.next;
            cur = cur.next;
            copy.next = next;
            copy = next;
        }
        return dummy.next;
    }
}
