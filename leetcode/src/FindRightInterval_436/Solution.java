package FindRightInterval_436;

import Interval.Interval;

import java.util.Arrays;

/**
 * Given a set of intervals, for each of the interval i,
 * check if there exists an interval j whose start point
 * is bigger than or equal to the end point of the interval i,
 * which can be called that j is on the "right" of i.
 *
 * For any interval i, you need to store the minimum interval j's index,
 * which means that the interval j has the minimum start point
 * to build the "right" relationship for interval i.
 * If the interval j doesn't exist, store -1 for the interval i.
 * Finally, you need output the stored value of each interval as an array.
 *
 * Note:
 * You may assume the interval's end point is always bigger than its start point.
 * You may assume none of these intervals have the same start point.
 *
 * Example 1:
 * Input: [ [1,2] ]
 * Output: [-1]
 * Explanation: There is only one interval in the collection, so it outputs -1.
 *
 * Example 2:
 * Input: [ [3,4], [2,3], [1,2] ]
 * Output: [-1, 0, 1]
 * Explanation: There is no satisfied "right" interval for [3,4].
 * For [2,3], the interval [3,4] has minimum-"right" start point;
 * For [1,2], the interval [2,3] has minimum-"right" start point.
 *
 * Example 3:
 * Input: [ [1,4], [2,3], [3,4] ]
 * Output: [-1, 2, -1]
 * Explanation: There is no satisfied "right" interval for [1,4] and [3,4].
 * For [2,3], the interval [3,4] has minimum-"right" start point.
 */
public class Solution {
    // sort then search? pretty naive...
    public int[] findRightInterval(Interval[] i) {
        if(i.length==1) return new int[]{-1};
        int[] rs = new int[i.length];
        Tuple[] sorted = new Tuple[i.length];
        for(int p=0;p<i.length;p++) sorted[p] = new Tuple(i[p].start,i[p].end,p);
        Arrays.sort(sorted);
        for(int p=0;p<i.length;p++){
            int key = i[p].end;
            int lo = 0,hi = i.length;
            while(lo<hi){
                int mid = (lo+hi)>>>1;
                if(sorted[mid].begin>=key) hi = mid;
                else lo = mid+1;
            }
            if(lo==i.length) rs[p] = -1;
            else rs[p] = sorted[lo].index;
        }
        return rs;
    }

    // There is another thought:
    // don't use new class tuple. Instead, use
    // Inteval's end field to store its index.

    public static void main(String[] args) {
        Solution s = new Solution();
        Interval[] input = new Interval[]{
                new Interval(3,4),
                new Interval(2,3),
                new Interval(1,2),
        };
        System.out.println(Arrays.toString(s.findRightInterval(input)));
        Interval[] input2 = new Interval[]{
                new Interval(1,4),
                new Interval(2,3),
                new Interval(3,4),
        };
        System.out.println(Arrays.toString(s.findRightInterval(input2)));
    }

    class Tuple implements Comparable<Tuple>{

        int begin;
        int end;
        int index;

        public Tuple(int begin,int end,int index){
            this.begin = begin;
            this.end = end;
            this.index = index;
        }

        @Override
        public int compareTo(Tuple o) {
            return this.begin-o.begin;
        }
    }
}
