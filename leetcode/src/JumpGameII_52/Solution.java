package JumpGameII_52;

import java.util.Arrays;

/**
 * Given an array of non-negative integers, you are initially positioned at
 * the first index of the array.
 *
 * Each element in the array represents your maximum jump length at that position.
 *
 * Your goal is to reach the last index in the minimum number of jumps.
 *
 * For example:
 * Given array A = [2,3,1,1,4]
 *
 * The minimum number of jumps to reach the last index is 2.
 * (Jump 1 step from index 0 to 1, then 3 steps to the last index.)
 *
 * Note:
 * You can assume that you can always reach the last index.
 */
public class Solution {
    // This will cause stackoverflow when the input array is very large..
    public static int jump(int[] nums) {
        if(nums.length==1) return 0;
        int[] steps = new int[nums.length]; // 0 for undetermined, -1 for unreachable.
        getMinSteps(nums,steps,0);
        return steps[0];
    }

    private static int getMinSteps(int[] nums,int[] steps,int position){
        if(nums[position]+position>=nums.length-1){
            steps[position] = 1;
            return 1;
        }

        int minSteps = Integer.MAX_VALUE;
        for (int i = 1; i <= nums[position]; i++) {
            if(steps[position+i]==-1) continue;
            if(steps[position+i]!=0)
                minSteps = Math.min(steps[position+i],minSteps);
            else
                minSteps = Math.min(getMinSteps(nums,steps,position+i),minSteps); // 0, undetermined.
        }
        steps[position] = minSteps+1;
        return minSteps+1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,4,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,5};
        System.out.println(jump3(nums));
        System.out.println(jump(nums));
    }

    // leetcode most voted, BFS
    // This solution cut indexes into
    // different levels according to their "steps"
    // and use BFS in each level..
    public static int jump2(int[] nums){
        if(nums.length==1) return 0;
        int step=0,position=0,currentMax=0,nextMax=0;
        while(true){   // true because the question promises the last index must be reachable
            ++step;
            while(position<=currentMax){
                nextMax = Math.max(nums[position]+position,nextMax);
                if(nextMax>=nums.length-1) return step;
                ++position;
            }
            currentMax = nextMax; // notice if nextMax=currentMax would cause dead loop
        }
    }

    public static int jump3(int[] nums){
        int steps=0,max=0,currentMax=0;
        for (int i = 0; i < nums.length-1; i++) {
            max = Math.max(nums[i]+i,max);
            if(i==currentMax){  // count step
                ++steps;
                currentMax = max;
            }
        }
        return steps;
    }
}