package LengthOfTheLastWord_58;

/**
 * Given a string s consists of upper/lower-case alphabets
 * and empty space characters ' ', return the length of last word in the string.
 *
 * If the last word does not exist, return 0.
 *
 * Note: A word is defined as a character sequence
 * consists of non-space characters only.
 *
 * For example,
 * Given s = "Hello World",
 * return 5.
 */
public class Solution {
    public static int lengthOfLastWord(String s) {
        int wordlen = 0, count = 0;
        for (int i = 0; i < s.length(); i++) {
            while (i < s.length() && s.charAt(i) != ' ') {
                count++;
                wordlen = count;
                i++;
            }
            count = 0; // reset;
        }
        return wordlen;
    }

    public static int lengthOfLastWord2(String s) {
        int i = s.length()-1;
        while(i>-1&&s.charAt(i)==' ') i--;
        // Now that i at the last char of the last word or -1;
        int lastIndex = i;
        while(i>-1&&s.charAt(i)!=' ') i--;
        return lastIndex-i;
    }
}
