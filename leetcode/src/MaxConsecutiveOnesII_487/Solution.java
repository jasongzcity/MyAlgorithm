package MaxConsecutiveOnesII_487;

/**
 * Given a binary array, find the maximum number of consecutive 1s
 * in this array if you can flip at most one 0.
 *
 * Example 1:
 * Input: [1,0,1,1,0]
 * Output: 4
 * Explanation:
 * Flip the first zero will get the the maximum number of consecutive 1s.
 * After flipping, the maximum number of consecutive 1s is 4.
 *
 * Note:
 * The input array will only contain 0 and 1.
 * The length of input array is a positive integer and will not exceed 10,000
 * Follow up:
 * What if the input numbers come in one by one as an infinite stream?
 * In other words, you can't store all numbers coming from the stream as
 * it's too large to hold in memory. Could you solve it efficiently?
 */
public class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        if(nums.length==0) return 0;
        int prev = -1,len = 0,max = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==1) ++len;
            else{
                max = Math.max(max,len+prev+1);
                prev = len;
                len = 0;
            }
        }
        max = Math.max(max,len+prev+1);
        return max;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = {1,0,1,1,0,1};
        int[] b = {1,1,1};
        int[] c = {0,1,1};
        int[] d = {0,0,1,1};

        System.out.println(s.findMaxConsecutiveOnes(a));
        System.out.println(s.findMaxConsecutiveOnes(b));
        System.out.println(s.findMaxConsecutiveOnes(c));
        System.out.println(s.findMaxConsecutiveOnes(d));
    }
}
