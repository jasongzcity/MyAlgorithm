package SingleNumber_136;

/**
 * Given an array of integers,
 * every element appears twice except for one. Find that single one.
 *
 * Note:
 * Your algorithm should have a linear runtime complexity.
 * Could you implement it without using extra memory?
 */
public class Solution {
    // solution using map would be easy..
    public int singleNumber(int[] nums) {
        int rs = 0;
        for(int i:nums) rs^=i;
        return rs;
    }

    public static void main(String[] args) {
        System.out.println(4^1);
    }
}
