package SummaryRanges_228;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Given a sorted integer array without duplicates,
 * return the summary of its ranges.
 *
 * For example, given [0,1,2,4,5,7], return ["0->2","4->5","7"].
 */
public class Solution {
    public List<String> summaryRanges(int[] nums) {
        if(nums.length==0) return Collections.emptyList();
        List<String> rs = new ArrayList<>(nums.length);
        int begin = 0,end = 0,prev = nums[0];
        for(int i=1;i<nums.length;i++){
            if(nums[i]==nums[i-1]+1) end++;
            else{
                if(end>begin) rs.add(nums[begin]+"->"+nums[end]);
                else rs.add(String.valueOf(nums[begin]));
                begin = end = i;
            }
        }
        if(end>begin) rs.add(nums[begin]+"->"+nums[end]);
        else rs.add(String.valueOf(nums[begin]));
        return rs;
    }
}
