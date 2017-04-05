package SearchInRotatedSortedArrayII_81;

/**
 * Follow up for "Search in Rotated Sorted Array"(#32):
 * What if duplicates are allowed?
 *
 * Would this affect the run-time complexity? How and why?
 *
 * The array may contain duplicates.
 */
public class Solution
{
    // O(lgn)
    public static boolean search(int[] nums, int target)
    {
        int lo = 0,hi = nums.length-1;
        while(lo<=hi)
        {
            int mid = (lo+hi)>>1;
            if(nums[mid]==target) return true;

            // trick,to deal with duplicate situation
            // in this situation, we can't determine the sorted order.
            // so we skip it.
            if(nums[lo]==nums[mid]&&nums[mid]==nums[hi])
            {
                lo++;
                hi--;
            }
            else if(nums[lo]<=nums[mid]) // range [lo,mid] is in order
            {
                if(nums[lo]<=target&&target<nums[mid]) hi = mid-1;
                else lo = mid+1;
            }
            else                        // range [mid,hi] is in order
            {
                if(nums[mid]<target&&target<=nums[hi]) lo = mid+1;
                else hi = mid-1;
            }
        }
        return false;
    }

    public static void main(String[] args)
    {
        int[] nums = new int[]{2,2,2,0,2,2};
        System.out.println(search(nums,0));
    }
}
