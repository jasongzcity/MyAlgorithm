package NumberOf1Bits_191;

/**
 * Write a function that takes an unsigned integer and returns
 * the number of ’1' bits it has
 * (also known as the Hamming weight).
 *
 * For example, the 32-bit integer ’11' has binary representation
 * 00000000000000000000000000001011, so the function should return 3.
 */
public class Solution {
    // check out fastest solutions at wikipedia:
    // https://en.wikipedia.org/wiki/Hamming_weight
    // I am implementing the easiest one
    public int hammingWeight(int n) {
        int count = 0;
        while(n!=0){
            n&=n-1; // bit trick, mask the last set bit.
            ++count;
        }
        return count;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.hammingWeight(0b10000000000000000000000000000000));
    }
}
