package MaximumSumOf3NonOverlappingSubarray;

import java.util.Arrays;

/**
 * In a given array nums of positive integers,
 * find three non-overlapping subarrays with maximum sum.
 *
 * Each subarray will be of size k,
 * and we want to maximize the sum of all 3*k entries.
 *
 * Return the result as a list of indices representing
 * the starting position of each interval (0-indexed).
 * If there are multiple answers, return the lexicographically smallest one.
 *
 * Example:
 * Input: [1,2,1,2,6,7,5,1], 2
 * Output: [0, 3, 5]
 * Explanation: Subarrays [1, 2], [2, 6], [7, 5]
 * correspond to the starting indices [0, 3, 5].
 * We could have also taken [2, 1],
 * but an answer of [1, 3, 5] would be lexicographically larger.
 *
 * Note:
 * nums.length will be between 1 and 20000.
 * nums[i] will be between 1 and 65535.
 * k will be between 1 and floor(nums.length / 3).
 */
public class Solution {

    // redo optimal solution
    public int[] maxSumOfThreeSubarrays(int[] a, int k){
        int n = a.length;
        int[] rs = new int[3], leftEnd = new int[n],
                rightBegin = new int[n], sum = new int[n+1];
        for(int i=0;i<a.length;i++) sum[i+1] = sum[i]+a[i];
        // use leftEnd records down the current maximum sum on the left hand side
        leftEnd[k-1] = k-1;
        for(int i=k,tm=sum[k];i<a.length;i++){
            if(sum[i+1]-sum[i-k+1]>tm){
                leftEnd[i] = i;
                tm = sum[i+1]-sum[i-k+1];
            }else leftEnd[i] = leftEnd[i-1];
        }
        rightBegin[n-k] = n-k;
        for(int i=n-k-1,tm=sum[n]-sum[n-k];i>=0;i--){
            if(sum[i+k]-sum[i]>=tm){
                rightBegin[i] = i;
                tm = sum[i+k]-sum[i];
            }else rightBegin[i] = rightBegin[i+1];
        }
        // scan to find the middle point
        for(int i=k,tm=0;i+2*k<=n;i++){
            int l = leftEnd[i-1], r = rightBegin[i+k];
            if(sum[l+1]-sum[l+1-k]+sum[r+k]-sum[r]+sum[i+k]-sum[i]>tm){
                rs[0] = l-k+1;
                rs[1] = i;
                rs[2] = r;
                tm = sum[l+1]-sum[l+1-k]+sum[r+k]-sum[r]+sum[i+k]-sum[i];
            }
        }
        return rs;
    }

    // optimal solution:
    public int[] maxSumOfThreeSubarrays2(int[] nums, int k) {
        int n = nums.length, maxsum = 0;
        int[] sum = new int[n+1], posLeft = new int[n],
                posRight = new int[n], ans = new int[3];
        for (int i = 0; i < n; i++) sum[i+1] = sum[i]+nums[i];
        // DP for starting index of the left max sum interval
        for (int i = k, tot = sum[k]-sum[0]; i < n; i++) {
            if (sum[i+1]-sum[i+1-k] > tot) {
                posLeft[i] = i+1-k;
                tot = sum[i+1]-sum[i+1-k];
            }
            else
                posLeft[i] = posLeft[i-1];
        }
        // DP for starting index of the right max sum interval
        // caution: the condition is ">= tot" for right interval,
        // and "> tot" for left interval
        posRight[n-k] = n-k;
        for (int i = n-k-1, tot = sum[n]-sum[n-k]; i >= 0; i--) {
            if (sum[i+k]-sum[i] >= tot) {
                posRight[i] = i;
                tot = sum[i+k]-sum[i];
            }
            else
                posRight[i] = posRight[i+1];
        }
        // test all possible middle interval
        for (int i = k; i <= n-2*k; i++) {
            int l = posLeft[i-1], r = posRight[i+k];
            int tot = (sum[i+k]-sum[i]) + (sum[l+k]-sum[l]) + (sum[r+k]-sum[r]);
            if (tot > maxsum) {
                maxsum = tot;
                ans[0] = l; ans[1] = i; ans[2] = r;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(Arrays.toString(s.maxSumOfThreeSubarrays(
                new int[]{17,9,3,2,7,10,20,1,13,4,5,16,4,1,17,6,4,19,8,3},4
        )));
    }
}
