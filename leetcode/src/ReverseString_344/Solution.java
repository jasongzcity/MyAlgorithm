package ReverseString_344;

/**
 * Write a function that takes a string as input and returns the string reversed.
 *
 * Example:
 * Given s = "hello", return "olleh".
 */
public class Solution {

    // do it in array
    public String reverseString(String s){
        int len = s.length();
        char[] a = new char[len];
        for(int i=0;i<len;i++) a[len-1-i] = s.charAt(i);
        return new String(a);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
//        System.out.println(s.reverseString(""));
        String str = "abcdefg";
        System.out.println(str);
        System.out.println(s.reverseString(str));
    }
}
