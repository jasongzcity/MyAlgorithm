package PermutationSequence_60;

import java.util.ArrayList;
import java.util.List;

/**
 * The set [1,2,3,…,n] contains a total of n! unique permutations.
 *
 * By listing and labeling all of the permutations in order,
 * We get the following sequence (ie, for n = 3):
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 *
 * Given n and k, return the kth permutation sequence.
 * Note: Given n will be between 1 and 9 inclusive.
 */
public class Solution {
    public static String getPermutation(int n, int k) {
        List<Character> chars = new ArrayList<>(n);
        for (char i = '1'; i <= n+'0'; i++) chars.add(i);
        int[] fac = new int[n];
        fac[0] = 1;
        for (int i = 1; i < n; i++) fac[i] = fac[i-1]*i; // set up factorial
        int index = k-1,div = n-1;
        StringBuilder sb = new StringBuilder(n);
        while(div>0){
            sb.append(chars.remove(index/fac[div]));
            index %= fac[div];
            --div;
        }
        sb.append(chars.get(0));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getPermutation(6,8));
    }
}
