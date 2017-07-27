package GuessNumberHigherOrLower_374;

/**
 * We are playing the Guess Game. The game is as follows:
 *
 * I pick a number from 1 to n. You have to guess which number I picked.
 *
 * Every time you guess wrong, I'll tell you whether the number is higher or lower.
 *
 * You call a pre-defined API guess(int num) which returns 3 possible results
 * (-1, 1, or 0):
 *
 * -1 : My number is lower
 * 1 : My number is higher
 * 0 : Congrats! You got it!
 *
 * Example:
 * n = 10, I pick 6.
 *
 * Return 6.
 */
public class Solution {

    // dummy method
    private int guess(int num){ return -1; }

    // TLE?????
    // Java's shift operation is slower than divide directly.......
    public int guessNumber(int n) {
        int lo = 1,hi = n;
        while(true){
            int mid = (lo+hi)>>1;
            int rs = guess(mid);
            if(rs==0) return mid;
            else if(rs<0) hi = mid-1;
            else lo = mid+1;
        }
    }

    // this got accepted
    public int guessNumber3(int n) {
        int lo = 1,hi = n;
        while(true){
            int mid = lo+(hi-lo)/2;
            int rs = guess(mid);
            if(rs==0) return mid;
            else if(rs<0) hi = mid-1;
            else lo = mid+1;
        }
    }

    // accepted
    public int guessNumber4(int n) {
        int lo = 1,hi = n;
        while(lo<hi){
            int mid = lo+(hi-lo)/2;
            int rs = guess(mid);
            if(rs>0) lo = mid+1;
            else hi = mid;
        }
        return lo;
    }

    // overflow at "n+1"
    public int guessNumber2(int n){
        int lo = 1,hi = n+1;
        while(lo<hi){
            int mid = (lo+hi)>>1;
            int rs = guess(mid);
            if(rs>0) lo = mid+1;
            else hi = mid;
        }
        return lo;
    }
}
