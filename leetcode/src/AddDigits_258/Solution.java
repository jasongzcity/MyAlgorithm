package AddDigits_258;

/**
 * Given a non-negative integer num,
 * repeatedly add all its digits until the result has only one digit.
 *
 * For example:
 *
 * Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2.
 * Since 2 has only one digit, return it.
 *
 * Follow up:
 * Could you do it without any loop/recursion in O(1) runtime?
 */
public class Solution {
    // https://en.wikipedia.org/wiki/Digital_root#Congruence_formula
    // This problem is also called "digit root"
    public int addDigits(int num) {
        return 1+(num-1)%9;
    }

    // iterative solution
    public int addDigits2(int num){
        int n = num;
        while(n/10!=0){
            num = n;
            n = 0;
            while(num!=0){
                n+=num%10;
                num/=10;
            }
        }
        return n;
    }
}
