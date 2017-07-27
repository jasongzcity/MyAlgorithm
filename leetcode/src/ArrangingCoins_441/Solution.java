package ArrangingCoins_441;

/**
 * You have a total of n coins that you want to form in a staircase shape,
 * where every k-th row must have exactly k coins.
 *
 * Given n, find the total number of full staircase rows that can be formed.
 *
 * n is a non-negative integer and fits within the range of a 32-bit signed integer.
 *
 * Example 1:
 * n = 5
 * The coins can form the following rows:
 * ¤
 * ¤ ¤
 * ¤ ¤
 * Because the 3rd row is incomplete, we return 2.
 *
 * Example 2:
 * n = 8
 * The coins can form the following rows:
 * ¤
 * ¤ ¤
 * ¤ ¤ ¤
 * ¤ ¤
 * Because the 4th row is incomplete, we return 3.
 */
public class Solution {
    // brute force
    public int arrangeCoins2(int n) {
        int count = n,i = 1;
        while(count>-1)
            count-=i++;
        return i-2;
    }

    // optimization:
    // the cost of each row can be determined in O(1) time
    // so we can use binary search
    public int arrangeCoins(int n){
        if(n==1) return 1;
        int lo = 1,hi = n;
        while(lo<hi){
            int mid = lo+((hi-lo)>>>1),sum;
            double half = (double)mid/2;
            sum = (int)((mid+1)*half);
            if(n<sum||(sum/half)!=mid+1) hi = mid; // overflow
            else lo = mid+1;
        }
        return --lo;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.arrangeCoins(1804289383));
    }
}
