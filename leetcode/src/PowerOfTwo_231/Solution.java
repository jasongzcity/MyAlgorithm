package PowerOfTwo_231;

/**
 * Given an integer, write a function to determine if it is a power of two.
 */
public class Solution {
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & n - 1) == 0;
    }

    public boolean isPowerOfTwo2(int n) {
        return n > 0 && (int)Math.pow(2, 30) % n == 0;
    }

    // great explanations:
    // https://discuss.leetcode.com/topic
    // /47195/4-different-ways-to-solve-iterative-recursive-bit-operation-math
}
