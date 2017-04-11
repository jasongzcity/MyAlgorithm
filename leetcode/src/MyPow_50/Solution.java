package MyPow_50;

/**
 * Implement pow(x, n).
 */
public class Solution {
    public static double myPow(double x, int n) {
        if(n<0) x = 1/x;
        return n%2==0?powRecur(x*x,n/2):x*powRecur(x*x,n/2);
    }

    private static double powRecur(double x,int n){
        if(n==0) return 1; // base
        return n%2==0?powRecur(x*x,n/2):x*powRecur(x*x,n/2);
    }

    public static void main(String[] args) {
//        System.out.println(myPow(8.84372,-5));
//        System.out.println(myPowIter(8.84372,-5));
        System.out.println(myPowIter(4,3));
        System.out.println(myPowIter(4,-3));
    }

    // Iterative version
    public static double myPowIter(double x,int n){
        double rs = 1;
        if(n<0){
            x = 1/x;
            n = -(n+1);     // to avoid n = Integer.MIN_VALUE(-(2^10))
            rs *= x;
        }
        while(n>0){
            if((n&1)==1) rs *= x;
            x *= x;
            n >>= 1;
        }
        return rs;
    }
}