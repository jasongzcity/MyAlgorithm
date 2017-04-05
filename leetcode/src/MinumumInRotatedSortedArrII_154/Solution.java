package MinumumInRotatedSortedArrII_154;

/**
 * Follow up for "Find Minimum in Rotated Sorted Array"(#153)
 * What if duplicates are allowed?
 *
 * Explain the Time Complexity
 */
public class Solution
{
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
