package HouseRobberII_213;

import java.util.Arrays;

/**
 * Note: This is an extension of House Robber.
 *
 * After robbing those houses on that street,
 * the thief has found himself a new place for his thievery
 * so that he will not get too much attention.
 * This time, all houses at this place are arranged in a circle.
 * That means the first house is the neighbor of the last one.
 * Meanwhile, the security system for these houses
 * remain the same as for those in the previous street.
 *
 * Given a list of non-negative integers representing
 * the amount of money of each house,
 * determine the maximum amount of money you can rob
 * tonight without alerting the police.
 */
public class Solution {
    // two-pass dp
    // accepted
    public int rob2(int[] a) {
        int len = a.length;
        if(len<4){
            // when length less than 4, rob the largest
            int rs = 0;
            for(int i:a)
                if(i>rs)
                    rs = i;
            return rs;
        }
        // dp1 for robbing the first house
        // dp2 for robbing the last house
        int[] dp1 = new int[len],dp2 = new int[len];
        dp1[0] = a[0];
        dp1[1] = a[1];
        dp1[2] = a[0]+a[2];
        for(int i=3;i<len-1;i++)
            dp1[i] = Math.max(dp1[i-3],dp1[i-2])+a[i];
        int max1 = Math.max(dp1[len-2],dp1[len-3]);
        dp2[len-1] = a[len-1];
        dp2[len-2] = a[len-2];
        dp2[len-3] = a[len-1]+a[len-3];
        for(int i=len-4;i>0;i--)
            dp2[i] = Math.max(dp2[i+2],dp2[i+3])+a[i];
        return Math.max(Math.max(dp2[1],dp2[2]),max1);
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,2,1,1};
        Solution s = new Solution();
        System.out.println(s.rob(a));
    }

    // O(1) space solution
    public int rob(int[] a){
        int len = a.length;
        if(len<4){
            // when length less than 4, rob the largest
            int rs = 0;
            for(int i:a)
                if(i>rs)
                    rs = i;
            return rs;
        }
        int prevyes1 = 0,prevno1 = 0,prevno2 = 0,prevyes2 = 0;
        for(int i=0,j=len-1;i<a.length-1;i++,j--){
            int temp1 = prevno1;
            prevno1 = Math.max(prevno1,prevyes1);
            prevyes1 = a[i]+temp1;
            int temp2 = prevno2;
            prevno2 = Math.max(prevyes2,prevno2);
            prevyes2 = temp2+a[j];
        }
        return Math.max(Math.max(Math.max(prevno1,prevyes1),prevno2),prevyes2);
    }
}
