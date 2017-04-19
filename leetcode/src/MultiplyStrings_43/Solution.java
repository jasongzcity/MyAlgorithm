package MultiplyStrings_43;

/**
 * Given two non-negative integers num1 and num2 represented as strings,
 * return the product of num1 and num2.
 *
 * Note:
 * The length of both num1 and num2 is < 110.
 * Both num1 and num2 contains only digits 0-9.
 * Both num1 and num2 does not contain any leading zero.
 * You must not use any built-in BigInteger library or convert the inputs
 * to integer directly.
 */
public class Solution {
    // inspired by the most voted solution on leetcode
    // which has very detailed explanation:
    // https://leetcode.com/problems/multiply-strings/#/solutions
    public static String multiply(String num1, String num2) {
        int len1 = num1.length(),len2 = num2.length();
        int[] rs = new int[len1+len2];
        for(int i=len1-1;i>-1;i--){
            for(int j=len2-1;j>-1;j--){
                int p1 = i+j,p2 = i+j+1;
                int sum = (num1.charAt(i)-'0')*(num2.charAt(j)-'0') + rs[p2];
                rs[p1] += sum/10;
                rs[p2] = sum%10;
            }
        }

        StringBuilder sb = new StringBuilder(len1+len2);
        for (int i = 0; i < rs.length; i++)
            if(sb.length()!=0||rs[i]!=0) // skip heading 0
                sb.append(rs[i]);
        return sb.length()==0?"0":sb.toString();
    }
}
