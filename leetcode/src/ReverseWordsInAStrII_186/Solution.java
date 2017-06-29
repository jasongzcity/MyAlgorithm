package ReverseWordsInAStrII_186;

/**
 * Given an input string, reverse the string word by word.
 * A word is defined as a sequence of non-space characters.
 *
 * The input string does not contain leading or trailing spaces
 * and the words are always separated by a single space.
 *
 * For example,
 * Given s = "the sky is blue",
 * return "blue is sky the".
 *
 * Could you do it in-place without allocating extra space?
 */
public class Solution {
    // This question is just like the
    // Reverse Words In A String (#151)
    public void reverseWords(char[] s) {
        if(s.length<2) return;
        for(int i=0,j=s.length-1;i<j;i++,j--) swap(s,i,j);
        int begin = 0,end = begin;
        while(true){
            while(end<s.length&&s[end]!=' ') ++end;
            for(int i=begin,j=end-1;i<j;i++,j--) swap(s,i,j);
            if(end==s.length) return;
            begin = ++end;
        }
    }

    private void swap(char[] a,int i,int j){
        char tm = a[i];
        a[i] = a[j];
        a[j] = tm;
    }
}
