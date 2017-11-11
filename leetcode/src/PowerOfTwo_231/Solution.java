package PowerOfTwo_231;

/**
 * Given an integer, write a function to determine if it is a power of two.
 */
public class Solution {

    // Second session
    // recursive and iterative..
    public boolean isPowerOfTwoII(int n){
        if(n<1) return false;
        while(n!=1){
            if(n%2==0) n/=2;
            else return false;
        }
        return true;
    }

    // remember there are easier ways!!
    // first I can simply use some bit trick.
    // one important bit trick is n&n-1
    // it masks the least significant bit,
    // and since power of two only has
    // one 1 bit. So we can just mask it and see if
    // its 0
    public boolean isPowerOfTwoII2(int n){
        return n>0&&(n&n-1)==0;
    }

    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & n - 1) == 0;
    }

    public boolean isPowerOfTwo2(int n) {
        return n > 0 && (int)Math.pow(2, 30) % n == 0;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.isPowerOfTwoII(7));
    }

    // great explanations:
    // https://discuss.leetcode.com/topic
    // /47195/4-different-ways-to-solve-iterative-recursive-bit-operation-math
}
