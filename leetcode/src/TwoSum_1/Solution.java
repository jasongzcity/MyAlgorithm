package TwoSum_1;

import java.util.*;

/**
 * Given an array of integers,
 * return indices of the two numbers such that they add up to a specific target.
 *
 * You may assume that each input would have exactly one solution,
 * and you may not use the same element twice.
 *
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class Solution {
    // O(n) space map solution
    // naive
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length<<1);
        for(int i=0;i<nums.length;i++){
            Integer in = map.get(target-nums[i]);
            if(in!=null) return new int[]{i, in};
            map.put(nums[i], i);
        }
        return null;
    }

    // two pointer
    // not accepted, should return the origin indices.
    public int[] twoSum2(int[] n, int target){
        Arrays.sort(n);
        int b = 0, e = n.length-1;
        while(true){
            if(n[b]+n[e]<target) b++;
            else if(n[b]+n[e]>target) e--;
            else return new int[]{b, e};
        }
    }
}
