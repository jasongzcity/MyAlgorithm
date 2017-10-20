package contest.DegreeOfAnArray_697;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a non-empty array of non-negative integers nums,
 * the degree of this array is defined as the maximum frequency of any one of its elements.
 *
 * Your task is to find the smallest possible length of a (contiguous)
 * subarray of nums, that has the same degree as nums.
 *
 * Example 1:
 * Input: [1, 2, 2, 3, 1]
 * Output: 2
 * Explanation:
 * The input array has a degree of 2 because both elements 1 and 2 appear twice.
 * Of the subarrays that have the same degree:
 * [1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
 * The shortest length is 2. So return 2.
 *
 * Example 2:
 * Input: [1,2,2,3,1,4,2]
 * Output: 6
 *
 * Note:
 * 1. nums.length will be between 1 and 50,000.
 * 2. nums[i] will be an integer between 0 and 49,999.
 */
public class Solution {
    // ACC Beats 97%
    public int findShortestSubArray(int[] nums) {
        int[] first = new int[50000], count = new int[50000];
        int counts = 0, min = nums.length;
        for(int i=0;i<nums.length;i++){
            if(first[nums[i]]==0){
                // first met
                first[nums[i]] = i+1;
            }
            ++count[nums[i]];
            if(count[nums[i]]>counts){
                counts = count[nums[i]];
                min = i-first[nums[i]]+2;
            }else if(count[nums[i]]==counts){
                min = Math.min(min, i-first[nums[i]]+2);
            }
        }
        return min;
    }
}
