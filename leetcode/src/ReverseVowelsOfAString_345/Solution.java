package ReverseVowelsOfAString_345;

/**
 * Write a function that takes a string as input
 * and reverse only the vowels of a string.
 *
 * Example 1:
 * Given s = "hello", return "holle".
 *
 * Example 2:
 * Given s = "leetcode", return "leotcede".
 *
 * Note:
 * The vowels does not include the letter "y".
 */
public class Solution {
    public String reverseVowels(String s) {
        if(s.length()<2) return s;
        char[] a = s.toCharArray();
        int left = 0,right = a.length-1;
        while(true){
            while(left<a.length&&!isVowel(a[left])) left++;
            while(right>-1&&!isVowel(a[right])) right--;
            if(left>=right) break;
            swap(a,left++,right--);
        }
        return new String(a);
    }

    private void swap(char[] a,int i,int j){
        char tm = a[i];
        a[i] = a[j];
        a[j] = tm;
    }

    private boolean isVowel(char c){
        return c=='a'||c=='e'||c=='i'||c=='o'||c=='u'
                ||c=='A'||c=='E'||c=='I'||c=='O'||c=='U';
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.reverseVowels("leetcode"));
    }
}
