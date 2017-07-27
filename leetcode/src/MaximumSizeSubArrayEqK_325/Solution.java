package MaximumSizeSubArrayEqK_325;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array a and a target value k,
 * find the maximum length of a subarray that sums to k.
 * If there isn't one, return 0 instead.
 *
 * Note:
 * The sum of the entire nums array is guaranteed to fit
 * within the 32-bit signed integer range.
 *
 * Example 1:
 * Given nums = [1, -1, 5, -2, 3], k = 3,
 * return 4. (because the subarray [1, -1, 5, -2] sums to 3 and is the longest)
 *
 * Example 2:
 * Given nums = [-2, -1, 2, 1], k = 1,
 * return 2. (because the subarray [-1, 2] sums to 1 and is the longest)
 *
 * Follow Up:
 * Can you do it in O(n) time?
 */
public class Solution {
    // most voted leetcode solution
    public int maxSubArrayLen2(int[] a, int k) {
        int longest = 0, sum = 0;
        Map<Integer, Integer> map = new HashMap<>(a.length << 1);
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
            Integer begin = map.get(sum - k);
            if (sum == k) longest = i + 1;
            else if (begin != null && i - begin > longest) longest = i - begin;
            map.putIfAbsent(sum, i); // we want to keep the begin index at leftmost position
        }
        return longest;
    }

    // faster solution
    public int maxSubArrayLen(int[] a, int k){
        int longest = 0;
        Map<Integer, Integer> map = new HashMap<>(a.length << 1);
        int[] sums = new int[a.length+1];
        map.put(0,0);
        // array sums represents the sum of the previous i elements.
        // so nums[0] is left 0 on purpose.
        for(int i=1;i<=a.length;i++){
            sums[i] = sums[i-1]+a[i-1];
            map.put(sums[i],i); // keep sums at the rightmost position
        }

        for(int i=0;i<a.length;i++){
            Integer rightEnd = map.get(sums[i]+k);
            if(rightEnd!=null&&rightEnd-i>longest){
                if(rightEnd==a.length) return rightEnd-i;
                else longest = rightEnd-i;
            }
        }
        return longest;
    }
}
