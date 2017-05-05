package LongestConsecutiveSequence_128;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an unsorted array of integers,
 * find the length of the longest consecutive elements sequence.
 *
 * For example,
 * Given [100, 4, 200, 1, 3, 2],
 * The longest consecutive elements sequence is [1, 2, 3, 4].
 * Return its length: 4.
 *
 * Your algorithm should run in O(n) complexity.
 */
public class Solution {
    // notice: This solution can be simplified
    public static int longestConsecutive(int[] nums) {
        int len = nums.length, maxLen = 0;
        Map<Integer, Integer> map = new HashMap<>(len);
        for (int i = 0; i < len; i++) {
            int tar = nums[i];
            if (!map.containsKey(tar)) {
                Integer forwardlength = map.get(tar + 1);
                Integer previouslength = map.get(tar - 1);
                if (forwardlength != null) {
                    if (previouslength != null) {
                        int totalLen = forwardlength + previouslength + 1;
                        map.put(tar + forwardlength, totalLen);
                        map.put(tar - previouslength, totalLen);
                        map.put(tar,1);
                        if (totalLen > maxLen) maxLen = totalLen;
                    } else {
                        int newlen = forwardlength + 1;
                        map.put(tar + forwardlength, newlen);
                        map.put(tar, newlen);
                        if (newlen > maxLen) maxLen = newlen;
                    }
                } else {
                    if (previouslength != null) {
                        int newlen = previouslength + 1;
                        map.put(tar - previouslength, newlen);
                        map.put(tar, newlen);
                        if (newlen > maxLen) maxLen = newlen;
                    } else {
                        map.put(tar, 1);
                        if (maxLen < 1) maxLen = 1;
                    }
                }
            }
        }
        return maxLen;
    }


    public static void main(String[] args) {
        int[] a1 = new int[]{4,0,-4,-2,2,5,2,0,-8,-8,-8,-8,-1,7,4,5,5,-4,6,6,-3};
        System.out.println(longestConsecutive(a1));
    }
}
