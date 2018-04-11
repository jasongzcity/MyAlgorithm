package MaximumXOROfTwoNumberInArray_421;

import java.util.HashSet;
import java.util.Set;

/**
 * Given a non-empty array of numbers, a0, a1, a2, … , an-1, where 0 ≤ ai < 2^31.
 *
 * Find the maximum result of ai XOR aj, where 0 ≤ i, j < n.
 *
 * Could you do this in O(n) runtime?
 *
 * Example:
 *
 * Input: [3, 10, 5, 25, 2, 8]
 *
 * Output: 28
 *
 * Explanation: The maximum result is 5 ^ 25 = 28.
 */
public class Solution {
    // intuitive way: O(n^2)
    // another guessing: use some way to find the bits sequence of each number
    // then compare.
    // Trie? code can be long....
    // optimal solution from leetcode...
    public int findMaximumXOR2(int[] nums) {
        int max = 0, mask = 0;
        for (int i = 31; i >= 0; i--) {
            mask |= (1 << i);
            Set<Integer> set = new HashSet<>();
            for (int num : nums) {
                set.add(num & mask); // reserve Left bits and ignore Right bits
            }

            /* Use 0 to keep the bit, 1 to find XOR
             * 0 ^ 0 = 0
             * 0 ^ 1 = 1
             * 1 ^ 0 = 1
             * 1 ^ 1 = 0
             */
            int tmp = max | (1 << i); // in each iteration, there are pair(s) whoes Left bits can XOR to max
            // a mathematical rule lies here
            // a ^ b = c then c ^ b = a
            for (int prefix : set) {
                // now if tmp ^ prefix = a
                // then tmp = prefix ^ a
                if (set.contains(tmp ^ prefix)) {
                    max = tmp;
                }
            }
        }
        return max;
    }

    // redo
    public int findMaximumXOR(int[] nums){
        int mask = 0, max = 0;
        for(int i=30;i>=0;i--){
            mask|=(1<<i);
            Set<Integer> s = new HashSet<>(nums.length*2);
            for(int num:nums){
                s.add(num&mask);
            }
            int candidate = (max | (1<<i));
            for(int j:s){
                if(s.contains(candidate ^ j)){
                    max = candidate;
                    break;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = new int[]{3, 10, 5, 25, 2, 8};
        System.out.println(s.findMaximumXOR(a));
    }
}
