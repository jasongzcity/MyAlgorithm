package FourSumII_454;

import java.util.*;

/**
 * Given four lists A, B, C, D of integer values,
 * compute how many tuples (i, j, k, l) there are such
 * that A[i] + B[j] + C[k] + D[l] is zero.
 *
 * To make problem a bit easier, all A, B, C, D have same
 * length of N where 0 ≤ N ≤ 500. All integers are in
 * the range of -2^28 to 2^28 - 1 and the result is
 * guaranteed to be at most 2^31 - 1.
 */
public class Solution
{
    // brute force. Time complexity: O(N^4)
    public static int fourSumCount(int[] A, int[] B, int[] C, int[] D)
    {
        Arrays.sort(A);
        Arrays.sort(B);
        Arrays.sort(C);
        Arrays.sort(D);
        int count = 0;
        for(int i=0;i<A.length;i++)
        {
            int ea = A[i];
            for(int j=0;j<B.length;j++)
            {
                int eb = B[j];
                for(int k=0;k<C.length;k++)
                {
                    int ec = C[k];
                    for(int l=0;l<D.length;l++)
                    {
                        int ed = D[l];
                        if(ea+eb+ec+ed<0) continue;
                        if(ea+eb+ec+ed>0) break;//no more searching in D
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // Accepted. Time complexity: O(n^2)
    public static int fourSumCount2(int[] A, int[] B, int[] C, int[] D)
    {
        int count = 0;
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<A.length;i++)
            for(int j=0;j<B.length;j++) // The map contains every sums as the key and their times of appearance as the value
                map.put(A[i]+B[j],map.getOrDefault(A[i]+B[j],0)+1);
        for(int i=0;i<C.length;i++)
            for(int j=0;j<D.length;j++)
                count += map.getOrDefault(-(C[i]+D[j]),0);
        return count;
    }
}
