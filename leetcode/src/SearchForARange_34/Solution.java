package SearchForARange_34;

/**
 * Given an array of integers sorted in ascending order,
 * find the starting and ending position of a given target value.
 *
 * Your algorithm's runtime complexity must be in the order of O(logn).
 *
 * If the target is not found in the array, return [-1, -1].
 *
 * For example, Given [5, 7, 7, 8, 8, 10]
 * and target value 8, return [3, 4].
 */
public class Solution
{
    // This method use two binary search to locate
    // the begin and the end index of the target element.
    // O(2*lgn)
    public static int[] searchRange(int[] nums, int target)
    {
        if(nums.length==0) return new int[]{-1,-1};
        int[] rs = new int[2];
        int lo = 0,hi = nums.length-1,mid = -1;
        while(lo<hi)
        {
            mid = lo+((hi-lo)>>1);
            if(nums[mid]<target) lo = mid+1;
            else hi = mid;
        }
        if(nums[lo]!=target)
        {
            rs[0] = rs[1] = -1;
            return rs;
        }
        else rs[0] = lo;

        hi = nums.length;
        while(lo<hi)
        {
            mid = lo+((hi-lo)>>1);
            if(nums[mid]>target) hi = mid;
            else if(lo==mid) break;
            else lo = mid;
        }
        rs[1] = lo;
        return rs;
    }

    // test to get the smallest index of target.
    public static int test(int[] nums,int target)
    {
        int lo = 0,hi = nums.length-1,mid = -1;
        while(lo<hi)
        {
            mid = lo+((hi-lo)>>1);
            if(nums[mid]<target) lo = mid+1;
            else hi = mid;
        }
        return nums[lo]==target?lo:-1;
    }

    // get the largest index of target
    public static int test2(int[] nums,int target)
    {
        int lo = 0,hi = nums.length,mid = -1; // notice hi not inclusive

        while(lo<hi)
        {
            mid = lo+((hi-lo)>>1);
            if(nums[mid]>target) hi = mid;
            else if(lo==mid) break; // prevent endless loop, check it later
            // trick, push lo to mid if nums[mid] = target
            // Since hi not inclusive, when lo==mid we can just
            // jump out of the loop without considering
            // the element at hi.
            else lo = mid;
        }
        return lo<nums.length&&nums[lo]==target?lo:-1;
    }

    public static void main(String[] args)
    {
        int[] nums1 = new int[]{1,1,1,1,1};
        int[] nums2 = new int[]{2,2,3,3,3,3,3,3,3,3};
        int[] nums3 = new int[]{1};
        System.out.println(test2(nums1,1));
        System.out.println(test2(nums2,3));
        System.out.println(test2(nums2,1));
        System.out.println(test2(nums2,4));
        System.out.println(test2(nums3,1));
        System.out.println(test2(nums3,2));
    }

    // below is the most voted C++ solution on leetcode
    // use a subtly trick
//    vector<int> searchRange(int A[], int n, int target) {
//        int i = 0, j = n - 1;
//        vector<int> ret(2, -1);
//        // Search for the left one
//        while (i < j)
//        {
//            int mid = (i + j) /2;
//            if (A[mid] < target) i = mid + 1;
//            else j = mid;
//        }
//        if (A[i]!=target) return ret;
//        else ret[0] = i;
//
//        // Search for the right one
//        j = n-1;  // We don't have to set i to 0 the second time.
//        while (i < j)
//        {
//            int mid = (i + j) /2 + 1;	// Make mid biased to the right
//            if (A[mid] > target) j = mid - 1;
//            else i = mid;				// So that this won't make the search range stuck.
//        }
//        ret[1] = j;
//        return ret;
//    }
}
