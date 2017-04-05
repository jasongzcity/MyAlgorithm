package SearchInsertPosition_35;

/**
 * Given a sorted array and a target value,
 * return the index if the target is found.
 * If not, return the index where it would be if it were inserted in order.
 *
 * You may assume no duplicates in the array
 */
public class Solution
{
    public static int searchInsert(int[] nums, int target)
    {
        int lo = 0,hi = nums.length-1;
        while(lo<=hi)
        {
            int mid = (lo+hi)>>1;
            if(target==nums[mid]) return mid;
            else if(target<nums[mid]) hi = mid-1;
            else lo = mid+1;
        }
        return lo;
    }

    public static void main(String[] args)
    {
        // test the correctness of solution
        // while duplicate elements allowed
        int[] nums1 = new int[]{1,2,2,2,3,4,8};
        int[] nums2 = new int[]{1,1};
        System.out.println(searchInsert(nums1,1));
        System.out.println(searchInsert(nums1,0));
        System.out.println(searchInsert(nums1,2));
        System.out.println(searchInsert(nums1,3));
        System.out.println(searchInsert(nums2,0));
        System.out.println(searchInsert(nums2,1));
        System.out.println(searchInsert(nums2,2));
        // its still correct
    }
}
