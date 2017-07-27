package FindDuplicateNumber_287;

/**
 * Given an array nums containing n + 1 integers where each integer is
 * between 1 and n (inclusive), prove that at least one duplicate number must exist.
 * Assume that there is only one duplicate number, find the duplicate one.
 *
 * Note:
 * You must not modify the array (assume the array is read only).
 * You must use only constant, O(1) extra space.
 * Your runtime complexity should be less than O(n^2).
 * There is only one duplicate number in the array,
 * but it could be repeated more than once.
 */
public class Solution {
    // Using binary search.
    // This solution is much easier to understand
    // O(nlogn)
    public int findDuplicate2(int[] a) {
        int lo = 0,hi = a.length;
        while(lo<hi){
            int mid = lo+((hi-lo)>>1),count = 0;
            for(int i=0;i<a.length;i++)
                if(a[i]<=mid)
                    count++;
            if(count<=mid) lo = mid+1;
            else hi = mid;
        }
        return lo;
    }

    // O(n) solution
    public int findDuplicate(int[] a){
        int slow = a.length,fast = slow;
        do{
            slow = a[slow-1];
            fast = a[a[fast-1]-1];
        }while(slow!=fast);

        int finder = a.length;
        while(slow!=finder){
            slow = a[slow-1];
            finder = a[finder-1];
        }
        return finder;
    }
}
