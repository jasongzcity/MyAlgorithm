package FirstMissingPositive_41;

/**
 * Given an unsorted integer array, find the first missing positive integer.
 *
 * For example,
 * Given [1,2,0] return 3,
 * and [3,4,-1,1] return 2.
 *
 * Your algorithm should run in O(n) time and uses constant space.
 */
public class Solution {

    // Solution below is inspired by the most voted solution on leetcode
    // scan the array and put a[i] to a[a[i]]
    public static int firstMissingPositive(int[] nums) {
        if(nums.length==0) return 1;
        for (int i = 0; i < nums.length; ) {
            // nums[i]!=nums[nums[i]-1] to avoid endless loop
            if(nums[i]>0&&nums[i]<=nums.length&&nums[i]!=nums[nums[i]-1])
                swap(nums,nums[i]-1,i);
            else
                i++;
        }

        int i = 0;
        for (; i < nums.length; i++) {
            if(nums[i]!=i+1) break;
        }
        return i+1;
    }

    private static void swap(int[] a,int i,int j){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-1,-2,0,1,8,9,7,-4,2,5,-9,10};
        System.out.println(firstMissingPositive(nums));
    }
}
