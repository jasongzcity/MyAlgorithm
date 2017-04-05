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
        System.out.println(findMedium(nums));
        int[] nums1 = new int[]{1};
        System.out.println(findMedium(nums1));
        int[] nums2 = new int[]{8,1,2};
        System.out.println(findMedium(nums2));
    }
    // hard: find minimum when allow duplicates.
}
