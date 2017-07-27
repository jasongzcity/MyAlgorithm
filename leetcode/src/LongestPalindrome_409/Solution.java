package LongestPalindrome_409;

/**
 * Given a string which consists of lowercase or uppercase letters,
 * find the length of the longest palindromes that can be built with those letters.
 *
 * This is case sensitive, for example "Aa" is not considered a palindrome here.
 *
 * Note:
 * Assume the length of given string will not exceed 1,010.
 *
 * Example:
 *
 * Input:
 * "abccccdd"
 * Output:
 * 7
 *
 * Explanation:
 * One longest palindrome that can be built is "dccaccd", whose length is 7.
 */
public class Solution {
    public int longestPalindrome(String s) {
        if(s.length()<2) return s.length();
        char[] a = s.toCharArray();
        int[] map = new int[58];
        for(char c:a) map[c-'A']++;
        int sum = 0;
        boolean hasOdd = false;
        for(int i:map){
            if(!hasOdd){
                if(i%2!=0) hasOdd = true;
                sum+=i;
            }else sum+=i/2*2;
        }
        return sum;
    }
}
