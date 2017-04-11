package MaximunSubArray_52;

/**
 * Find the contiguous subarray within an array (containing at least one number)
 * which has the largest sum.
 *
 * For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
 * the contiguous subarray [4,-1,2,1] has the largest sum = 6.
 *
 * More practice:
 * If you have figured out the O(n) solution,
 * try coding another solution using the divide and conquer approach,
 * which is more subtle.
 */
public class Solution {
    public static int maxSubArray(int[] nums) {
//        return maxSubArrayDNC(nums,0,nums.length-1);
        return maxSubArrayDP(nums);
    }

    // divide and conquer
    // thought: in the range of [lo,hi],
    // the max subarray must either totally in the left or right,
    // or across the middle point.
    public static int maxSubArrayDNC(int[] nums,int lo,int hi){
        if(lo==hi) return nums[lo];
        int mid = (lo+hi)>>1;
        int leftSum = maxSubArrayDNC(nums,lo,mid);
        int rightSum = maxSubArrayDNC(nums,mid+1,hi);
        int crossSum = getCrossSum(nums,lo,hi,mid);
        return leftSum>rightSum?(leftSum>crossSum?leftSum:crossSum):
                (rightSum>crossSum?rightSum:crossSum);
    }

    private static int getCrossSum(int[] a,int lo,int hi,int mid){
        int maxLeft = Integer.MIN_VALUE,maxRight = Integer.MIN_VALUE,sum = 0;
        for (int i = mid; i >= lo; i--) {
            sum += a[i];
            if(sum>maxLeft) maxLeft = sum;
        }
        sum = 0;
        for (int i = mid+1; i < hi+1; i++) {
            sum += a[i];
            if(sum>maxRight) maxRight = sum;
        }
        return maxLeft+maxRight;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(maxSubArray(nums));
    }

    public static int maxSubArrayDP(int[] nums){
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = nums[i]+(dp[i-1]>0?dp[i-1]:0);
            if(dp[i]>max) max = dp[i];
        }
        return max;
    }
}