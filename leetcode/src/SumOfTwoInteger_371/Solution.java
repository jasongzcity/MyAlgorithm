package SumOfTwoInteger_371;

/**
 * Calculate the sum of two integers a and b,
 * but you are not allowed to use the operator + and -.
 *
 * Example:
 * Given a = 1 and b = 2, return 3.
 */
public class Solution {
    public int getSum2(int a, int b) {
        int tm = 0,mask = 1,sum = 0,tempA = a&mask,tempB = b&mask,add = 0;
        while(mask!=0){
            if(tempA!=0||tempB!=0){
                tm = tempA^tempB;
                if(tm==0){
                    if(add==1) tm=mask;
                    add = 1;
                }else if(add==1){
                    tm=0;
                }
            }else if(add==1){
                tm = mask;
                add = 0;
            }
            sum|=tm;
            mask<<=1;
            tempA = a&mask;
            tempB = b&mask;
        }
        return sum;
    }

    // A very useful conclusion about bit manipulation:
    // https://discuss.leetcode.com/topic/50315/
    // a-summary-how-to-use-bit-manipulation-to-solve-problems-easily-and-efficiently

    // recursive solution
    public int getSum(int a, int b){
        return b==0?a:getSum(a^b,(a&b)<<1);
    }

    // iterative solution
    public int getSum3(int a,int b){
        while(b!=0){
            int carry = (a&b)<<1;
            a ^= b;
            b = carry;
        }
        return a;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.getSum(-11,-32));
    }
}
