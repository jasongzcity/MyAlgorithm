package MergeSortedArray_88;

import java.util.Arrays;

/**
 * Given two sorted integer arrays nums1 and nums2,
 * merge nums2 into nums1 as one sorted array.
 *
 * Note:
 * You may assume that nums1 has enough space (size that is greater or equal to m + n)
 * to hold additional elements from nums2.
 * The number of elements initialized in nums1 and nums2 are m and n respectively.
 */
public class Solution {
    public static void merge(int[] n1, int m, int[] n2, int n) {
        int len = m+n-1;
        --m;
        --n;
        while(m>-1||n>-1){
            while(m>-1&&(n==-1||n1[m]>=n2[n])) n1[len--] = n1[m--];
            while(n>-1&&(m==-1||n1[m]<n2[n])) n1[len--] = n2[n--];
        }
    }

    public static void main(String[] args) {
        int[] n1 = new int[]{1,2,2,8,15,99,158,159,200,0,0,0};
        int[] n2 = new int[]{100,201,208};
        merge(n1,n1.length-3,n2,3);
        System.out.println(Arrays.toString(n1));

        int[] n3 = new int[]{1,2,3};
        int[] n4 = new int[0];
        merge(n3,3,n4,0);
        System.out.println(Arrays.toString(n3));
    }
}
