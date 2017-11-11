package SearchInRotatedSortedArr_33;

/**
 * Suppose an array sorted in ascending order is
 * rotated at some pivot unknown to you beforehand.
 *
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 *
 * You are given a target value to search.
 * If found in the array return its index, otherwise return -1.
 *
 * You may assume no duplicate exists in the array.
 */
public class Solution {

    // Second session
    // first thought would be finding the pivot.
    // however, we can also try to make other conditions in the
    // binary search
    public static int searchII(int[] nums, int target){
        int lo = 0, hi = nums.length-1;
        while(lo<=hi){
            int mid = (hi-lo)/2+lo;
            if(nums[mid]==target) return mid;
            if(nums[mid]>nums[lo]){
                // [lo, mid] sorted
                if(target<nums[mid]&&target>=nums[lo]) hi = mid-1;
                else lo = mid+1;
            }else{
                // [mid, hi] sorted
                if(target>nums[mid]&&target<=nums[hi]) lo = mid+1;
                else hi = mid-1;
            }
        }
        return -1;
    }

    public static int search(int[] nums, int target)
    {
        int index = 0;
        if(nums.length==0) return -1;
        while(index<nums.length-1&&nums[index]<=nums[index+1]) index++;// This step is slow..
        if(index==nums.length-1) // the array did not rotate
            return binarySearchInRange(nums,0,nums.length,target);
        if(target<nums[0])
            return binarySearchInRange(nums,index+1,nums.length,target);
        else
            return binarySearchInRange(nums,0,index+1,target);
    }

    // binary search in range. hi not inclusive
    static int binarySearchInRange(int[] nums, int lo, int hi,int target)
    {
        while(lo<hi-1)
        {
            int mid = (lo+hi)>>1;
            if(target<nums[mid]) hi = mid;
            else lo = mid;
        }
        return nums[lo]==target?lo:-1;
    }

    public static void main(String[] args)
    {
        int[] nums = new int[]{4,5,6,7,8,0,1,2,3};
        System.out.println(search(nums,11));
    }

    public static int search2(int[] num,int target)
    {
        // use binary search to locate pivot
        if(num.length==0) return -1;
        int lo = 0,hi = num.length-1;
        while(lo<hi)  // use binary search to find to smallest element
        {
            int mid = (lo+hi)>>1;
            if(num[mid]>num[hi]) lo = mid+1;
            else hi = mid;
        }

        // The code below to find the largest element in a rotated array
//        while(lo<hi)
//        {
//            int mid = (lo+hi)>>1;
//            if(num[mid]<num[lo]) hi = mid-1;
//            else if(num[mid]>num[lo]) lo = mid;
//            else if(num[lo]<num[hi]) lo++;
//            else hi--;
//        }

        int pivot = lo; // the smallest also the pivot

        lo = 0;
        hi = num.length-1;

        // binary search
        while(lo<=hi)
        {
           int mid = (lo+hi)>>1;
           int realmid = (mid+pivot)%num.length;
           if(num[realmid]<target) lo = mid+1;
           else if(num[realmid]==target) return realmid;
           else hi = mid-1;
        }
        return -1;
    }

    // inspired by the solution in #81
    // use nums[mid] & nums[lo] & nums[hi] to judge if one
    // of the range is in order. And we can take advantage
    // from the order.
    // This method cost O(lgn) averagely.
    public static int search3(int[] nums,int target)
    {
        int lo = 0,hi = nums.length-1;
        while(lo<=hi)
        {
            int mid = (lo+hi)>>1;
            if(nums[mid]==target) return mid;
            if(nums[lo]<=nums[mid])  // [lo,mid] in order
            {
                if(nums[lo]<=target&&target<nums[mid]) hi = mid-1;
                else lo = mid+1;
            }
            else                    // [lo,mid] in order
            {
                if(nums[mid]<target&&target<=nums[hi]) lo = mid+1;
                else hi = mid-1;
            }
        }
        return -1;
    }
}
