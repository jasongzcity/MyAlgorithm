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
    // Second session
    // Compare with #32, say we got an input [3,1,3,3,3,3,3,3,3]
    // when you have a[lo] == a[hi] == a[mid]
    // we can't know which side is "sorted" (it can also be
    // [3,3,3,3,3,1,3])
    // so we skip it..
    // counter example for algorithm from#32 can't apply in this
    // situation: [3,4,3,3,3,3,3,3] you may choose to go into the "larger half"
    // of the array
    public boolean searchII(int[] nums, int target){
        int lo = 0, hi = nums.length-1;
        while(lo<=hi){
            int mid = (lo+hi)/2;
            if(nums[mid]==target) return true;
            if(nums[mid]==nums[lo]&&nums[mid]==nums[hi]){
                lo++;
                hi--;
            }
            // when we have skipped the duplicates,
            // we can do it just like #32
            else if(nums[mid]>=nums[lo]){
                // now [lo, mid] is really sorted.
                // notice that target >= nums[lo]
                // is still necessary,
                // it could be target < nums[mid]
                // but locate at the right of the mid.
                if(nums[mid]>target&&nums[lo]<=target) hi = mid-1;
                else lo = mid+1;
            }else{
                // again, target<=nums[hi] is necessary because you
                // can still have the target locate at the right of
                // the mid while target is larger than nums[mid]
                if(nums[hi]>=target&&nums[mid]<target) lo = mid+1;
                else hi = mid-1;
            }
        }
        return false;
    }

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
