package MinimumInRotatedSortedArr_153;

/**
 * Suppose an array sorted in ascending order
 * is rotated at some pivot unknown to you beforehand.
 *
 * Find the minimum element.
 *
 * You may assume no duplicate exists in the array.
 */
public class Solution
{
    // Second session
    // notice this is not accepted.
    public static int findMinII(int[] nums){
        int lo = 0, hi = nums.length;
        while(lo<hi){
            int mid = (lo+hi)/2;
            if(nums[mid]<nums[lo]) hi = mid;// notice test case
            // [8,1,2] the hi may fall to 0 so
            // compare lo and lo+1;
            // not as straight forward as the hi one.
            else lo = mid+1;
        }
        return nums[lo];
    }

    public static int findMin(int[] nums)
    {
        int lo = 0,hi = nums.length-1;
        while(lo<hi)
        {
            int mid = (lo+hi)>>1;
            if(nums[mid]>nums[hi]) lo = mid+1;  // unsorted, contains pivot
            else hi = mid;                      // sorted, keep finding.
        }
        return nums[lo];
    }

    // extra mission: find the medium of the array.
    public static float findMedium(int[] nums)
    {
        if(nums.length==0) return -1;
        int lo = 0,hi = nums.length-1;
        while(lo<hi)
        {
            int mid = (lo+hi)>>1;
            if(nums[mid]>nums[hi]) lo = mid+1;  // unsorted, contains pivot
            else hi = mid;                      // sorted, keep finding.
        }
        int len = nums.length;
        if((len&1)==1) // odd
            return (float)nums[((len>>1)+lo)%len];
        else
            return ((float)nums[((len>>1)-1+lo)%len]+(float)nums[((len>>1)+lo)%len])/2;
    }

    public static void main(String[] args)
    {
        int[] nums = new int[]{7,8,9,10,11,12,13,1};
        System.out.println(findMin(nums));
        int[] nums1 = new int[]{};
        System.out.println(findMin(nums1));
        int[] nums2 = new int[]{8,1,2};
        System.out.println(findMin(nums2));
    }
    // hard: find minimum when allow duplicates.
}
