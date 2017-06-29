package KthLargestElementInArr_215;

import java.util.PriorityQueue;
import java.util.Random;

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
    // using heap
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq;
        if (k < nums.length >> 1) pq = new PriorityQueue<>((i1, i2) -> (-Integer.compare(i1, i2)));
        else {
            pq = new PriorityQueue<>();
            k = nums.length - k + 1;
        }
        for (int i : nums) pq.add(i);
        int rs = 0;
        for (int i = 0; i < k; i++) rs = pq.poll();
        return rs;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 1, 5, 6, 4, 9, 0, 1010};
        Solution s = new Solution();
        System.out.println(s.findKthLargest2(nums, 2));
        System.out.println(s.findKthLargest2(nums, 8));
    }

    // K-select using partition
    // hi exclusive
    // put elements in descending order
    public int findKthLargest2(int[] a, int k){
        Random rand = new Random();
        int lo = 0,hi = a.length;
        while(true){
            int slow = lo-1,fast = lo;
            swap(a,hi-1,lo+rand.nextInt(a.length)%(hi-lo));
            while(fast<hi-1){
                if(a[fast]>a[hi-1]&&fast>++slow) swap(a,fast,slow);
                fast++;
            }
            swap(a,hi-1,++slow);
            if(slow==k-1) return a[slow];
            if(slow<k-1) lo = slow+1;
            else hi = slow;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
