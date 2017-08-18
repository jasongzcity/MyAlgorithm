package ArrayPartitionI_561;

import java.util.Arrays;
import java.util.Random;

/**
 * Given an array of 2n integers,
 * your task is to group these integers into n pairs of integer,
 * say (a1, b1), (a2, b2), ..., (an, bn)
 * which makes sum of min(ai, bi) for all i from 1 to n as large as possible.
 *
 * Example 1:
 * Input: [1,4,3,2]
 * Output: 4
 * Explanation: n is 2, and the maximum sum of pairs is 4 = min(1, 2) + min(3, 4).
 *
 * Note:
 * n is a positive integer, which is in the range of [1, 10000].
 * All the integers in the array will be in the range of [-10000, 10000].
 */
public class Solution {

    private Random rand = new Random();

    // naive solution
    public int arrayPairSum2(int[] nums) {
        Arrays.sort(nums);
        int rs = 0;
        for(int i=0;i<nums.length;i+=2) rs+=nums[i];
        return rs;
    }

    public int arrayPairSum(int[] nums){
        return partition(nums,0,nums.length-1);
    }

    // We don't have to sort the whole array,
    // we just need to find elements that at position
    // 0,2,...,2n-2

    // partition. lo and hi inclusive
    private int partition(int[] a,int lo,int hi){
        if(lo>hi) return 0;
        if(lo==hi){
            if(lo%2==0) return a[lo];
            else return 0;
        }else if(hi==lo+1){
            // fast route
            if(a[lo]<a[hi]^lo%2!=0) return a[lo];
            else return a[hi];
        }else{
            // randomize first
            swap(a,hi,lo+rand.nextInt(hi-lo+1));
            // now use a[hi] as pivot
            int slow = lo,fast = lo;
            while(fast<hi){
                if(a[fast]<a[hi]) swap(a,slow++,fast);
                fast++;
            }
            swap(a,slow,hi);
            // pivot at slow position now
            int sum = slow%2==0?a[slow]:0;
            return sum+partition(a,lo,slow-1)+partition(a,slow+1,hi);
        }
    }

    private void swap(int[] a,int i,int j){
        int tm = a[i];
        a[i] = a[j];
        a[j] = tm;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.arrayPairSum(new int[]{1,4,3,2}));
    }

    // Since the range is given we could use bucket sort.
}
