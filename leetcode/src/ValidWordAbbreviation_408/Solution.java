package ValidWordAbbreviation_408;

/**
 * Given a non-empty string s and an abbreviation abbr,
 * return whether the string matches with the given abbreviation.
 *
 * A string such as "word" contains only the following valid abbreviations:
 * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1",
 * "1o2", "2r1", "3d", "w3", "4"]
 * Notice that only the above abbreviations are valid abbreviations of the string "word".
 * Any other string is not a valid abbreviation of "word".
 *
 * Note:
 * Assume s contains only lowercase letters and abbr contains only lowercase letters
 * and digits.
 *
 * Example 1:
 * Given s = "internationalization", abbr = "i12iz4n":
 * Return true.
 *
 * Example 2:
 * Given s = "apple", abbr = "a2e":
*
 * Return false.
 */
public class Solution {
    public boolean validWordAbbreviation(String word, String abbr) {
        int pw = 0,pa = 0;
        char[] w = word.toCharArray(),a = abbr.toCharArray();
        while(pw<w.length&&pa<a.length){
            if(a[pa]<'a'&&a[pa]!='0'){
                // digit
                int begin = pa;
                while(pa<a.length&&a[pa]<'a') pa++;
                pw += Integer.parseInt(new String(a,begin,pa-begin));
            }else if(w[pw++]!=a[pa++])
                return false;
        }
        return pw==w.length&&pa==a.length;
    }
}
