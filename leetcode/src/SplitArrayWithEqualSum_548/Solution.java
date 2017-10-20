package SplitArrayWithEqualSum_548;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given an array with n integers,
 * you need to find if there are triplets (i, j, k) which satisfies following conditions:
 *
 * 1. 0 < i, i + 1 < j, j + 1 < k < n - 1
 * 2. Sum of subarrays (0, i - 1), (i + 1, j - 1), (j + 1, k - 1) and (k + 1, n - 1)
 *    should be equal.
 *
 * where we define that subarray (L, R) represents a slice of the
 * original array starting from the element indexed L to the element indexed R.
 *
 * Example:
 * Input: [1,2,1,2,1,2,1]
 * Output: True
 * Explanation:
 * i = 1, j = 3, k = 5.
 * sum(0, i - 1) = sum(0, 0) = 1
 * sum(i + 1, j - 1) = sum(2, 2) = 1
 * sum(j + 1, k - 1) = sum(4, 4) = 1
 * sum(k + 1, n - 1) = sum(6, 6) = 1
 * Note:
 * 1 <= n <= 2000.
 * Elements in the given array will be in range [-1,000,000, 1,000,000].
 */
public class Solution {
    // brute-force thought:
    // O(n^2) For every index we keep a sum. Each time we reach an index, we looked
    // back in the previous sum to find the matches..
    // we use map to keep track of these
    // The sum can be too large we can't use an array to map it...

    // The solution says O(N^2) is acceptable...
    public boolean splitArray(int[] nums) {
        // assume not empty
//        Map<Integer, Integer> map = new HashMap<>();    // <Sum-Times> pair
        int[] sums = new int[nums.length];
        sums[0] = nums[0];
        for(int i=1;i<nums.length;i++){
            sums[i] = sums[i-1]+nums[i];
        }
        // (____0____0____0_____)
        //           ^
        //           |
        //         pivot
        for(int pivot = 3; pivot < nums.length-3; pivot++){
            Set<Integer> set = new HashSet<>(); // each set for each pivot
            for(int leftp = 1; leftp < pivot-1; leftp++){
                if(sums[leftp-1] == sums[pivot-1] - sums[leftp]) set.add(sums[leftp-1]);
            }
            for(int rightp = pivot + 2; rightp < nums.length - 1; rightp++){
                if(sums[rightp-1] - sums[pivot] == sums[nums.length-1] - sums[rightp]
                        && set.contains(sums[rightp-1] - sums[pivot]))
                    return true;
            }
        }
        return false;
    }

    // could do DFS
    // Run a DFS with memoization
    // thus we can avoid using set & hashing
    public boolean splitArray2(int[] nums) {
        int[] memo = new int[nums.length];
        memo[0] = nums[0];
        for(int i=1;i<nums.length;i++) memo[i] = memo[i-1]+nums[i];
        for(int end = 0; end < nums.length-6; end++){
            if(end==0||nums[end]!=0||nums[end-1]!=0)
                if(findTarget(end+1, memo[end], memo, nums,1))
                    return true;
        }

        return false;
    }

    // notice begin is exclusive in the next sum!
    private boolean findTarget(int begin, int tar, int[] memo, int[] nums, int depth){
        if(begin>=memo.length) return false;
        if(depth==3){
            // we have found 3 subsets, we only need to check the last part..
            return memo[memo.length-1]-memo[begin]==tar;
        }
        for(int i=begin+1; i<memo.length; i++){
            if(i==begin+1||nums[i]!=0||nums[i-1]!=0)
                if(memo[i]-memo[begin]==tar &&
                        findTarget(i+1, tar, memo, nums, depth+1))
                    return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.splitArray2(new int[]{1,2,1,2,1,2,1,2,1}));
    }

    // optimal solution
    public boolean splitArray3(int[] nums) {
        int len = nums.length;
        if (len < 7) {
            return false;
        }
        int sum = 0;
        int max = nums[0], min = nums[0];
        for (int num : nums) {
            sum += num;
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        int maxTarget = (sum - 3 * min) / 4;
        int minTarget = (sum - 3 * max) / 4;
        int accum = 0;
        for (int i = 1; i < len - 5; i++) {
            accum += nums[i - 1];
            if (accum <= maxTarget && accum >= minTarget && dfs(nums, i + 1, accum, sum - accum - nums[i], 1)) {
                return true;
            }
        }
        return false;
    }
    private boolean dfs(int[] nums, int start, int target, int remain, int numOfSlices) {
        if (numOfSlices == 3) {
            return remain == target;
        }
        int len = nums.length;
        int accum = 0;
        int upper = len - 5 + numOfSlices * 2;
        for (int i = start + 1; i < upper; i++) {
            accum += nums[i - 1];
            if (accum == target && dfs(nums, i + 1, target, remain - accum - nums[i], numOfSlices + 1)) {
                return true;
            }
        }
        return false;
    }
}
