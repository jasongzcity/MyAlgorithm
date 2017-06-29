package MeetingRooms_252;

import Interval.Interval;

import java.util.Arrays;

/**
 * Given an array of meeting time intervals consisting of
 * start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * determine if a person could attend all meetings.
 *
 * For example,
 * Given [[0, 30],[5, 10],[15, 20]],
 * return false.
 */
public class Solution {
    public boolean canAttendMeetings(Interval[] intervals) {
//        Arrays.sort(intervals); Interval is not comparable....
        int max = -1;
        for(Interval in:intervals)
            if(in.start>max)
                max = in.start;
        Interval[] map = new Interval[max+1];
        for(Interval in:intervals){
            if(map[in.start]!=null) return false;
            map[in.start] = in;
        }
        int end = -1;
        for(Interval in:map){
            if(in!=null){
                if(end>in.start) return false;
                end = in.end;
            }
        }
        return true;
    }

    public boolean canAttendMeetings2(Interval[] ins){
        int[] begin = new int[ins.length],end = new int[ins.length];
        for(int i=0;i<ins.length;i++){
            begin[i] = ins[i].start;
            end[i] = ins[i].end;
        }
        Arrays.sort(begin);
        Arrays.sort(end);
        for(int i=0;i<ins.length-1;i++)
            if(end[i]>begin[i+1])
                return false;
        return true;
    }
}
