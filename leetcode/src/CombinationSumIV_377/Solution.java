package CombinationSumIV_377;

import java.util.Arrays;

/**
 * Given an integer array with all positive numbers and no duplicates,
 * find the number of possible combinations that add up to a positive integer target.
 *
 * Example:
 *
 * nums = [1, 2, 3]
 * target = 4
 *
 * The possible combination ways are:
 * (1, 1, 1, 1)
 * (1, 1, 2)
 * (1, 2, 1)
 * (1, 3)
 * (2, 1, 1)
 * (2, 2)
 * (3, 1)
 *
 * Note that different sequences are counted as different combinations.
 * Therefore the output is 7.
 *
 * Follow up:
 * What if negative numbers are allowed in the given array?
 * How does it change the problem?
 * What limitation we need to add to the question to allow negative numbers?
 */
public class Solution
{
    public static int combinationSum4(int[] nums, int target) {
        int[] comb = new int[target+1];
        comb[0] = 1;
        for (int i = 0; i < comb.length; i++)
            for (int j = 0; j < nums.length; j++)
                if(i-nums[j]>=0)
                    comb[i] += comb[i-nums[j]];
        return comb[target];
    }

    private static void getComb(int[] a,int target,int[] counter){
        if(target==0){
            counter[0]++;
            return;
        }
        for(int i=0;i<a.length&&a[i]<=target;i++){
            getComb(a,target-a[i],counter);
        }
    }

    // Better recursive method but still slow.
    // However we can see the clue for a DP method:
    // the combinations of i is the sum of
    // combinations of (i-a[0],i-a[1],...,i-a[length-1](when i-a[n]>=0))
    // now we need to figure out the base condition.
    private static int getComb2(int[] a,int target){
        if(target==0) return 1;
        int rs = 0;
        for(int i=0;i<a.length&&a[i]<=target;i++){
            rs += getComb2(a,target-a[i]);
        }
        return rs;
    }

    // A Top-down DP
    // It looks like the getComb2 method but
    // it uses an array to store the result so
    // avoid recalculations.
    private static int getComb4(int[] nums,int target,int[] comb){
        if(comb[target]!=-1) return comb[target]; // if already calculated

        comb[0] = 1;
        int rs = 0;
        for (int i = 0; i < nums.length&&nums[i]<=target; i++) {
            rs += getComb4(nums,target-nums[i],comb);
        }
        comb[target] = rs;
        return comb[target];
    }

    public static int topdownCaller(int[] nums,int target){
        int[] comb = new int[target+1];
        Arrays.fill(comb,-1);
        comb[0] = 1;
        getComb4(nums,target,comb);
        return comb[target];
    }

    // Use an array(comb) to store the results of the calculations
    // It's a bottom-up DP method.
    // From the smallest to the largest.
    private static void getComb3(int[] nums,int target,int[] comb){
        comb[0] = 1;    // Trick. All elements will have one combination the nums array
                        // have the number(i-nums[j]==0).
        int i = 1;
        while(i<=target){
            comb[i] = 0;
            for (int j = 0; j < nums.length; j++)
                if(i-nums[j]>=0)
                    comb[i] += comb[i-nums[j]];
            ++i;
        }
        System.out.println(comb[target]);
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3};
        System.out.println(combinationSum4(nums,32));
    }
}