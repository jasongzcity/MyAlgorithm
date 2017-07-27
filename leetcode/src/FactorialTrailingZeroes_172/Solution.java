package FactorialTrailingZeroes_172;

/**
 * Given an integer n, return the number of trailing zeroes in n!.
 *
 * Note: Your solution should be in logarithmic time complexity.
 */
public class Solution {
    // iterative
    // k would overflow...
    // but great explanation:
    // https://discuss.leetcode.com/topic/6848/my-explanation-of-the-log-n-solution
    public int trailingZeroes2(int n) {
        int rs = 0,power;
        for(int k=5;(power=n/k)>0;k*=5) rs+=power;
        return rs;
    }

    public int trailingZeroes3(int n){
        int rs = 0;
        while(n>0){
            n/=5;
            rs+=n;
        }
        return rs;
    }

    // recursive
    public int trailingZeroes(int n){
        return n==0?0:n/5+trailingZeroes(n/5);
    }
}
