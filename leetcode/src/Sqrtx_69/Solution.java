package Sqrtx_69;

/**
 * Implement int sqrt(int x).
 * Compute and return the square root of x.
 */
public class Solution {

    // second session.
    public static int mySqrt(int x){
        if(x<2) return x;
        int lo = 2, hi = x;
        while(lo<hi){
            int mid = (hi-lo)/2 + lo; // avoid overflow
            int sqr = mid*mid;
            if(sqr/mid!=mid||sqr>x) hi = mid;
            else lo = mid+1;
        }
        return lo-1;
    }

    public static int mySqrt3(int x) {
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