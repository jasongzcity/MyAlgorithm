package WiggleSortII_324;

import java.util.Arrays;
import java.util.Random;

/**
 * Given an unsorted array nums,
 * reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
 *
 * Example:
 * (1) Given nums = [1, 5, 1, 1, 6, 4], one possible answer is [1, 4, 1, 5, 1, 6].
 * (2) Given nums = [1, 3, 2, 2, 3, 1], one possible answer is [2, 3, 1, 3, 1, 2].
 *
 * Note:
 * You may assume all input has valid answer.
 *
 * Follow Up:
 * Can you do it in O(n) time and/or in-place with O(1) extra space?
 */
public class Solution {
    // "remap" the index and then use three-way partitioning
    public void wiggleSort(int[] nums) {
        int mid = findKthLargest(nums,(nums.length+1)/2);
        for(int p=0,low=0,hi=nums.length-1;p<=hi;p++){
            int mapped = remap(p,nums.length);
            if(nums[mapped]>mid) swap(nums,mapped,remap(low++,nums.length));
            else if(nums[mapped]<mid){
                swap(nums,mapped,remap(hi--,nums.length));
                --p; // check this position again
            }
            // let it stay if equals mid.
        }
    }

    private int remap(int i,int n){
        return (1+2*i) % (n|1);
    }

    // Average 0(n) time
    public int findKthLargest(int[] a, int k){
        Random rand = new Random();
        int lo = 0,hi = a.length;
        while(lo<hi){
            int slow = lo-1,fast = lo;
            swap(a,hi-1,lo+rand.nextInt(a.length)%(hi-lo));
            while(fast<hi-1){
                if(a[fast]>a[hi-1]&&fast>++slow) swap(a,fast,slow);
                fast++;
            }
            swap(a,hi-1,++slow);
            if(slow==k-1) return a[slow];
            if(slow<k-1) lo = slow+1;
            else hi = slow;
        }
        return lo;
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public void wiggleSort2(int[] nums){
        Arrays.sort(nums);
        int[] backup = new int[nums.length];
        int lo,hi,p = 0;
        if(nums.length%2==1){
            lo = 0;
            hi = nums.length/2+1;
            while(lo<nums.length/2){
                backup[p++] = nums[lo++];
                backup[p++] = nums[hi++];
            }
            backup[nums.length-1] = nums[nums.length/2];
        }else{
            lo = nums.length/2-1;
            hi = nums.length-1;
            while(lo>-1){
                backup[p++] = nums[lo--];
                backup[p++] = nums[hi--];
            }
        }
        System.arraycopy(backup,0,nums,0,nums.length);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = {1,5,1,1,6,4};
        s.wiggleSort2(a);
        System.out.println(Arrays.toString(a));
    }
}
