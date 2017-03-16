package ThreeSumClosest_16;

import java.util.Arrays;

/**
 * Given an array S of n integers, find three integers in S such that
 * the sum is closest to a given number, target.
 * Return the sum of the three integers.
 * You may assume that each input would have exactly one solution.
 */
public class Solution
{
    public static int threeSumClosest(int[] nums, int target)
    {
        /* will use the likely method of 3Sum */
        Arrays.sort(nums);
        int minDiff = Integer.MAX_VALUE;
        int currentSum = 0;
        for(int i=0;i<nums.length-2;) // scanning pointer
        {
            if(i!=0&&nums[i]==nums[i-1])// optimization. skip the same element
            {
                i++;
                continue;
            }
            for(int lo=i+1,hi=nums.length-1;lo<hi;)
            {
                int sum = nums[lo]+nums[hi]+nums[i];
                int diff = sum-target;
                if(diff==0) return target; //optimization
                int absDiff = Math.abs(diff);
                if(absDiff<minDiff)
                {
                    currentSum = sum;
                    minDiff = absDiff;
                }
                if(diff>0) hi--;    // the sum is too large
                else lo++;          // sum too small
            }
            i++;
        }
        return currentSum;
    }
}
