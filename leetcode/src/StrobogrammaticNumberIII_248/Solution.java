package StrobogrammaticNumberIII_248;

/**
 * A strobogrammatic number is a number that looks the same when
 * rotated 180 degrees (looked at upside down).
 *
 * Write a function to count the total strobogrammatic numbers that
 * exist in the range of low <= num <= high.
 *
 * For example,
 * Given low = "50", high = "100", return 3.
 * Because 69, 88, and 96 are three strobogrammatic numbers.
 *
 * Note:
 * Because the range might be a large number,
 * the low and high numbers are represented as string.
 */
public class Solution {

    private int counter = 0;

    private char[][] cm = new char[][]{
            {'0','0'},
            {'1','1'},
            {'6','9'},
            {'8','8'},
            {'9','6'}
    };

    // low and hi inclusive
    public int strobogrammaticInRange(String low, String high) {
        int[] count = {0};
        for (int len = low.length(); len <= high.length(); len++) {
            char[] c = new char[len];
            dfs(low, high, c, 0, len - 1, count);
        }
        return count[0];
    }

    public void dfs(String low, String high , char[] c, int left, int right,
                    int[] count) {
        if (left > right) {
            String s = new String(c);
            if ((s.length() == low.length() && s.compareTo(low) < 0) ||
                    (s.length() == high.length() && s.compareTo(high) > 0)) {
                return;
            }
            count[0]++;
            return;
        }
        for (char[] p : cm) {
            c[left] = p[0];
            c[right] = p[1];
            if (c.length != 1 && c[0] == '0') {
                continue;
            }
            if (left == right && p[0] != p[1]) {
                continue;
            }
            dfs(low, high, c, left + 1, right - 1, count);
        }
    }
}
