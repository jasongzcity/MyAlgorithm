package ReverseStringII_541;

/**
 * Given a string and an integer k, you need to reverse the first k characters for every 2k characters counting from the start of the string. If there are less than k characters left, reverse all of them. If there are less than 2k but greater than or equal to k characters, then reverse the first k characters and left the other as original.
 *
 * Example:
 * Input: s = "abcdefg", k = 2
 * Output: "bacdfeg"
 *
 * Restrictions:
 * The string consists of lower English letters only.
 * Length of the given string and k will in the range [1, 10000]
 */
public class Solution {
    public String reverseStr(String s, int k) {
        int len = s.length(),begin = 0,end;
        char[] a = new char[len];
        while(begin<len){
            end = begin+k-1;
            if(end>len-1) end = len-1;
            for(int i=begin,j=end;i<=j;i++,j--){
                a[i] = s.charAt(j);
                a[j] = s.charAt(i);
            }
            begin = end+1;
            for(;begin<len&&begin<=end+k;begin++) a[begin] = s.charAt(begin);
        }
        return new String(a);
    }
    // notice: solution above is slower than the optimal because:
    // the optimal do the tranform on the copied array, so that it avoid
    // a lot of judging.

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.reverseStr("abcdefg",2));
    }
}
