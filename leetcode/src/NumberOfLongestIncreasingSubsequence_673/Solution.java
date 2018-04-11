package NumberOfLongestIncreasingSubsequence_673;

import java.util.Arrays;

/**
 * Given an unsorted array of integers, find the number of longest increasing subsequence.
 *
 * Example 1:
 * Input: [1,3,5,4,7]
 * Output: 2
 * Explanation: The two longest increasing subsequence are [1, 3, 4, 7] and [1, 3, 5, 7].
 *
 * Example 2:
 * Input: [2,2,2,2,2]
 * Output: 5
 * Explanation: The length of longest continuous increasing subsequence is 1,
 * and there are 5 subsequences' length is 1, so output 5.
 *
 * Note: Length of the given array will be not exceed 2000 and the answer
 * is guaranteed to be fit in 32-bit signed int.
 */
public class Solution {
    // according to #300 longest increasing subsequence
    // we can use O(N^2) to find out the length of longest increasing subsequence
    // thought: using same method to find the length while keep track of the
    // number
    public int findNumberOfLIS(int[] nums) {
        if(nums.length<2) return nums.length;
        int[] length = new int[nums.length], count = new int[nums.length];
        Arrays.fill(count, 1);
        Arrays.fill(length, 1);
        int maxLen = 1, counts = 0;
        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                if(nums[j]>nums[i]){
                    if(length[i]+1>length[j]){
                        length[j] = length[i]+1;
                        count[j] = count[i];
                    }else if(length[i]+1==length[j]){
                        count[j] += count[i];
                    }
                }
            }
        }

        for(int i=0;i<nums.length;i++){
            if(maxLen==length[i]) counts += count[i];
            else if(maxLen<length[i]){
                maxLen = length[i];
                counts = count[i];
            }
        }
        return counts;
    }

    // faster
    public int findNumberOfLIS2(int[] nums){
        if(nums.length<2) return nums.length;
        int maxlen = 1, num = 0;
        int[] length = new int[nums.length], count = new int[nums.length];
        Arrays.fill(length, 1);
        Arrays.fill(count, 1);
        for(int i=1;i<nums.length;i++){
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]){
                    // try to update the length and count
                    if(length[i]==length[j]+1) count[i] += count[j];
                    else if(length[i]<length[j]+1){
                        length[i] = length[j]+1;
                        count[i] = count[j];
                    }
                }
            }
            if(maxlen==length[i]) num+=count[i];
            else if(maxlen<length[i]){
                maxlen = length[i];
                num = count[i];
            }
        }
        return num;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findNumberOfLIS2(new int[]{1, 3, 5, 4, 7}));
    }
}
