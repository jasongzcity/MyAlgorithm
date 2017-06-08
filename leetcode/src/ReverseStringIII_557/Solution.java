package ReverseStringIII_557;

/**
 * Given a string, you need to reverse the order of characters in each word
 * within a sentence while still preserving whitespace and initial word order.
 *
 * Example 1:
 * Input: "Let's take LeetCode contest"
 * Output: "s'teL ekat edoCteeL tsetnoc"
 *
 * Note: In the string, each word is separated by single space and
 * there will not be any extra space in the string.
 */
public class Solution {
    public String reverseWords(String s) {
        char[] a = s.toCharArray();
        int p = 0;
        while(p<a.length){
            int pp = p;
            while(pp+1<a.length&&a[pp+1]!=' ') ++pp;
            // [p...pp] is one word
            for(int i=p,j=pp;i<j;i++,j--){
                char c = a[i];
                a[i] = a[j];
                a[j] = c;
            }
            p = pp+2; // skip space
        }
        return new String(a);
    }
}
