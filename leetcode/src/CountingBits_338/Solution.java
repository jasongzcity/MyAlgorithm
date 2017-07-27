package CountingBits_338;

import java.util.Arrays;

/**
 * Given a non negative integer number num.
 * For every numbers i in the range 0 ≤ i ≤ num calculate the
 * number of 1's in their binary representation and return them as an array.
 *
 * Example:
 * For num = 5 you should return [0,1,1,2,1,2].
 *
 * Follow up:
 *
 * It is very easy to come up with a solution with run time O(n*sizeof(integer)).
 * But can you do it in linear time O(n) /possibly in a single pass?
 * Space complexity should be O(n).
 * Can you do it like a boss? Do it without using any builtin function
 * like __builtin_popcount in c++ or in any other language.
 */
public class Solution {

    // numerous solutions at :
    // https://leetcode.com/problems/counting-bits/#/solution

    // naive solution
    public int[] countBits(int num) {
        int[] rs = new int[num+1];
        for(int i=1;i<=num;i++){
            if((i&1)==1) rs[i] = rs[i-1]+1;
            else{
                int prev = i-1,count = 1,mask = 2;
                while((prev&mask)!=0){
                    ++count;
                    mask*=2;
                }
                rs[i] = rs[i-1]-count+1;
            }
        }
        return rs;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(Arrays.toString(s.countBits3(8)));
        System.out.println(Arrays.toString(s.countBits3(16)));
    }

    // solution called "popcount" or hamming weight
    // This is slow
    private int popcount(int i){
        int count = 0;
        for(;i>0;++count) i&=i-1;
        return count;
    }

    public int[] countBits2(int num){
        int[] rs = new int[num+1];
        for(int i=1;i<=num;i++) rs[i] = popcount(i);
        return rs;
    }

    // solution based on the difference on most significant bit
    // its not one-pass solution though!
    // we have transition equation:
    // P(x+b)=P(x)+1, b = 2^​m > x
    public int[] countBits3(int num){
        int[] rs = new int[num+1];
        int base = 0,jump = 1;
        while(jump<=num){
            while(base<jump&&base+jump<=num){
                rs[base+jump] = rs[base]+1;
                base++;
            }
            base = 0;
            jump*=2;
        }
        return rs;
    }

    // solution based on the difference on least significant bit
    public int[] countBits4(int num){
        int[] rs = new int[num+1];
        for(int i=1;i<=num;i++) rs[i] = rs[i>>1]+(i&1);
        return rs;
    }

    // solution based on the last set bit
    // (recall binary index tree about last set bit)
    public int[] countBits5(int num){
        int[] rs = new int[num+1];
        for(int i=1;i<=num;i++){
            // use bit trick to mask the last set bit
            rs[i] = rs[i&(i-1)]+1;
        }
        return rs;
    }
}
