package SlidingWindowMaximum_239;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given an array nums, there is a sliding window of size k
 * which is moving from the very left of the array to the very right.
 * You can only see the k numbers in the window.
 * Each time the sliding window moves right by one position.
 *
 * For example,
 * Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
 *
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7      3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 *
 * Therefore, return the max sliding window as [3,3,5,5,6,7].
 *
 * Note:
 * You may assume k is always valid,
 * ie: 1 ≤ k ≤ input array's size for non-empty array.
 *
 * Follow up:
 * Could you solve it in linear time?
 */
public class Solution {
    // most voted solution on leetcode
    public int[] maxSlidingWindow(int[] a, int k) {
        int n = a.length,ri = 0;
        if(k==0) return new int[0];
        int[] r = new int[n-k+1];
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            // remove numbers out of range k
            if (!q.isEmpty() && q.peek() < i - k + 1) q.poll();

            // remove smaller numbers in k range as they are useless
            // This is the key point! Keep polling smaller element from the end
            // so that maintain the element in descending order.
            while (!q.isEmpty() && a[q.peekLast()] < a[i]) q.pollLast();

            q.add(i);
            if (i >= k - 1) r[ri++] = a[q.peek()];
        }
        return r;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,3,-1,-3,5,3,-1,7};
        Solution s = new Solution();
        a = s.maxSlidingWindow2(a,3);
    }

    // two-pass solution without using a deque
    // deque is slow.
    public int[] maxSlidingWindow2(int[] a, int k){
        int len = a.length;
        if(len==0) return new int[0];
        int[] maxLeft = new int[len],maxRight = new int[len],rs = new int[len-k+1];
        maxLeft[0] = a[0];
        maxRight[len-1] = a[len-1];
        for(int i=1,j=len-2;i<len;i++,j--){
            maxLeft[i] = i%k==0?a[i]:Math.max(maxLeft[i-1],a[i]);
            maxRight[j] = j%k==0?a[j]:Math.max(maxRight[j+1],a[j]);
        }
        for(int i=0,j=0;i+k<=len;i++)
            rs[j++] = Math.max(maxRight[i],maxLeft[i+k-1]);
        return rs;
    }
}
