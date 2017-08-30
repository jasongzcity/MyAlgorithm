package MaximumDistanceInArray_624;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Given m arrays, and each array is sorted in ascending order.
 * Now you can pick up two integers from two different arrays
 * (each array picks one) and calculate the distance.
 * We define the distance between two integers a and b
 * to be their absolute difference |a-b|. Your task is to find the maximum distance.
 *
 * Example 1:
 * Input:
 *   [[1,2,3],
 *    [4,5],
 *    [1,2,3]]
 * Output: 4
 * Explanation:
 * One way to reach the maximum distance 4 is to pick 1
 * in the first or third array and pick 5 in the second array.
 * Note:
 * Each given array will have at least 1 number.
 * There will be at least two non-empty arrays.
 * The total number of the integers in
 * all the m arrays will be in the range of [2, 10000].
 * The integers in the m arrays will be in the range of [-10000, 10000].
 */
public class Solution {
    // O(m^2) naive solution
    // TLE..
    public int maxDistance2(List<List<Integer>> arrays) {
        List<Integer> low = new ArrayList<>(arrays.size()),
                high = new ArrayList<>(arrays.size());
        for(List<Integer> l:arrays){
            low.add(l.get(0));
            high.add(l.get(l.size()-1));
        }
        int max = 0;
        for(int i=0;i<low.size();i++){
            for(int j=0;j<high.size();j++){
                if(i!=j){
                    max = Math.max(Math.abs(high.get(j)-low.get(i)),max);
                }
            }
        }
        return max;
    }

    // Use two priority queues
    public int maxDistance(List<List<Integer>> arrays){
        PriorityQueue<List<Integer>> lows = new PriorityQueue<>(new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> l1, List<Integer> l2) {
                return l1.get(0)-l2.get(0);
            }
        }), highs = new PriorityQueue<>(new Comparator<List<Integer>>() {
                @Override
                public int compare(List<Integer> l1, List<Integer> l2) {
                    return l2.get(l2.size()-1)-l1.get(l1.size()-1);
                }
            });
        for(List<Integer> l:arrays){
            lows.add(l);
            highs.add(l);
        }
        List<Integer> lowest = lows.peek(),highest = highs.peek();
        if(lowest!=highest) return highest.get(highest.size()-1)-lowest.get(0);
        else{
            lows.poll();
            highs.poll();
            List<Integer> sHighest = highs.peek(),sLowest = lows.peek();
            return Math.max(sHighest.get(sHighest.size()-1)-lowest.get(0),
                    highest.get(highest.size()-1)-sLowest.get(0));
        }
    }

    // optimization:
    // since the range is given we could use bucket sort the lists.
}
