package MaximumProductSubarray_152;

/**
 * Find the contiguous subarray within an array (containing at least one number)
 * which has the largest product.
 *
 * For example, given the array [2,3,-2,4],
 * the contiguous subarray [2,3] has the largest product = 6.
 */
public class Solution {
    // unaccepted
    // consider [-2,3,-4]
    public int maxProduct(int[] nums) {
        int[] dp = new int[nums.length];
        int max = Integer.MIN_VALUE,current = 1,beforeMinus = 1;
        boolean minusFlag = false;
        for(int i=0;i<nums.length;i++){
            if(nums[i]>=0) dp[i] = current*nums[i];
            else{
                if(minusFlag&&max<beforeMinus*current*nums[i]) max = beforeMinus*current*nums[i];
                int tm = i+1,product = nums[i];
                while(tm<nums.length&&nums[tm]<0) product*=nums[tm++];
                if(product>0) dp[i = tm-1] = product*current;
                else{
                    if(minusFlag)
                        if(max<beforeMinus*product*current) max = beforeMinus*product*current;
                    dp[tm-2] = current*product/nums[tm-1];
                    dp[tm-1] = product/nums[i];
                    max = Math.max(max,dp[tm-2]);
                    i = tm-1;
                }
            }
            if(dp[i]>max) max = dp[i];
            current = dp[i]>0?dp[i]:1;
        }
        return max;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = new int[]{0,3,-10,3,-1};
        System.out.println(s.maxProduct2(a));
    }

    // most voted solution on leetcode
    public int maxProduct2(int[] nums){
        int max = nums[0];
        for(int i=1,imax = max,imin = max;i<nums.length;i++){
            if(nums[i]<0){ // swap
                int tm = imax;
                imax = imin;
                imin = tm;
            }
            imax = Math.max(nums[i],nums[i]*imax);
            imin = Math.min(nums[i],nums[i]*imin);
            if(imax>max) max = imax;
        }
        return max;
    }

    // This is actually also a DP solution
    // to "look" more like DP, we can have two array
    // containing the min so far and max so far.
    // always notice the status transition.

    // a more straightforward solution
//    public int maxProduct(int[] A) {
//        if (A.length == 0) {
//            return 0;
//        }
//
//        int maxherepre = A[0];
//        int minherepre = A[0];
//        int maxsofar = A[0];
//        int maxhere, minhere;
//
//        for (int i = 1; i < A.length; i++) {
//            maxhere = Math.max(Math.max(maxherepre * A[i], minherepre * A[i]), A[i]);
//            minhere = Math.min(Math.min(maxherepre * A[i], minherepre * A[i]), A[i]);
//            maxsofar = Math.max(maxhere, maxsofar);
//            maxherepre = maxhere;
//            minherepre = minhere;
//        }
//        return maxsofar;
//    }
}
