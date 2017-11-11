package LargestNumber_179;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Given a list of non negative integers,
 * arrange them such that they form the largest number.
 *
 * For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.
 * Note: The result may be very large,
 * so you need to return a string instead of an integer.
 */
public class Solution {
    // thought: first transfer them to strings and
    // then sort them as expect.
    public String largestNumber(int[] nums) {
        String[] sa = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            sa[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(sa, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o2+o1).compareTo(o1+o2);
            }
        });
        String rs = "";
        for(String s:sa) rs+=s;
        return rs.charAt(0)=='0'?"0":rs;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.largestNumber(new int[]{3, 30, 34, 5, 9}));
        System.out.println("3".compareTo("30"));
    }
}
