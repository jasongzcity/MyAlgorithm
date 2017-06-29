package MaximumGap_164;

import java.util.Arrays;

/**
 * Given an unsorted array,
 * find the maximum difference between the successive elements in its sorted form.
 *
 * Try to solve it in linear time/space.
 *
 * Return 0 if the array contains less than 2 elements.
 *
 * You may assume all elements in the array are non-negative integers and
 * fit in the 32-bit signed integer range.
 */
public class Solution {
    // radix sort
    // accepted
    public int maximumGap2(int[] a) {
        if(a.length<2) return 0;
        int max = -1;
        for(int i:a)
            if(i>max)
                max = i;
        int mask = 1;
        int[] count = new int[10],temp = new int[a.length],pointerA = a,pointerB = temp;
        while(max/mask>0){
            for(int i:pointerA) count[i/mask%10]++;
            int sum = 0;
            for(int i=0;i<10;i++) sum = count[i]+=sum;
            for(int i=a.length-1;i>-1;i--) pointerB[--count[pointerA[i]/mask%10]] = pointerA[i];
            // swap array, keep sorting.
            temp = pointerA;
            pointerA = pointerB;
            pointerB = temp;
            Arrays.fill(count,0);
            mask*=10;
        }
        int prev = pointerA[0],maxGap = -1;
        for(int i=1;i<a.length;i++){
            if(pointerA[i]-prev>maxGap)
                maxGap = pointerA[i]-prev;
            prev = pointerA[i];
        }
        return maxGap;
    }

    public static void main(String[] args) {
        int[] a = new int[]{3,6,9,1};
        Solution s = new Solution();
        System.out.println(s.maximumGap(a));
    }

    // bucket sort
    public int maximumGap(int[] a){
        int len = a.length;
        if(len<2) return 0;
        int[] bucket = new int[len<<1];
        Arrays.fill(bucket,-1);
        int lo = a[0],hi = a[0];
        for(int i:a){
            if(i>hi) hi = i;
            else if(i<lo) lo = i;
        }
        if(hi-lo==0) return 0;
        int gap = (int)Math.ceil((double)(hi-lo)/(len-1));
        for(int i:a){
            int hash = (i-lo)/gap;
            if(bucket[hash]==-1||bucket[hash]>i) bucket[hash] = i;
            if(bucket[hash+len]==-1||bucket[hash+len]<i) bucket[hash+len] = i;
        }
        int prev = bucket[len],maxDiff = -1;
        for(int i=1;i<len;i++){
            if(bucket[i]==-1) continue;
            if(bucket[i]-prev>maxDiff) maxDiff = bucket[i]-prev;
            prev = bucket[i+len];
        }
        return maxDiff;
    }
}
