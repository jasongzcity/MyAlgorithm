package Sqrtx_69;

/**
 * Implement int sqrt(int x).
 * Compute and return the square root of x.
 */
public class Solution {
    public static int mySqrt(int x) {
        if (x < 2) return x; // 0 and 1
        int hi = x / 2, lo = 2;
        while (hi >= lo) {
            int mid = lo + ((hi - lo) >> 1);
            int sqrt = mid*mid;
            if (sqrt == x) return mid;
            if (sqrt > x||sqrt/mid!=mid) hi = mid - 1;
            else lo = mid + 1;
        }
        return hi;
    }

    public static void main(String[] args) {
        System.out.println(mySqrt(2147395599));
        System.out.println(mySqrt2(9));
    }

    public static int mySqrt2(int x) {
        if(x<2) return x;
        int hi = x/2+1,lo = 2; // hi not inclusive
        while(lo<hi){
            int mid = lo + ((hi - lo) >> 1);
            int sqrt = mid*mid;
            if(sqrt>x||sqrt/mid!=mid) hi = mid;
            else lo = mid+1;
        }
        return --lo;
    }
}