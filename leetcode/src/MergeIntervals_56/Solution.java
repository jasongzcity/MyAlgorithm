package MergeIntervals_56;

import java.util.List;

/**
 * Given a collection of intervals, merge all overlapping intervals.
 *
 * For example,
 * Given [1,3],[2,6],[8,10],[15,18],
 * return [1,6],[8,10],[15,18].
 */
public class Solution {
    /**
     * Definition for an interval.
     * public class Interval {
     *     int start;
     *     int end;
     *     Interval() { start = 0; end = 0; }
     *     Interval(int s, int e) { start = s; end = e; }
     * }
     */
    public List<Interval> merge(List<Interval> intervals) {
        if(intervals.size()<=1) return intervals;
        intervals.sort((o1,o2)-> { return Integer.compare(o1.start, o2.start); });

        for (int i = 0; i < intervals.size()-1; ) {
            Interval i1 = intervals.get(i);
            Interval i2 = intervals.get(i+1);
            if(i1.end>=i2.start){
                if(i1.end<i2.end){
                    i1.end = i2.end;
                }
                intervals.remove(i+1);
            }else{
                i++;
            }
        }
        return intervals;
    }

    static class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }
}
