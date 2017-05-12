package SingleNumberII_137;

/**
 * Given an array of integers,
 * every element appears three times except for one,
 * which appears exactly once. Find that single one.
 *
 * Note:
 * Your algorithm should have a linear runtime complexity.
 * Could you implement it without using extra memory?
 */
public class Solution {
    public static int singleNumber(int[] nums) {
        int ones = 0,twos = 0;
        for(int i:nums){
            ones = (ones ^ i) & ~twos;
            twos = (twos ^ i) & ~ones;
        }
        return ones;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,2,3,1,2,1,2};
        System.out.println(singleNumber(a));
    }
}
