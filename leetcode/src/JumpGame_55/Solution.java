package JumpGame_55;

import java.util.Arrays;

/**
 * Given an array of non-negative integers,
 * you are initially positioned at the first index of the array.
 *
 * Each element in the array represents your maximum jump length at that position.
 *
 * Determine if you are able to reach the last index.
 *
 * For example:
 * A = [2,3,1,1,4], return true.
 * A = [3,2,1,0,4], return false.
 */
public class Solution {
    public static boolean canJump(int[] nums) {
        // array not empty!
        boolean[] flags = new boolean[nums.length]; // false for undetermined, true for unreachable.
        return checkPosition(flags,0,nums);
    }

    // check if current position can reach the last index.
    private static boolean checkPosition(boolean[] flags,int position,int[] a){
        if(a[position]+position>=a.length-1) return true;

        for (int i = 1; i <= a[position]; i++) {
            if(flags[position+i]) continue; // unreachable
            if(checkPosition(flags,position+i,a)){ // reached!
                return true;
            }else{
                flags[position+i] = true; // unreachable, avoid multiple check.
            }
        }
        flags[position] = true; // target unreachable started from this position.
        return false;
    }

    public static void main(String[] args) {
//        int[] nums1 = new int[]{2,3,1,1,4};
//        int[] nums2 = new int[]{3,2,1,0,4};
//        System.out.println(canJump(nums1));
//        System.out.println(canJump(nums2));

        int[] nums3 = new int[10000];
        Arrays.fill(nums3,1);
        System.out.println(canJump2(nums3));
    }

    public static boolean canJump2(int[] nums){
        int reach = 0;
        for(int i=0;i<nums.length&&i<=reach;i++)
            reach = (nums[i]+i)>reach?nums[i]+i:reach;
        return reach>=nums.length-1;
    }
}