package WeightedMeetingRooms;

import Interval.Interval;

/**
 * This problem is from CSCI-B503, in DP topic.
 * It's just like classic interval problem. However, in this problem,
 * every interval is weighted, we need to find out a way to schedule
 * the meeting rooms and get the highest sum of weights.
 */
public class Solution {

    // thought:
    // sort intervals by their end time.
    // if we define f(i) as the largest weights we can get from
    // the previous i intervals, then f(j) = max(f(j-1), f(k)+weight(j))
    // where k is the last intervals compatible with i.
    // This solution is naively O(n^2) and by using binary search to find k,
    // it can be improved to O(nlogn)

    // My another thought is that if we define f(i) as the largest weight we can get
    // if we are scheduling the meeting i, thus every time we need to find the
    // last compatible interval k and updates f(i) with interval 1 to k, now this is
    // O(n^2), which is slower..
    public int schedule(WeightedInterval[] a){
        int[] dp = new int[a.length];
        dp[0] = a[0].weight;
        for(int i=1;i<a.length;i++){
            int prev = binarySearch(a, i, a[i].start);
            int prevval = prev>=0?dp[prev]:0;
            dp[i] = Math.max(dp[i-1], prevval+a[i].weight);
        }
        return dp[a.length-1];
    }

    // return the last Interval which has smaller end time than tar.
    // hi exclusive.
    private int binarySearch(WeightedInterval[] a, int hi, int tar){
        int lo = 0;
        while(lo<hi){
            int mid = (lo+hi)/2;
            // or (hi-lo)/2 + lo to avoid overflow...
            if(a[mid].end>=tar) hi = mid;
            else lo = mid+1;
        }
        // notice that now a[lo] will be the first interval with
        // end time larger than tar.
        return lo-1;
    }


    static class WeightedInterval extends Interval {
        int weight;
        public WeightedInterval(int begin, int end, int weight){
            this.start = begin;
            this.end = end;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        WeightedInterval[] a = new WeightedInterval[]{
                new WeightedInterval(0,3,2),
                new WeightedInterval(3,6,10),
                new WeightedInterval(4,7,7),
                new WeightedInterval(8,12,2)
        };
        System.out.println(s.schedule(a));
    }
}
