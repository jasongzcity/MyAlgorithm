package IntersectionOfTwoArraysII_350;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given two arrays, write a function to compute their intersection.
 *
 * Example:
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].
 *
 * Note:
 * Each element in the result should appear as many times as it shows in both arrays.
 * The result can be in any order.
 * Follow up:
 * What if the given array is already sorted? How would you optimize your algorithm?
 * What if nums1's size is small compared to nums2's size? Which algorithm is better?
 * What if elements of nums2 are stored on disk,
 * and the memory is limited such that you cannot load all elements
 * into the memory at once?
 */
public class Solution {
    // see analysis of the follow ups at thoughts.txt

    // binary search solution
    public int[] intersect(int[] n1, int[] n2) {
        if(n1.length==0||n2.length==0) return new int[0];
        Arrays.sort(n1);
        Arrays.sort(n2);
        List<Integer> l = new ArrayList<>(n1.length);
        int count = 0;
        for(int i=0;i<n1.length;i++){
            if(i>0&&n1[i]==n1[i-1]) ++count;
            else count = 0;
            binarySearch(n2,n1[i],count,l);
        }
        int[] rs = new int[l.size()];
        count = 0;
        for(int i:l) rs[count++] = i;
        return rs;
    }

    private void binarySearch(int[] a,int target,int count,List<Integer> l){
        int lo = 0,hi = a.length;
        while(lo<hi){
            int mid = (lo+hi)>>1;
            if(target>a[mid]) lo = mid+1;
            else hi = mid;
        }
        lo+=count;
        if(lo<a.length&&a[lo]==target) l.add(target);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(Arrays.toString(
                s.intersect(new int[]{1,2,2,1},new int[]{7,893,39,2,1,1,2,45,23,1,2})));
        System.out.println(Arrays.toString(
                s.intersect(new int[]{1,2,2,1},new int[]{7,893,39,2,45,23,1})));
    }
}
