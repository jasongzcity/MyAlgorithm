package ValidPalindromeII_680;

/**
 * Given a non-empty string s, you may delete at most one character.
 * Judge whether you can make it a palindrome.
 *
 * Example 1:
 * Input: "aba"
 * Output: True
 *
 * Example 2:
 * Input: "abca"
 * Output: True
 * Explanation: You could delete the character 'c'.
 *
 * Note:
 * The string will only contain lowercase characters a-z.
 * The maximum length of the string is 50000.
 */
public class Solution {
    // unaccepted...
    // consider "claalcl", the program will skip the first 'c', then cause wrong output..
    public boolean validPalindrome2(String str) {
        char[] s = str.toCharArray();
        boolean flag = true;
        for(int l=0,r=s.length-1;l<r;++l,--r){
            if(s[l]!=s[r]){
                if(!flag) return false;
                if(s[l+1]==s[r]) ++l;
                else if(s[r-1]==s[l]) --r;
                else return false;
                flag = false;
            }
        }
        return true;
    }

    // modified
    public boolean validPalindrome(String str){
        char[] s = str.toCharArray();
        for(int l=0,r=s.length-1;l<r;l++,r--)
            if(s[l]!=s[r])
                return check(s, l, r - 1) || check(s, l + 1, r);
        return true;
    }

    private boolean check(char[] s,int l,int r){
        while(l<r)
            if(s[l++]!=s[r--])
                return false;
        return true;
    }


    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.validPalindrome("claalcl"));
    }
}
