package ValidPerfectSquare_367;

/**
 * Given a positive integer num, write a function which returns True
 * if num is a perfect square else False.
 *
 * Note: Do not use any built-in library function such as sqrt.
 *
 * Example 1:
 * Input: 16
 * Returns: True
 *
 * Example 2:
 * Input: 14
 * Returns: False
 *
 * Tips: BinarySearch
 */
public class Solution {
    public boolean isPerfectSquare2(int n) {
        if(n<5){
            // deal with special cases.
            return n==4||n==1;
        }
        // end exclusive
        int begin = 2,end = n>>1;
        while(begin<end){
            int mid = (begin+end)>>1,square = mid*mid;
            if(square==n) return true;
            else if(square>n||square/mid!=mid) end = mid; // too large or overflow
            else begin = mid+1;
        }
        return false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.isPerfectSquare(808201));
    }

    // newton's method
    // https://en.wikipedia.org/wiki/Integer_square_root#Using_only_integer_division
    public boolean isPerfectSquare(int n){
        long x = n;
        while(x*x>n){
            x = (x+n/x)>>1;
        }
        return x*x==n;
    }

    // perfect square = 1+3+5+7+...
    // Unaccepted. TLE
    public boolean isPerfectSquare3(int n){
        int x = 0,k = 1;
        while(x<n){
            x+=k;
            k+=2;
        }
        return x==n;
    }
}
