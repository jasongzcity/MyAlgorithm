package HIndex_274;

import java.util.Arrays;

/**
 * Given an array of citations (each citation is a non-negative integer) of a researcher,
 * write a function to compute the researcher's h-index.
 *
 * According to the definition of h-index on Wikipedia:
 * "A scientist has index h if h of his/her N papers have at least h citations each,
 * and the other N − h papers have no more than h citations each."
 *
 * For example, given citations = [3, 0, 6, 1, 5],
 * which means the researcher has 5 papers in total and each of them
 * had received 3, 0, 6, 1, 5 citations respectively.
 * Since the researcher has 3 papers with at least 3 citations each
 * and the remaining two with no more than 3 citations each, his h-index is 3.
 *
 * Note: If there are several possible values for h,
 * the maximum one is taken as the h-index.
 */
public class Solution {
    public int hIndex2(int[] citations) {
        if(citations.length==0) return 0;
        Arrays.sort(citations);
        int count = 0,pointer = citations.length-1;
        for(int i=citations.length;;i--){
            while(pointer>-1&&citations[pointer]>=i){
                pointer--;
                count++;
            }
            if(count>=i) return i;
        }
    }

    // solution above is slow
    // use counting sort instead
    public int hIndex(int[] citations){
        int[] count = new int[citations.length+1];
        for(int i:citations){
            int key = i>citations.length?citations.length:i;
            count[key]++;
        }
        int index = count.length-1;
        for(int papers=count[index];papers<index;papers+=count[index])
            index--;
        return index;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.hIndex(new int[]{10,10,10,10}));
        System.out.println(s.hIndex(new int[]{0,0,0,0}));
        System.out.println(s.hIndex(new int[]{0}));
        System.out.println(s.hIndex(new int[]{100}));
        System.out.println(s.hIndex(new int[]{4}));
    }
}
