package PalindromePermutation_266;

/**
 * Given a string, determine if a permutation of the string could form a palindrome.
 *
 * For example,
 * "code" -> False, "aab" -> True, "carerac" -> True.
 */
public class Solution {
    public boolean canPermutePalindrome(String s) {
        if(s.length()<2) return true;
        char[] ca = s.toCharArray();
        int[] count = new int[128];
        for(char c:ca) count[c]++;
        boolean odd = false;
        for(int i:count){
            if(i%2!=0){
                if(odd) return false;
                else odd = true;
            }
        }
        return true;
    }
}
