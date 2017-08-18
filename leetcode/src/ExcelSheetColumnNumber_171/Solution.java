package ExcelSheetColumnNumber_171;

/**
 * Related to question Excel Sheet Column Title
 *
 * Given a column title as appear in an Excel sheet,
 * return its corresponding column number.
 *
 * For example:
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Z -> 26
 * AA -> 27
 * AB -> 28
 */
public class Solution {

    // Iterative
    public int titleToNumber(String s) {
        int rs = 0;
        for(int i=0;i<s.length();i++)
            rs = rs*26+s.charAt(i)-'A'+1;
        return rs;
    }
}
