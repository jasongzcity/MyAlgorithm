package UglyNumberII_264;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Write a program to find the n-th ugly number.
 *
 * Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.
 * For example, 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the
 * first 10 ugly numbers.
 *
 * Note that 1 is typically treated as an ugly number, and n does not exceed 1690.
 */
public class Solution {
    // pq solution
    public int nthUglyNumber2(int n) {
        if(n<=1) return 1;
        PriorityQueue<Tuple> pq = new PriorityQueue<>(n);
        pq.add(new Tuple(2,2));
        pq.add(new Tuple(3,3));
        pq.add(new Tuple(5,5));
        int val = 0,factor;
        while(n-->1){ // skip 1
            Tuple t = pq.poll();
            val = t.val;
            factor = t.factor;
            t.val*=5;
            t.factor = 5;
            if(t.val/5==val) pq.add(t); // avoid overflow
            int newVal = val*3;
            if(factor<=3&&newVal/3==val) pq.add(new Tuple(newVal,3));
            newVal = val*2;
            if(factor==2&&newVal/2==val) pq.add(new Tuple(newVal,2));
        }
        return val;
    }

    class Tuple implements Comparable<Tuple>{
        int val;
        int factor;
        public Tuple(int val,int factor){
            this.val = val;
            this.factor = factor;
        }

        @Override
        public int compareTo(Tuple o) {
            return this.val-o.val;
        }
    }

    // DP solution
    public int nthUglyNumber(int n){
        if(n==1) return 1;
        int[] dp = new int[n];
        dp[0] = 1;
        int p2 = 0,p3 = 0,p5 = 0;
        for(int i=1;i<n;i++){
            int next = Math.min(dp[p2]*2,Math.min(dp[p3]*3,dp[p5]*5));
            dp[i] = next;
            if(next==dp[p2]*2) ++p2;
            if(next==dp[p3]*3) ++p3;
            if(next==dp[p5]*5) ++p5;
        }
        return dp[n-1];
    }

    // Solution using 3 queues
    public int nthUglyNumber3(int n) {
        if (n == 1) return 1;
        Queue<Long> queue2 = new LinkedList<>();
        Queue<Long> queue3 = new LinkedList<>();
        Queue<Long> queue5 = new LinkedList<>();
        queue2.add(2l);
        queue3.add(3l);
        queue5.add(5l);
        long val = 0;
        while(n-->1){
            long v2 = queue2.peek();
            long v3 = queue3.peek();
            long v5 = queue5.peek();
            val = Math.min(v2, Math.min(v3, v5));
            if (val == v2) {
                queue2.poll();
                queue2.add(val*2);
                queue3.add(val*3);
            }
            else if (val == v3) {
                queue3.poll();
                queue3.add(val*3);
            }
            else
                queue5.poll();
            queue5.add(val*5);
        }
        return (int)val;
    }
}
