package PalindromicSubstring_647;

/**
 * Given a string, your task is to count how many palindromic substrings in this string.
 *
 * The substrings with different start indexes or end indexes are
 * counted as different substrings even they consist of same characters.
 *
 * Example 1:
 * Input: "abc"
 * Output: 3
 * Explanation: Three palindromic strings: "a", "b", "c".
 *
 * Example 2:
 * Input: "aaa"
 * Output: 6
 * Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 *
 * Note:
 * The input string length won't exceed 1000.
 */
public class Solution {
    // this problem could also ask to return the
    // palindromic string in a list.
    // there must be connections between these palindromes, or that
    // there are n^2 substrings you may need to check n^2 times....
    // in total n^3 time complexity....
    // duplicate subproblems: say "abba"
    // if you already know "bb" is palindrome then all you need to do is spanning..
    public int countSubstrings(String s) {
        char[] a = s.toCharArray();
        int counter = a.length;
        for(int i=0;i<a.length;i++){
            int left = i-1, right = i+1;
            counter+=count(a, left, right);
            if(i+1<a.length&&a[i+1]==a[i]){
                ++counter;
                left = i-1;
                right = i+2;
                counter+=count(a, left, right);
            }
        }
        return counter;
    }

    private int count(char[] a, int left, int right) {
        int counter = 0;
        while (left >= 0 && right < a.length) {
            if (a[left--] == a[right++]) ++counter;
            else break;
        }
        return counter;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.countSubstrings("aaa"));
    }
}
