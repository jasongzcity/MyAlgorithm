package MajorityElementsII_229;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an integer array of size n, find all elements that
 * appear more than ⌊ n/3 ⌋ times.
 * The algorithm should run in linear time and in O(1) space.
 */
public class Solution {
    // same method with majority element I
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> rs = new ArrayList<>(6);
        int cand1 = 0,cand2 = 1,count1 = 0,count2 = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==cand1) ++count1;
            else if(nums[i]==cand2) ++count2;
            else if(count1==0){ cand1 = nums[i]; count1 = 1; }
            else if(count2==0){ cand2 = nums[i]; count2 = 1; }
            else { --count1; --count2; }
        }
        count1 = count2 = 0;
        for(int i:nums){
            if(i==cand1) ++count1;
            else if(i==cand2) ++count2;
        }
        if(count1>nums.length/3) rs.add(cand1);
        if(count2>nums.length/3) rs.add(cand2);
        return rs;
    }
}
