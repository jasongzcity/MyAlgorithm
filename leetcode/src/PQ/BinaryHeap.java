package PQ;

import java.util.Arrays;
import java.util.Random;

/**
 * Implement a binary heap for primordial "int" type.
 *
 * It's a simple implementation and is not
 * well-encapsulated against extreme input.
 *
 * This heap will put the smallest element at first,
 * and will use primordial '<' and '>' to compare elements.
 */
public final class BinaryHeap
{
    private static int DEFAULT_CAPACITY = 16;

    private int[] table;    // table for integers

    private int size;

    /* shortcuts for better readability */
    private int parent(int child){
        return (child-1)>>1;
    }

    private int rightChild(int parent){
        return (parent<<1)+2;
    }

    private int leftChild(int parent){
        return (parent<<1)+1;
    }

    public int capacity() {
        return table.length;
    }

    public int size() {
        return size;
    }

    BinaryHeap(){
        table = new int[DEFAULT_CAPACITY];
        size = 0;
    }

    BinaryHeap(int initial_capacity){
        if(initial_capacity>DEFAULT_CAPACITY) 
            table = new int[initial_capacity];
        else table = new int[DEFAULT_CAPACITY];
        size = 0;
    }

    private boolean isValid(int index){
        return index>-1&&index<size;
    }

    private void percolateUp(int index){
        if(!isValid(index)) return;
        int parent = parent(index),i = index,backup = table[index];
        while(isValid(parent)&&backup<table[parent]){
            table[i] = table[parent];
            i = parent;
            parent = parent(i);
        }
        table[i] = backup;
    }

    private void percolateDown(int index){
        if(!isValid(index)) return;
        int i = index,backup = table[index],smallerChild = -1;
        while(isValid(leftChild(i))){
            if(isValid(rightChild(i))){
                smallerChild = table[leftChild(i)]<table[rightChild(i)]?
                                leftChild(i):rightChild(i);
            }else{
                smallerChild = leftChild(i);
            }
            if(backup>table[smallerChild]){
                table[i] = table[smallerChild];
                i = smallerChild;
            }else{
                break;
            }
        }
        table[i] = backup;
    }

    private void ensureCapacity(int capacity){
        if(capacity<=table.length) return;
        int[] newTable = new int[capacity<<1];
        System.arraycopy(table,0,newTable,0,size);
        table = newTable;
    }

    public int peek(){
        return table[0];
    }

    public int pop(){
        int rs = table[0];
        table[0] = table[size-1];
        --size;
        percolateDown(0);
        return rs;
    }

    public void insert(int e){
        ensureCapacity(size+1);
        table[size] = e;
        ++size;
        percolateUp(size-1);
    }

    public static void main(String[] args)
    {
        BinaryHeap heap = new BinaryHeap();
        Random rand = new Random(47);
        for (int i = 0; i < 32; i++) {
            heap.insert(rand.nextInt(20000));
        }
        for (int i = 0; i < 20; i++) {
            heap.pop();
        }
        for (int i = 0; i < 38; i++) {
            heap.insert(rand.nextInt(20000));
        }
        int[] result = new int[heap.size()];
        int i = 0;
        while(heap.size()!=0){
            result[i] = heap.pop();
            ++i;
        }
        System.out.println(Arrays.toString(result));
    }
}
