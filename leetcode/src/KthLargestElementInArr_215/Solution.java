package KthLargestElementInArr_215;

import java.util.PriorityQueue;

/**
 * Find the kth largest element in an unsorted array.
 * Note that it is the kth largest element in the sorted order,
 * not the kth distinct element.
 *
 * For example,
 * Given [3,2,1,5,6,4] and k = 2, return 5.
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
 */
public class Solution {
    // Accepted
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq;
        if(k<nums.length>>1) pq = new PriorityQueue<>((i1,i2)->(-Integer.compare(i1,i2)));
        else{
            pq = new PriorityQueue<>();
            k = nums.length-k+1;
        }
        for(int i:nums) pq.add(i);
        int rs = 0;
        for(int i=0;i<k;i++) rs = pq.poll();
        return rs;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3,2,1,5,6,4,9,0,1010};
        Solution s = new Solution();
        System.out.println(s.findKthLargest(nums,2));
        System.out.println(s.findKthLargest(nums,8));
    }
}
