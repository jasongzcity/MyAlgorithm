package PowerOfFour_342;

/**
 * Given an integer (signed 32 bits),
 * write a function to check whether it is a power of 4.
 *
 * Example:
 * Given num = 16, return true. Given num = 5, return false.
 *
 * Follow up: Could you solve it without loops/recursion?
 */
public class Solution {
    // bit manipulation
    public boolean isPowerOfFour(int n) {
        return n >= 1 && (n & (n - 1)) == 0 && (n & 0x55555555) == n;
    }

    // Math
    public boolean isPowerOfFour2(int n){
        return (Math.log10(n)/Math.log10(4))%1==0;
    }

    public static void main(String[] args) {
        System.out.println(Math.log10(17)/Math.log10(4)%1);
        System.out.println(Math.log10(16)/Math.log10(4)%1);
    }
}
