package ShortestUnsortedContinuousArray_581;

import java.util.Arrays;

/**
 * Given an integer array, you need to find one continuous subarray
 * that if you only sort this subarray in ascending order,
 * then the whole array will be sorted in ascending order, too.
 *
 * You need to find the shortest such subarray and output its length.
 *
 * Example 1:
 * Input: [2, 6, 4, 8, 10, 9, 15]
 * Output: 5
 * Explanation: You need to sort [6, 4, 8, 10, 9]
 * in ascending order to make the whole array sorted in ascending order.
 *
 * Note:
 * Then length of the input array is in range [1, 10,000].
 * The input array may contain duplicates, so ascending order here means <=.
 */
public class Solution {
    // Note: I have met this at microsoft campus interview
    // This is the solution I have found out during the interview.
    // two-pass solution
    public int findUnsortedSubarray(int[] nums) {
        if(nums.length==0) return 0;
        int min = Integer.MAX_VALUE, max = nums[0], right = -1, left = -1;
        for(int i=1;i<nums.length;i++){
            if(nums[i]<max){
                right = i;
                min = Math.min(min,nums[i]);
            }
            max = Math.max(max,nums[i]);
        }
        if(right==-1) return 0; // the array is sorted.
        int i=0;
        while(nums[i]<=min) i++;
        return right-i+1;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findUnsortedSubarray3(new int[]{2,6,4,8,10,9,15}));
    }

    // there must exist other solutions
    // the same two-pass
    public int findUnsortedSubarray2(int[] nums){
        if(nums.length==0) return 0;
        int left = -1, right = -1, min = nums[nums.length-1], max = nums[0];
        for(int i=1;i<nums.length;i++){
            if(nums[i]<max){
                right = i;
            }
            max = Math.max(max,nums[i]);
        }
        if(right==-1) return 0;
        for(int i=nums.length-2;i>-1;i--){
            if(nums[i]>min){
                left = i;
            }
            min = Math.min(min,nums[i]);
        }
        return right-left+1;
    }

    // sorting
    // I think this is the smartest actually.
    public int findUnsortedSubarray3(int[] nums) {
        if (nums.length == 0) return 0;
        int[] copy = nums.clone();
        Arrays.sort(copy);
        int left = -1, right = -1;
        for (int i = 0; i < nums.length; i++) {
            if (copy[i] != nums[i]) {
                left = i;
                break;
            }
        }
        if (left == -1) return 0;
        for (int i = nums.length - 1; i > -1; i--) {
            if (nums[i] != copy[i]) {
                right = i;
                break;
            }
        }
        return right - left + 1;
    }

    // using stack.O(n)
    // It's the same to two-pass solution above.
}
