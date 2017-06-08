package MissingNumber_268;

import java.util.Arrays;

/**
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n,
 * find the one that is missing from the array.
 *
 * For example,
 * Given nums = [0, 1, 3] return 2.
 *
 * Note:
 * Your algorithm should run in linear runtime complexity.
 * Could you implement it using only constant extra space complexity?
 */
public class Solution {
    public int missingNumber(int[] nums) {
        int length = nums.length,properSum = (length*(length+1))>>1;
        for(int i:nums) properSum-=i;
        return properSum;
    }

    // bit manipulation, smart!
    public int missingNumber2(int[] nums){
        int result = nums.length;
        for(int i=0;i<nums.length;i++){
            result ^= i;
            result ^= nums[i];
        }
        return result;
    }

    // Sorted circumstances
    public int missingNumber3(int[] nums){
        Arrays.sort(nums);
        int lo = 0,hi = nums.length,mid;
        while(lo<hi){
            mid = (lo+hi)>>1;
            if(nums[mid]>mid) hi = mid;
            else lo = mid+1;
        }
        return lo;
    }
}
