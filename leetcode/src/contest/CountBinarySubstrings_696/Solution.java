package contest.CountBinarySubstrings_696;

/**
 * Give a string s, count the number of non-empty (contiguous)
 * substrings that have the same number of 0's and 1's,
 * and all the 0's and all the 1's in these substrings are grouped consecutively.
 *
 * Substrings that occur multiple times are counted the number of times they occur.
 *
 * Example 1:
 * Input: "00110011"
 * Output: 6
 * Explanation: There are 6 substrings that have equal number of
 * consecutive 1's and 0's: "0011", "01", "1100", "10", "0011", and "01".
 *
 * Notice that some of these substrings repeat and are
 * counted the number of times they occur.
 *
 * Also, "00110011" is not a valid substring because all the 0's (and 1's)
 * are not grouped together.
 *
 * Example 2:
 * Input: "10101"
 * Output: 4
 * Explanation: There are 4 substrings: "10", "01", "10", "01"
 * that have equal number of consecutive 1's and 0's.
 *
 * Note:
 * 1. s.length will be between 1 and 50,000.
 * 2. s will only consist of "0" or "1" characters.
 */
public class Solution {
    // ACC Beats 93%
    public int countBinarySubstrings(String s) {
        if(s.length()<2) return 0;
        char[] ca = s.toCharArray();
        int rs = 0, p = 1, prevcount = 1,curcount = 0;
        while(p<ca.length&&ca[p]==ca[0]){
            p++;
            prevcount++;
        }
        while(p<ca.length){
            curcount = 1;
            p++;
            while(p<ca.length&&ca[p]==ca[p-1]){
                ++p;
                ++curcount;
            }
            rs+=Math.min(prevcount, curcount);
            prevcount = curcount;
        }
        return rs;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.countBinarySubstrings("10101"));
    }
}
