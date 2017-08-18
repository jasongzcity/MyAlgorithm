package HappyNumber_202;

import java.util.HashSet;
import java.util.Set;

/**
 * Write an algorithm to determine if a number is "happy".
 *
 * A happy number is a number defined by the following process:
 * Starting with any positive integer,
 * replace the number by the sum of the squares of its digits,
 * and repeat the process until the number equals 1 (where it will stay),
 * or it loops endlessly in a cycle which does not include 1.
 * Those numbers for which this process ends in 1 are happy numbers.
 *
 * Example: 19 is a happy number
 * 1^2 + 9^2 = 82
 * 8^2 + 2^2 = 68
 * 6^2 + 8^2 = 100
 * 1^2 + 0^2 + 0^2 = 1
 */
public class Solution {

    private int[] map = {0,1,4,9,16,25,36,49,64,81};

    public boolean isHappy2(int n) {
        n = sum(n);
        boolean[] set = new boolean[10000];
        while(n!=1){
            if(set[n]) return false;
            else set[n] = true;
            n = sum(n);
        }
        return true;
    }

    private int sum(int n){
        int tm = n;
        n = 0;
        while(tm!=0){
            n+=map[tm%10];
            tm/=10;
        }
        return n;
    }

    // smart faster solution
    // just like finding cycle in the list!
    public boolean isHappy(int n){
        int slow = n,fast = n;
        do{
            slow = sum(slow);
            fast = sum(fast);
            fast = sum(fast);
        }while(fast!=slow);
        return slow==1;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.isHappy(111111));
    }
}
