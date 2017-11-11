package PowerOfThree_326;

/**
 * Given an integer, write a function to determine if it is a power of three.
 *
 * Follow up:
 * Could you do it without using any loop / recursion?
 */
public class Solution {

    // second session
    // I will skip the iterative solution
    // another maths trick to solve this kind of "power"
    // questions
    public boolean isPowerOfThreeII(int n){
        return n>0&&((long)Math.pow(3,30)/n)==0;
    }

    // iterative solution
    public boolean isPowerOfThree(int n) {
        if(n<=0) return false;
        while(n%3==0) n/=3;
        return n==1;
    }

    // O(1) solution
    public boolean isPowerOfThree2(int n){
        return n>0&&(long)Math.pow(3,30)%n==0;
    }

    // Math...
    public boolean isPowerOfThree3(int n) {
        return (Math.log10(n) / Math.log10(3)) % 1 == 0;
    }
}
