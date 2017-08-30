package WiggleSort_280;

import java.util.Arrays;
import java.util.Random;

/**
 * Given an unsorted array nums,
 * reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....
 *
 * For example, given nums = [3, 5, 2, 1, 6, 4],
 * one possible answer is [1, 6, 2, 5, 3, 4].
 */
public class Solution {
    // pre-sort solution
    // O(n^2)
    // Accepted but slow....
    public void wiggleSort2(int[] nums) {
        Arrays.sort(nums);
        for(int i=1;i<nums.length-1;i+=2){
            int tm = nums[nums.length-1];
            System.arraycopy(nums,i,nums,i+1,nums.length-1-i);
            nums[i] = tm;
        }
    }

    private Random rand = new Random();

    private void swap(int[] a,int i,int j){
        int tm = a[i];
        a[i] = a[j];
        a[j] = tm;
    }

    public void wiggleSort3(int[] nums){
        partition2(nums,0,nums.length-1);
    }

    // O(nlogn)
    private void partition2(int[] a,int lo,int hi){
        if(lo>=hi) return;
        swap(a,hi,lo+rand.nextInt(hi-lo+1)); // randomize
        int slow = lo,fast = lo;
        for(;fast<hi;fast++)
            if(a[fast]<a[hi])
                swap(a,slow++,fast);
        swap(a,slow,hi);
        if(slow%2==0){
            // small situation
            if(slow-1>=lo) swap(a,slow,slow-1);
            partition2(a,lo,slow-2);
            partition2(a,slow+1,hi);
        }else{
            if(slow+1<=hi) swap(a,slow,slow+1);
            partition2(a,lo,slow-1);
            partition2(a,slow+2,hi);
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = {1,3,2,4};
        s.wiggleSort(a);
        System.out.println(Arrays.toString(a));
        int[] b = {3,6,5,1,2,4};
        s.wiggleSort(b);
        System.out.println(Arrays.toString(b));
    }

    // I have been making the problem even more complicated....
    // one pass solution
    public void wiggleSort(int[] nums){
        boolean less = true;
        for(int i=1;i<nums.length;i++,less^=true)
            if(less^(nums[i-1]<nums[i]))
                swap(nums,i-1,i);
    }
}
