package LargestPalindromeProduct_479;

/**
 * Find the largest palindrome made from the product of two n-digit numbers.
 *
 * Since the result could be very large,
 * you should return the largest palindrome mod 1337.
 *
 * Example:
 * Input: 2
 * Output: 987
 * Explanation: 99 x 91 = 9009, 9009 % 1337 = 987
 *
 * Note:
 * The range of n is [1,8].
 */
public class Solution {
    // leetcode optimal solution
    public int largestPalindrome(int n) {
        if(n==1) return 9;
        int hiBound = (int)Math.pow(10,n)-1,lowBound = (int)Math.pow(10,n-1)-1;
        // if n = 3, first half would be 998001/1000 = 998
        // the most tricky part about this problem would be:
        // finding the half
        long half = (long)hiBound*hiBound/(int)Math.pow(10,n);

        long palindrome = 0L;
        boolean found = false;
        while(!found){
            // try each half
            palindrome = createPalindrome(half);
            for(long i=hiBound;i>lowBound;i--){
                if(palindrome/i>hiBound||i*i<palindrome) break;
                if(palindrome%i==0){
                    found = true;
                    break;
                }
            }
            --half;
        }
        return (int)(palindrome%1337);
    }

    private long createPalindrome(Long half){
        return Long.parseLong(half+new StringBuilder(
                half.toString()).reverse().toString());
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.largestPalindrome(6));
    }
}
