package FindKPairsWithSmallestSum_373;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * You are given two integer arrays nums1 and nums2 sorted in
 * ascending order and an integer k.
 *
 * Define a pair (u,v) which consists of one element from the
 * first array and one element from the second array.
 *
 * Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
 *
 * Example 1:
 * Given nums1 = [1,7,11], nums2 = [2,4,6],  k = 3
 * Return: [1,2],[1,4],[1,6]
 * The first 3 pairs are returned from the sequence:
 * [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
 *
 * Example 2:
 * Given nums1 = [1,1,2], nums2 = [1,2,3],  k = 2
 * Return: [1,1],[1,1]
 * The first 2 pairs are returned from the sequence:
 * [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
 *
 * Example 3:
 * Given nums1 = [1,2], nums2 = [3],  k = 3
 * Return: [1,3],[2,3]
 * All possible pairs are returned from the sequence:
 * [1,3],[2,3]
 */
public class Solution {
    public List<int[]> kSmallestPairs(int[] n1, int[] n2, int k) {
        int onelen = n1.length,twolen = n2.length;
        List<int[]> rs = new ArrayList<>(k);
        if(onelen*twolen<=k){
            // shortcut
            for(int i=0;i<onelen;i++)
                for(int j=0;j<twolen;j++)
                    rs.add(new int[]{n1[i],n2[j]});
            return rs;
        }
        PriorityQueue<Tuple> pq = new PriorityQueue<>(k);
        for(int i=0;i<onelen&&i<twolen&&i*i<=k;i++)
            pq.add(new Tuple(i,i,false,n1[i]+n2[i]));
        for(int i=0;i<k;i++){
            // get k smallest
            Tuple t = pq.poll();
            rs.add(new int[]{n1[t.p1],n2[t.p2]});
            if(t.p1==t.p2){
                // split
                if(t.p2+1<twolen) pq.add(new Tuple(t.p1,t.p2+1,true,n1[t.p1]+n2[t.p2+1]));
                if(t.p1+1<onelen){
                    t.sum = n1[++t.p1]+n2[t.p2];
                    pq.add(t);
                }
            }else{
                if(t.fixedOnOne&&t.p2+1<twolen){
                    t.sum = n1[t.p1]+n2[++t.p2];
                    pq.add(t);
                }else if(!t.fixedOnOne&&t.p1+1<onelen){
                    t.sum = n1[++t.p1]+n2[t.p2];
                    pq.add(t);
                }
            }
        }
        return rs;
    }

    class Tuple implements Comparable<Tuple>{

        int p1;
        int p2;
        boolean fixedOnOne; // fixed on array two if false
        int sum;

        public Tuple(int p1,int p2,boolean fixedOnOne,int sum){
            this.p1 = p1;
            this.p2 = p2;
            this.fixedOnOne = fixedOnOne;
            this.sum = sum;
        }

        @Override
        public int compareTo(Tuple o) {
            return this.sum-o.sum;
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a1 = new int[]{1,7,11};
        int[] a2 = new int[]{2,4,6};
        for(int[] a:s.kSmallestPairs(a1,a2,3))
            System.out.println(Arrays.toString(a));
    }
}
