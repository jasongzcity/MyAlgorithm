package SearchInsertPosition_35;

/**
 * Given a sorted array and a target value, return the index if the target is found.
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
}
