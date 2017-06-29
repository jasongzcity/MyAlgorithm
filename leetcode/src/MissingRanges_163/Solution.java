package MissingRanges_163;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a sorted integer array where the range of elements
 * are in the inclusive range [lower, upper], return its missing ranges.
 *
 * For example, given [0, 1, 3, 50, 75], lower = 0 and upper = 99,
 * return ["2", "4->49", "51->74", "76->99"].
 */
public class Solution {
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        int pointer = 0,lowBound = lower;
        List<String> rs = new ArrayList<>();
        while(pointer!=nums.length){
            if(lowBound<nums[pointer]){
                if(lowBound<nums[pointer]-1) rs.add(lowBound+"->"+(nums[pointer]-1));
                else rs.add(lowBound+"");
            }
            lowBound = nums[pointer++]+1;
            if(lowBound==Integer.MIN_VALUE) return rs; // deal with overflow
        }
        if(lowBound==upper) rs.add(upper+"");
        else if(lowBound<upper) rs.add(lowBound+"->"+upper);
        return rs;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = new int[]{8,10,12,50,100};
        List<String> l = s.findMissingRanges(a,0,100);
        System.out.println(l.toString());
    }
}
