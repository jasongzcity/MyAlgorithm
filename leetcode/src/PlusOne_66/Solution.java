package PlusOne_66;

import java.util.Arrays;

/**
 * Given a non-negative integer represented as a non-empty array of digits,
 * plus one to the integer.
 *
 * You may assume the integer do not contain any leading zero,
 * except the number 0 itself.
 *
 * The digits are stored such that the most
 * significant digit is at the head of the list.
 */
public class Solution {
    public static int[] plusOne(int[] digits) {
        //int plus = 1;
        for(int i=digits.length-1;i>-1;i--){
            int sum = digits[i]+1;
            digits[i] = sum%10;
            if(sum<10) break;
            if(sum==10&&i==0){ // expand array
                int[] n = new int[digits.length+1];
                System.arraycopy(digits,0,n,1,digits.length);
                n[0] = 1;
                return n;
            }
        }
        return digits;
    }

    public static void main(String[] args) {
        int[] a = new int[]{9,9,9};
        System.out.println(Arrays.toString(plusOne(a)));
        int[] a1 = new int[]{1,9,9,9,9};
        System.out.println(Arrays.toString(plusOne(a1)));
    }
}
