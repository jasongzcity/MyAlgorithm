package AddBinary_67;

/**
 * Given two binary strings, return their sum (also a binary string).
 *
 * For example,
 * a = "11"
 * b = "1"
 * Return "100".
 */
public class Solution {
    public static String addBinary(String a, String b) {
        int alen = a.length(),blen = b.length();
        int len = Math.max(alen,blen);
        int i = alen-1,j = blen-1,carry = 0;
        StringBuilder sb = new StringBuilder(len+1);
        while(i>-1||j>-1){
            int anum = i>-1?a.charAt(i)-'0':0;
            int bnum = j>-1?b.charAt(j)-'0':0;
            int sum = anum+bnum+carry;
            sb.append(sum%2);
            carry = sum/2;
            i--;
            j--;
        }
        if(carry==1) sb.append(carry);
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        String a = "1111";
        String b = "1";
        System.out.println(addBinary(a,b));
    }
}
