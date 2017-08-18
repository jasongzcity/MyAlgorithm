package AddString_415;

/**
 * Given two non-negative integers num1 and num2 represented as string,
 * return the sum of num1 and num2.
 *
 * Note:
 * The length of both num1 and num2 is < 5100.
 * Both num1 and num2 contains only digits 0-9.
 * Both num1 and num2 does not contain any leading zero.
 * You must not use any built-in BigInteger library or
 * convert the inputs to integer directly.
 */
public class Solution {
    public String addStrings(String num1, String num2) {
        int len = Math.max(num1.length(),num2.length());
        char[] str = new char[len+1],n1 = num1.toCharArray(),n2 = num2.toCharArray();
        int p1 = n1.length-1,p2 = n2.length-1,ps = str.length-1,plus = 0;
        while(p1>-1||p2>-1||plus!=0){
            int i1=0,i2=0;
            if(p1>-1) i1 = n1[p1--]-'0';
            if(p2>-1) i2 = n2[p2--]-'0';
            int sum = i1+i2+plus;
            if(sum>=10){
                plus = 1;
                sum-=10;
            }else plus = 0;
            str[ps--] = (char)(sum+'0');
        }
        return new String(str,ps+1,str.length-1-ps);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.addStrings("0","0"));
        System.out.println(s.addStrings("19","81"));
    }
}
