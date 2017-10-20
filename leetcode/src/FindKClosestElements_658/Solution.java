package FindKClosestElements_658;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a sorted array, two integers k and x,
 * find the k closest elements to x in the array.
 * The result should also be sorted in ascending order.
 * If there is a tie, the smaller elements are always preferred.
 *
 * Example 1:
 * Input: [1,2,3,4,5], k=4, x=3
 * Output: [1,2,3,4]
 *
 * Example 2:
 * Input: [1,2,3,4,5], k=4, x=-1
 * Output: [1,2,3,4]
 *
 * Note:
 * The value k is positive and will always be smaller than the length of the sorted array.
 * Length of the given array is positive and will not exceed 10^4
 * Absolute value of elements in the array and x will not exceed 10^4
 */
public class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        if(arr.length==0) return Collections.emptyList();
        List<Integer> l = new LinkedList<>();
        int lo = 0, hi = arr.length;
        // binary search
        while(lo<hi){
            int mid = (lo+hi)/2;
            if(arr[mid]>=x) hi = mid;
            else lo = mid+1;
        }
        if(lo<arr.length&&arr[lo]==x){
            l.add(x);
            lo--;
            hi++;
        }else{
            lo--;
        }
        while(l.size()<k){
            if(lo>-1&&(hi==arr.length||(x-arr[lo]<=arr[hi]-x))) l.add(0, arr[lo--]);
            else l.add(arr[hi++]);
        }
        return l;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
//        System.out.println(s.findClosestElements2(new int[]{1},1, 3));
//        System.out.println(s.findClosestElements2(new int[]{1,2,3,4,5},4, -1));
//        System.out.println(s.findClosestElements2(new int[]{1,2,3,4,5},3, 1));
//        System.out.println(s.findClosestElements2(new int[]{1,2,3,4,5,8,9,10,11,100,200,330,400},5, 12));
        System.out.println(s.findClosestElements2(new int[]{-100,-99,-88,11,22,33},2, 13));
    }

    public List<Integer> findClosestElements2(int[] arr, int k, int x) {
        int lo = 0, hi = arr.length - k;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (x - arr[mid] > arr[mid+k] - x)
                lo = mid + 1;
            else
                hi = mid;
        }
        List<Integer> l = new ArrayList<>(k);
        for(int i=lo;i<lo+k;i++) l.add(arr[i]);
        return l;
    }
}
