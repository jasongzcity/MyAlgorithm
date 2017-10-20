package ContinuousArray_525;

import java.util.Arrays;

/**
 * Given a binary array, find the maximum length of a contiguous subarray
 * with equal number of 0 and 1.
 *
 * Example 1:
 * Input: [0,1]
 * Output: 2
 * Explanation: [0, 1] is the longest contiguous subarray with equal number of 0 and 1.
 *
 * Example 2:
 * Input: [0,1,0]
 * Output: 2
 * Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray
 * with equal number of 0 and 1.
 *
 * Note: The length of the given binary array will not exceed 50,000.
 */
public class Solution {
    public int findMaxLength(int[] nums) {
        int[] ones = new int[nums.length];
        int[] zeroes = new int[nums.length];
        Arrays.fill(ones,Integer.MAX_VALUE);
        Arrays.fill(zeroes,Integer.MAX_VALUE);

        int balance = 0, maxlen = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==0) balance--;
            else balance++;
            if(balance==0) maxlen = i+1;
            else if(balance<0){
                // more zeroes.
                if(zeroes[-balance-1]!=Integer.MAX_VALUE){
                    maxlen = Math.max(maxlen, i-zeroes[-balance-1]);
                }else{
                    zeroes[-balance-1] = i;
                }
            }else{
                // balance > 0
                if(ones[balance-1]!=Integer.MAX_VALUE){
                    maxlen = Math.max(maxlen, i-ones[balance-1]);
                }else{
                    ones[balance-1] = i;
                }
            }
        }
        return maxlen;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findMaxLength(new int[]{0,0,1}));
    }

}
