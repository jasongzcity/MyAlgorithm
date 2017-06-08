package FindMedianFromDataStream_295;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Median is the middle value in an ordered integer list.
 * If the size of the list is even, there is no middle value.
 * So the median is the mean of the two middle value.
 *
 * Examples:
 * [2,3,4] , the median is 3
 * [2,3], the median is (2 + 3) / 2 = 2.5
 *
 * Design a data structure that supports the following two operations:
 *
 * void addNum(int num) -
 * Add a integer number from the data stream to the data structure.
 * double findMedian() -
 * Return the median of all elements so far.
 *
 * For example:
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 */
public class MedianFinder{

    // Simply use two binary heap will solve.
    private PriorityQueue<Integer> larger = new PriorityQueue<>();
    private PriorityQueue<Integer> smaller =
            new PriorityQueue<>((x, y) -> -Integer.compare(x,y));

    private double median = 0;
    private int size = 0;

    public MedianFinder(){}

    public void addNum(int num) {
        ++size;
        if(num>=median) larger.add(num);
        else smaller.add(num);
        boolean flag = larger.size()>smaller.size();
        if((size&1)==1){
            median = flag?larger.peek():smaller.peek();
        }else{
            if(larger.size()!=smaller.size()){ // rebalance
                if(flag) smaller.add(larger.poll());
                else larger.add(smaller.poll());
            }
            median = (double)(larger.peek()+smaller.peek())/2;
        }
    }

    public double findMedian() { return median; }

    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        mf.addNum(2);
        System.out.println(mf.findMedian());
        mf.addNum(3);
        System.out.println(mf.findMedian());
    }
}
