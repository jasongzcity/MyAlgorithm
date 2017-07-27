package IntersectionOfTwoArray_349;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Given two arrays, write a function to compute their intersection.
 *
 * Example:
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2].
 *
 * Note:
 * Each element in the result must be unique.
 * The result can be in any order.
 */
public class Solution {
    // solution using hash map is naive

    // Solution using two pointers
    public int[] intersection2(int[] n1, int[] n2) {
        if(n1.length==0||n2.length==0) return new int[0];
        int p1 = 0,p2 = 0;
        Arrays.sort(n1);
        Arrays.sort(n2);
        Set<Integer> set = new HashSet<>(n1.length<<1);
        while(p1<n1.length&&p2<n2.length){
            if(n1[p1]<n2[p2]) ++p1;
            else if(n1[p1]>n2[p2]) ++p2;
            else{
                set.add(n1[p1]);
                ++p1;
                ++p2;
            }
        }
        int[] rs = new int[set.size()];
        p1 = 0;
        for(int i:set) rs[p1++] = i;
        return rs;
    }

    // Solution using binary search
    public int[] intersection(int[] n1, int[] n2){
        if(n1.length==0||n2.length==0) return new int[0];
        Set<Integer> set = new HashSet<>(n1.length);
        Arrays.sort(n2);
        for(int i:n1) binarySearch(n2,i,set);
        int[] rs = new int[set.size()];
        int p = 0;
        for(int i:set) rs[p++] = i;
        return rs;
    }

    private void binarySearch(int[] a,int target,Set<Integer> set){
        int lo = 0,hi = a.length;
        while(lo<hi){
            int mid = (lo+hi)>>1;
            if(a[mid]<target) lo = mid+1;
            else hi = mid;
        }
        if(lo<a.length&&a[lo]==target) set.add(target);
    }
}
