package RangeSumQueryImmu_303;

/**
 * Given an integer array nums, find the sum of the elements between
 * indices i and j (i ≤ j), inclusive.
 *
 * Example:
 * Given nums = [-2, 0, 3, -5, 2, -1]
 *
 * sumRange(0, 2) -> 1
 * sumRange(2, 5) -> -1
 * sumRange(0, 5) -> -3
 *
 * Note:
 * You may assume that the array does not change.
 * There are many calls to sumRange function.
 */
public class NumArray {

    private int[] a;

    public NumArray(int[] n) {
        a = new int[n.length];
        if(n.length==0) return;
        a[0] = n[0];
        for(int i=1;i<n.length;i++) a[i] = a[i-1]+n[i];
    }

    public int sumRange(int i, int j) {
        return a[j]-(i>0?a[i-1]:0);
    }
}
