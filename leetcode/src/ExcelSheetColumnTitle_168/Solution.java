package ExcelSheetColumnTitle_168;

/**
 * Given a positive integer,
 * return its corresponding column title as appear in an Excel sheet.
 *
 * For example:
 * 1 -> A
 * 2 -> B
 * 3 -> C
 * ...
 * 26 -> Z
 * 27 -> AA
 * 28 -> AB
 */
public class Solution {
    // I will try recursive and iterative solution

    // Recursive
    public String convertToTitle2(int n) {
        if(n==0) return "";
        return convertToTitle2((n-1)/26)+(char)((n-1)%26+'A');
    }

    // Iterative
    public String convertToTitle(int n){
        String s = "";
        while(n!=0){
            s = (char)((n-1)%26+'A')+s;
            n = (n-1)/26;
        }
        return s;
    }

    public String decimalToBinary(int n){
        if(n==0) return "";
        return decimalToBinary(n/2)+n%2;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.decimalToBinary(14));
    }
}
