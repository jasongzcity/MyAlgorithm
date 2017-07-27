package LongestIncreasingSubsequence_300;

/**
 * Given an unsorted array of integers,
 * find the length of longest increasing subsequence.
 *
 * For example,
 * Given [10, 9, 2, 5, 3, 7, 101, 18],
 * The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4.
 * Note that there may be more than one LIS combination,
 * it is only necessary for you to return the length.
 *
 * Your algorithm should run in O(n^2) complexity.
 *
 * Follow up: Could you improve it to O(n log n) time complexity?
 */
public class Solution {
    // O(n^2) intuitive solution, accepted.
    // I have noticed some overlapped subproblems in this problem.
    public int lengthOfLIS2(int[] nums) {
        if(nums.length==0) return 0;
        int[] dp = new int[nums.length];
        int longest = 0;
        for(int i=0;i<nums.length;i++){
            if(dp[i]==0){
                subSequence(nums,dp,i);
                if(dp[i]>longest) longest = dp[i];
            }
        }
        return longest;
    }

    private void subSequence(int[] nums,int[] dp,int begin){
        int val = nums[begin],len = 0;
        for(int i=begin+1;i+len<nums.length;i++){
            if(nums[i]>val){
                if(dp[i]==0) subSequence(nums,dp,i);
                if(dp[i]>len) len = dp[i];
            }
        }
        dp[begin] = len+1;
    }

    // O(nlogn) solution
    // iterative.
    public int lengthOfLIS(int[] a){
        if(a.length==0) return 0;
        int[] stack = new int[a.length]; // array that acting like a stack
        int p = 0;
        stack[0] = a[0];
        for(int i=1;i<a.length;i++){
            if(a[i]>stack[p]) stack[++p] = a[i]; // push to the stack
            else{
                // use binary search to find a position to add a[i] in the stack
                int lo = 0,hi = p+1; // hi exclusive
                while(lo<hi){
                    int mid = (lo+hi)/2;
                    if(stack[mid]<a[i]) lo = mid+1;
                    else hi = mid;
                }
                stack[lo] = a[i];
            }
        }
        return p+1;
    }
}
