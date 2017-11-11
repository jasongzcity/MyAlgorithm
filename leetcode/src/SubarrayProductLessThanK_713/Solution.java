package SubarrayProductLessThanK_713;

/**
 * Your are given an array of positive integers nums.
 *
 * Count and print the number of (contiguous) subarrays
 * where the product of all the elements in the subarray is less than k.
 *
 * Example 1:
 * Input: nums = [10, 5, 2, 6], k = 100
 * Output: 8
 * Explanation: The 8 subarrays that have product less than 100 are:
 * [10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6].
 * Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
 *
 * Note:
 * 0 < nums.length <= 50000.
 * 0 < nums[i] < 1000.
 * 0 <= k < 10^6.
 */
public class Solution {

    // thought from leetcode solution:
    // it's another slide window problem.
    // At first I was doubtful whether the number of subarray
    // can be calculated from the sliding window..
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if(k<=1) return 0;
        int left = 0,right = 0,prod = 1,count = 0;
        while(right<nums.length){
            // the window is [left, right]
            prod*=nums[right];
            while(prod>=k) prod/=nums[left++];
            count+=right++-left+1;
        }
        return count;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.numSubarrayProductLessThanK(new int[]{10,5,2,8}, 100));
    }
}
