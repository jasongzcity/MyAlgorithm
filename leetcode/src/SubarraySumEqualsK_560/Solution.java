package SubarraySumEqualsK_560;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers and an integer k,
 * you need to find the total number of continuous subarrays whose sum equals to k.
 *
 * Example 1:
 * Input:nums = [1,1,1], k = 2
 * Output: 2
 * Note:
 * The length of the array is in range [1, 20,000].
 * The range of numbers in the array is [-1000, 1000]
 * and the range of the integer k is [-1e7, 1e7].
 */
public class Solution {
    // alike continuous subarray sum #523
    // A naive dp thought:
    // the sum of the integers is in range [-2000000, 2000000]
    // we can do this dp. O(n*range) time and O(range) space which is stupid...
    // do it by using map
    public int subarraySum(int[] nums, int k) {
        if(nums.length==0) return 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0,1);
        int sum = 0, subs = 0;
        for(int i:nums){
            sum += i;
            subs += map.getOrDefault(sum-k,0);
            map.put(sum, map.getOrDefault(sum,0)+1);
        }
        return subs;
    }

    // hint: divide & conquer would make it faster..
    // can follow up later
}
