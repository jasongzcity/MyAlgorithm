package SingleNumberIII_260;

/**
 * Given an array of numbers nums,
 * in which exactly two elements appear only once and
 * all the other elements appear exactly twice. Find the two elements
 * that appear only once.
 *
 * For example:
 * Given nums = [1, 2, 1, 3, 2, 5], return [3, 5].
 *
 * Note:
 * The order of the result is not important.
 * So in the above example, [5, 3] is also correct.
 * Your algorithm should run in linear runtime complexity.
 * Could you implement it using only constant space complexity?
 */
public class Solution {
    public static int[] singleNumber(int[] nums) {
        int[] rs = new int[]{0,0};
        int sum = 0;
        for(int i:nums) sum^=i;
        // now sum = answer1^answer2
        // now all bit 1 in sum must be 1 from answer1 and 0 from answer2(or opposite)
        sum&=-sum; // get the lowest bit 1, so that we can separate ans1 and ans2
        // by using this bit

        for(int i:nums){
            if((i&sum)==0) rs[0]^=i;
            else rs[1]^=i;
        }
        return rs;
    }

    public static void main(String[] args) {
        System.out.println(Bits.Utils.toBits(20));
        System.out.println(Bits.Utils.toBits(-20));
    }
}
