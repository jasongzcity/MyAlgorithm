package InsertInterval_57;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a set of non-overlapping intervals,
 * insert a new interval into the intervals (merge if necessary).
 *
 * You may assume that the intervals were initially sorted
 * according to their start times.
 *
 * Example 1:
 * Given intervals [1,3],[6,9],
 * insert and merge [2,5] in as [1,5],[6,9].
 *
 * Example 2:
 * Given [1,2],[3,5],[6,7],[8,10],[12,16],
 * insert and merge [4,9] in as [1,2],[3,10],[12,16].
 * This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
 */
public class Solution {
    public static List<Interval> insert(List<Interval> intervals, Interval newInterval){
        boolean added = false;

        for(int i=0;i<intervals.size();){
            Interval in = intervals.get(i);
            if(!added&&in.start>=newInterval.start){
                intervals.add(i,newInterval);
                i = i==0?0:i-1;
                added = true;
                continue;
            }
            if(i<intervals.size()-1){
                Interval innext = intervals.get(i+1);
                if(in.end>=innext.start){
                    in.end = Math.max(innext.end,in.end);
                    intervals.remove(i+1);
                    continue;
                }
            }
            ++i;
        }
        if(!added){
            if(intervals.size()==0){
                intervals.add(newInterval);
            }else{
                Interval in = intervals.get(intervals.size()-1);
                if(in.end>=newInterval.start)
                    in.end = Math.max(newInterval.end,in.end);
                else
                    intervals.add(newInterval);
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

    // most voted solution on leetcode
    public static List<Interval> insert2(List<Interval> intervals, Interval newInterval){
        int i = 0;
        while(i<intervals.size()&&intervals.get(i).end<newInterval.start) ++i;
        // now element at i position is overlapped with newInterval
        while(i<intervals.size()){
            Interval in = intervals.get(i);
            if(in.start<=newInterval.end){
                newInterval.start = Math.min(newInterval.start,in.start);
                newInterval.end = Math.max(newInterval.end,in.end);
                intervals.remove(i);
            }else{
                break;
            }
        }
        intervals.add(i,newInterval);
        return intervals;
    }

    public static void main(String[] args) {
        List<Integer> l = new ArrayList<>();
        l.add(l.size(),1);
    }
}
