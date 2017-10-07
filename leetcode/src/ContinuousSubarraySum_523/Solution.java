package ContinuousSubarraySum_523;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a list of non-negative numbers and a target integer k,
 * write a function to check if the array has a continuous subarray of size
 * at least 2 that sums up to the multiple of k,
 * that is, sums up to n*k where n is also an integer.
 *
 * Example 1:
 * Input: [23, 2, 4, 6, 7],  k=6
 * Output: True
 * Explanation: Because [2, 4] is a continuous subarray of size 2 and sums up to 6.
 *
 * Example 2:
 * Input: [23, 2, 6, 4, 7],  k=6
 * Output: True
 * Explanation: Because [23, 2, 6, 4, 7] is an continuous subarray of size 5
 * and sums up to 42.
 *
 * Note:
 * The length of the array won't exceed 10,000.
 * You may assume the sum of all the numbers is in the range of a signed 32-bit integer.
 */
public class Solution {
    // brute-force: there are N^3 subarrays
    public boolean checkSubarraySum(int[] nums, int k) {
        if(nums.length<=1) return false;
        for(int begin=0;begin<nums.length;begin++){
            for(int end=nums.length-1;end>=0;end--){
                int sum = nums[begin];
                for(int i=begin+1;i<=end;i++){
                    sum+=nums[i];
                    if(sum%k==0) return true;
                }
            }
        }
        return false;
    }

    // brute-force with memoization
    public boolean checkSubarraySum2(int[] nums, int k){
        if(nums.length<=1) return false;
        int[] memo = new int[nums.length];
        memo[0] = nums[0];
        for(int i=1;i<nums.length;i++) memo[i]+=memo[i-1]+nums[i];

        for(int begin=-1;begin<nums.length-2;begin++){
            int base = memo[nums.length-1] - (begin < 0 ? 0 : memo[begin]);
            for(int end=nums.length;end>=begin+3;end--){
                base -= (end >= nums.length ? 0 : nums[end]);
                if((k==0&&base==0)||(k!=0&&base%k==0)) return true;
            }
        }
        return false;
    }

    // even easier solution with map
    public boolean checkSubarraySum3(int[] nums, int k){
        if(nums.length<2) return false;
        Map<Integer,Integer> map = new HashMap<>();
        map.put(0,-1);  // deal with corner cases...
        int sum = 0;
        for(int i=0;i<nums.length;i++){
            sum += nums[i];
            if(k!=0) sum %= k;
            Integer prev = map.get(sum);
            if(prev!=null){
                if(i-prev>1) return true;
            }else{
                map.put(sum,i);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.checkSubarraySum3(new int[]{1,2,3,4,0},0));
        System.out.println(s.checkSubarraySum3(new int[]{0,1,1,0},2));
        System.out.println(s.checkSubarraySum3(new int[]{23,2,6,4,7},0));
        System.out.println(s.checkSubarraySum3(new int[]{1,2,3},6));
    }
}
