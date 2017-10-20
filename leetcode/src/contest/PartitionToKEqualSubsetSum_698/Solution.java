package contest.PartitionToKEqualSubsetSum_698;

import java.util.Arrays;

/**
 * Given an array of integers nums and a positive integer k,
 * find whether it's possible to divide this array into k non-empty subsets
 * whose sums are all equal.
 *
 * Example 1:
 * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 * Output: True
 * Explanation: It's possible to divide it into 4 subsets (5),
 * (1, 4), (2,3), (2,3) with equal sums.
 *
 * Note:
 * 1 <= k <= len(nums) <= 16.
 * 0 < nums[i] < 10000.
 */
public class Solution {
    // TLE..
    public boolean canPartitionKSubsets2(int[] nums, int k) {
        Arrays.sort(nums);
        int sum = 0;
        for(int i:nums) sum+=i;
        if(sum%k!=0) return false;
        int tar = sum/k;
//        for(int i=0;i<nums.length;i++){
//            if(nums[i]!=0){
//                if(!dfs(nums,nums[i],tar)) return false;
//                else k--;
//            }
//        }
        return dfs2(nums,0,tar,k);
    }

    private boolean dfs2(int[] a,int cur,int tar,int k){
        if(cur==tar){
            if(k==1) return true;
            return dfs2(a,0,tar, k-1); // reset
        }
        for(int i=0;i<a.length&&cur+a[i]<=tar;i++){
            if(a[i]==0) continue;
            int back = a[i];
            a[i] = 0;
            if(dfs2(a,cur+back,tar,k)) return true;
            else a[i] = back;
        }
        return false;
    }

    public boolean canPartitionKSubsets(int[] nums, int k){
        Arrays.sort(nums);
        int sum = 0;
        for(int i:nums) sum+=i;
        if(sum%k!=0) return false;
        int tar = sum/k;
        for(int i=nums.length-1;i>-1;i--){
            if(nums[i]!=tar){
                if(!dfs(nums,nums[i],tar,i-1)) return false;
            }
        }
        return true;
    }

    private boolean dfs(int[] a, int cur, int tar, int begin){
        if(tar==cur) return true;
        for(int i=begin;i>-1;i--){
            if(cur+a[i]<=tar){
                if(dfs(a,cur+a[i],tar,i-1)){
                    a[i] = tar;
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.canPartitionKSubsets(new int[]{4,3,2,3,5,2,1},4));
        System.out.println(s.canPartitionKSubsets(new int[]{114,96,18,190,207,111,73,471,
                99,20,1037,700,295,101,39,649,4},4));
    }
}
