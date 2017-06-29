package RotateArray_189;

import java.util.Arrays;

/**
 * Rotate an array of n elements to the right by k steps.
 *
 * For example, with n = 7 and k = 3,
 * the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
 *
 * Note:
 * Try to come up as many solutions as you can,
 * there are at least 3 different ways to solve this problem.
 *
 * Hint:
 * Could you do it in-place with O(1) extra space?
 * Related problem: Reverse Words in a String II
 */
public class Solution {
    // O(n) space 2-pass naive solution
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        int[] tm = new int[k];
        System.arraycopy(nums,nums.length-k,tm,0,k);
        System.arraycopy(nums,0,nums,k,nums.length-k);
        System.arraycopy(tm,0,nums,0,k);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = new int[]{1,2,3,4,5,6};
        s.rotate2(a,2);
        System.out.println(Arrays.toString(a));
//        s.rotate2(a,a.length);
//        System.out.println(Arrays.toString(a));
//        s.rotate2(a,a.length-1);
//        System.out.println(Arrays.toString(a));
    }

    // in-place one-pass solution
    public void rotate2(int[] nums, int k){
        int len = nums.length;
        k %= len;
        int cur = (len-1+k)%len,prev = nums[len-1],backup,starter=len-1,counter=0;
        while(true){
            backup = nums[cur];
            nums[cur] = prev;
            prev = backup;
            ++counter;
            if(cur==starter){
                if(counter==len) return;
                else prev = nums[cur = --starter];
            }
            cur = (cur+k)%len;
        }
    }

    // in-place solution using reverse
    // two pass
    public void rotate3(int[] nums, int k){
        k %= nums.length;
        reverse(nums,0,nums.length-1);
        reverse(nums,0,k-1);
        reverse(nums,k,nums.length-1);
    }

    // reverse solution, for better readability
    private void reverse(int[] a,int begin,int end){
        for(int i=begin,j=end;i<j;i++,j--){
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }
}
