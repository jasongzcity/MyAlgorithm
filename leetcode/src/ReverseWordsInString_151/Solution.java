package ReverseWordsInString_151;

import java.util.Arrays;

/**
 * Given an input string, reverse the string word by word.
 *
 * For example,
 * Given s = "the sky is blue",
 * return "blue is sky the".
 *
 * Update (2015-02-12):
 * For C programmers: Try to solve it in-place.
 *
 * Clarification:
 *
 * What constitutes a word?
 * A sequence of non-space characters constitutes a word.
 *
 * Could the input string contain leading or trailing spaces?
 * Yes. However, your reversed string should not contain leading or trailing spaces.
 *
 * How about multiple spaces between two words?
 * Reduce them to a single space in the reversed string.
 *
 */
public class Solution {
    // O(n) space
    public String reverseWords(String s) {
        s = s.trim();
        int len = s.length();
        if(len<2) return s;
        StringBuilder sb = new StringBuilder(len);
        char[] value = s.toCharArray();
        int i = s.length()-1,j = i;
        while(true){
            while(j>-1&&value[j]!=' ') --j;
            sb.append(value,j+1,i-j).append(' ');
            if(j==-1) break;
            while(value[j]==' ') --j;
            i = j;
        }
        sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }

    public static void main(String[] args) {
        String src = "  make   it   come  true    ";
        System.out.println(src);
        Solution s = new Solution();
        src = s.reverseWords2(src);
        System.out.println(src);
        System.out.println();
        src = "say hello";
        System.out.println(src);
        src = s.reverseWords2(src);
        System.out.println(src);
//        System.out.println("test:");
//        char[] origin = "aa    abcdefghi".toCharArray();
//        System.out.println(Arrays.toString(origin));
//        s.reverseAndMoveForward(origin,6,14,3);
//        System.out.println(Arrays.toString(origin));
//
//        char[] o = "     abc".toCharArray();
//        System.out.println(Arrays.toString(o));
//        s.reverseAndMoveForward(o,5,7,5);
//        System.out.println(Arrays.toString(o));
    }

    // two-pass in-place solution
    public String reverseWords2(String s){
        char[] a = s.toCharArray();
        for(int i=0,j=a.length-1;i<j;i++,j--) swap(a,i,j); // reverse the whole string
        int begin=0,end,forward = 0,wordEnd = -1;
        while(true){
            while(begin<a.length&&a[begin]==' ') { begin++; forward++; }
            if(begin>=a.length) break;
            end = begin;
            while(end<a.length&&a[end]!=' ') ++end;
            reverseAndMoveForward(a,begin,end-1,forward);
            wordEnd = end-1-forward; // record the word's end
            begin = end+1;
        }
        return wordEnd==-1?"":new String(a,0,wordEnd+1);
    }

    private void swap(char[] a,int i,int j){
        char tm = a[i];
        a[i] = a[j];
        a[j] = tm;
    }

    // begin and end inclusive
    private void reverseAndMoveForward(char[] a,int begin,int end,int forward){
        int i = begin,j = end,f = forward;
        while(forward>0&&i<=j) a[i-forward--] = a[j--];
        while(i<j) swap(a,i++,j--);
        if(end-f+1<a.length) a[end-f+1] = ' '; // add space after one word.
    }
}
