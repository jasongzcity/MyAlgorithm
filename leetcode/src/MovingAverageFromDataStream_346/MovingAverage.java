package MovingAverageFromDataStream_346;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a stream of integers and a window size,
 * calculate the moving average of all integers in the sliding window.
 *
 * For example,
 * MovingAverage m = new MovingAverage(3);
 * m.next(1) = 1
 * m.next(10) = (1 + 10) / 2
 * m.next(3) = (1 + 10 + 3) / 3
 * m.next(5) = (10 + 3 + 5) / 3
 */
public class MovingAverage {

    private int window;
    private Queue<Integer> q;
    private int nums = 0;
    private double average = 0;

    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        window = size;
        q = new LinkedList<>();
    }

    public double next(int val) {
        if(nums<window) average = (average*nums+val)/++nums;
        else average += ((double)val-q.poll())/window;
        q.add(val);
        return average;
    }

    public static void main(String[] args) {
        MovingAverage s = new MovingAverage(3);
        System.out.println(s.next(1));
        System.out.println(s.next(10));
        System.out.println(s.next(3));
        System.out.println(s.next(5));
    }
}
