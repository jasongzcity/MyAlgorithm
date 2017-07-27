package KthSmallestElementInTheSortedMatrix_378;

import java.util.PriorityQueue;

/**
 * Given a n x n matrix where each of the rows and columns are
 * sorted in ascending order, find the kth smallest element in the matrix.
 *
 * Note that it is the kth smallest element in the sorted order,
 * not the kth distinct element.
 *
 * Example:
 *
 * matrix = [
 * [ 1,  5,  9],
 * [10, 11, 13],
 * [12, 13, 15]
 * ],
 * k = 8,
 *
 * return 13.
 * Note:
 * You may assume k is always valid, 1 <= k <= n^2.
 */
public class Solution {
    // Solution using heap.
    // Same as #373 Find K pairs with smallest pairs.
    // https://discuss.leetcode.com/topic/52948/share-my-thoughts-and-clean-java-code
    public int kthSmallest2(int[][] matrix, int k) {
        int rows = matrix.length,cols = matrix[0].length;
        PriorityQueue<Tuple> pq = new PriorityQueue<>(k);
        for(int i=0;i<cols&&i<k;i++) pq.add(new Tuple(0,i,matrix[0][i]));
        // pop out k-1 tuple and the kth smallest is at the top
        for(int i=0;i<k-1;i++){
            Tuple t = pq.poll();
            if(t.row<rows-1){
                t.val = matrix[++t.row][t.col];
                pq.add(t);
            }
        }
        return pq.peek().val;
    }

    class Tuple implements Comparable<Tuple>{

        int row;
        int col;
        int val;

        public Tuple(int row,int col,int val){
            this.col = col;
            this.row = row;
            this.val = val;
        }

        @Override
        public int compareTo(Tuple o) {
            return this.val-o.val;
        }
    }

    // Binary Search solution
    // like (#287) Find Duplicate Number
    // But it uses value(also called range) instead of indice for searching
    public int kthSmallest(int[][] m, int k){
        int n = m.length,lo = m[0][0],hi = m[n-1][n-1];
        while(lo<hi){
            int mid = lo+((hi-lo)>>>1),j = n-1,count = 0;
            for(int i=0;i<n;i++){
                while(j>-1&&m[i][j]>mid) --j;
                count+=j+1;
            }
            if(k>count) lo = mid+1;
            else hi = mid;
        }
        return lo;
    }
}
