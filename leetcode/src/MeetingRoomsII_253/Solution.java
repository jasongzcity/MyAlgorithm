package MeetingRoomsII_253;

import Interval.Interval;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Given an array of meeting time intervals consisting of
 * start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * find the minimum number of conference rooms required.
 *
 * For example,
 * Given [[0, 30],[5, 10],[15, 20]],
 * return 2.
 */
public class Solution {
    // Accepted greedy solution.
    public int minMeetingRooms2(Interval[] ins) {
        if (ins.length < 2) return ins.length;
        Arrays.sort(ins, (i1, i2) -> (i1.start - i2.start));
        int rooms = 0, count = ins.length;
        while (count > 0) {
            ++rooms;
            int end = Integer.MIN_VALUE;
            for (int i = 0; i < ins.length; i++) {
                if (ins[i] != null && ins[i].start >= end) {
                    end = ins[i].end;
                    ins[i] = null;
                    --count;
                }
            }
        }
        return rooms;
    }

    public static void main(String[] args) {
        Interval[] ins = new Interval[]{
                new Interval(2, 15),
                new Interval(36, 45),
                new Interval(9, 29),
                new Interval(16, 23),
                new Interval(4, 9)
        };
        Solution s = new Solution();
        System.out.println(s.minMeetingRooms(ins));
    }

    // its brute force! And it has to iterate the array
    // for multiple times...
    public int minMeetingRooms(Interval[] ins) {
        if (ins.length < 2) return ins.length;
        Integer[] begin = new Integer[ins.length];
        int[] end = new int[ins.length];
        for (int i = 0; i < ins.length; i++) {
            begin[i] = ins[i].start;
            end[i] = ins[i].end;
        }
        Arrays.sort(begin);
        Arrays.sort(end);
        int count = ins.length, rooms = 0;
        while (count > 0) {
            int endTime = Integer.MIN_VALUE;
            ++rooms;
            for (int i = 0; i < ins.length; i++) {
                if (begin[i] != null && begin[i] >= endTime) {
                    --count;
                    endTime = end[i];
                    begin[i] = null;
                }
            }
        }
        return rooms;
    }

    // Optimal solution which is really amazing!
    // Accepted. Detailed explanation at:
    // https://discuss.leetcode.com/topic/35253/
    // explanation-of-super-easy-java-solution-beats-98-8-from-pinkfloyda
    public int minMeetingRooms3(Interval[] ins) {
        if(ins.length<2) return ins.length;
        int rooms = 0,endPointer = 0;
        int[] start = new int[ins.length],end = new int[ins.length];
        for(int i=0;i<ins.length;start[i]=ins[i].start,end[i]=ins[i].end,i++);
        Arrays.sort(start);
        Arrays.sort(end);
        for(int i=0;i<ins.length;i++){
            if(start[i]<end[endPointer]) ++rooms;
            else ++endPointer;
        }
        return rooms;
    }

    // Another amazing solution using heap
    // Very clear and smart
    // detailed comment and code at:
    // https://discuss.leetcode.com/topic/20958/ac-java-solution-using-min-heap
    public int minMeetingRooms4(Interval[] ins){
        if(ins.length<2) return ins.length;
        Arrays.sort(ins,(i1,i2)->(i1.start-i2.start));
        PriorityQueue<Interval> pq = new PriorityQueue<>((i1,i2)->(i1.end-i2.end));
        pq.add(ins[0]);
        for(int i=1;i<ins.length;i++){
            // The meeting which ends earliest has already ends
            if(ins[i].start>=pq.peek().end) pq.poll();
            pq.add(ins[i]);
        }
        return pq.size();
    }
}