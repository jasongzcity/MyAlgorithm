package MaximumProductOfThreeNumbers_628;

import java.util.Arrays;

/**
 * Given an integer array,
 * find three numbers whose product is maximum and output the maximum product.
 *
 * Example 1:
 * Input: [1,2,3]
 * Output: 6
 *
 * Example 2:
 * Input: [1,2,3,4]
 * Output: 24
 *
 * Note:
 * The length of the given array will be in range [3,104]
 * and all elements are in the range [-1000, 1000].
 *
 * Multiplication of any three numbers in the input
 * won't exceed the range of 32-bit signed integer.
 */
public class Solution {
    // trivial nlogn sorting solution
    public int maximumProduct2(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length, nega = nums[0]*nums[1], posi = nums[len-2]*nums[len-3];
        return Math.max(nega,posi)*nums[len-1];
    }

    // we scan the array to find the five candidates..
    public int maximumProduct(int[] nums){
        Integer n1 = Integer.MAX_VALUE, n2 = Integer.MAX_VALUE,
                p1 = Integer.MIN_VALUE, p2 = Integer.MIN_VALUE, p3 = Integer.MIN_VALUE;
        for(int i:nums){
            if(i<=n1){
                n2 = n1;
                n1 = i;
            }else if(i<n2){
                n2 = i;
            }
            if(i>=p1){
                p3 = p2;
                p2 = p1;
                p1 = i;
            }else if(i>=p2){
                p3 = p2;
                p2 = i;
            }else if(i>p3){
                p3 = i;
            }
        }
        return Math.max(p2*p3,n1*n2)*p1;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.maximumProduct(new int[]{1,2,3}));
    }
}
