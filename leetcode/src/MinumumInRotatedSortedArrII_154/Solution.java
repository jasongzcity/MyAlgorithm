package MinumumInRotatedSortedArrII_154;

/**
 * Follow up for "Find Minimum in Rotated Sorted Array"(#153)
 * What if duplicates are allowed?
 *
 * Explain the Time Complexity
 */
public class Solution
{
    // Second session
    // thought: when we introduce duplicates in the input array,
    // we can't judge our position by using only nums[mid]>=nums[lo]
    // notice this is wrong...
    public int findMinII(int[] nums){
        int lo = 0, hi = nums.length-1;
        while(lo<hi){
            int mid = (lo+hi)/2;
            if(nums[mid]==nums[lo]&&nums[mid]==nums[hi]) --hi;
            else if(nums[mid]>nums[lo]) lo = mid+1;
            else hi = mid; // nums[mid] <= nums[lo]
        }
        return hi<0?nums[0]:nums[hi];
    }

    public static int findMinII2(int[] a){
        int lo = 0, hi = a.length-1;
        while(lo<hi){
            int mid = (lo+hi)/2;
            if(a[mid]>a[hi]) lo = mid+1;
            else if(a[mid]<a[hi]) hi = mid;
            else --hi; // trick to skip duplicates.
            // notice that if we have extreme inputs like:
            // [3, 1,1,1,1,1,1,1] we will get O(n) instead of O(logn)
        }
        return a[lo];
    }


    public static int findMin(int[] nums)
    {
        int lo = 0,hi = nums.length-1;
        while(lo<hi)
        {
            int mid = lo+((hi-lo)>>1);
            if(nums[mid]>nums[hi]) lo = mid+1;
            else if(nums[mid]<nums[hi]) hi = mid;
            else hi--; // hi-- when equals
        }
        return nums[lo];
    }

    public static void main(String[] args)
    {
        int[] nums1 = new int[]{1,0,1,1,1};
        System.out.println(findMin(nums1));
        int[] nums2 = new int[]{1,1,1,0,1};
        System.out.println(findMin(nums2));
    }
}
