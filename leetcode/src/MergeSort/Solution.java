package MergeSort;

import java.util.Arrays;

/**
 * Implement mergesort algorithm
 */
public class Solution {
    public static int[] mergeSort(int[] a){
        if(a.length==0) return a;
        return divideAndMerge(a, 0, a.length-1);
    }

    // begin and end inclusive
    private static int[] divideAndMerge(int[] a, int begin, int end){
        if(begin==end) return new int[]{a[begin]};
        int mid = (begin+end)/2;
        int[] left = divideAndMerge(a, begin, mid);
        int[] right = divideAndMerge(a, mid+1, end);
        return merge(left, right);
    }

    private static int[] merge(int[] a,int[] b){
        int[] rt = new int[a.length+b.length];
        int pa = 0,pb = 0,rp = 0;
        while(pa<a.length||pb<b.length){
            if(pb>=b.length||(pa<a.length&&a[pa]<=b[pb])) rt[rp++] = a[pa++];
            else rt[rp++] = b[pb++];
        }
        return rt;
    }

    public static void main(String[] args) {
        int[] a = {};
        int[] b = {0};
        int[] c = {2, 3, 52, 23, 234, 123, 34, 234, 234};
        int[] d = {12, 124, 4, 12, 32, 1, 2, 3, 414, 543, 234};
        int[] e = {12, 2, 2, 9, 9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 9, 9, 9, 9, 3, 3, 31, 1, 1, 1, 1, 1};
        System.out.println(Arrays.toString(mergeSort(a)));
        System.out.println(Arrays.toString(mergeSort(b)));
        System.out.println(Arrays.toString(mergeSort(c)));
        System.out.println(Arrays.toString(mergeSort(d)));
        System.out.println(Arrays.toString(mergeSort(e)));
    }
}
