package IncreasingTripletSubsequence_334;

/**
 * Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.
 *
 * Formally the function should:
 * Return true if there exists i, j, k
 * such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
 * Your algorithm should run in O(n) time complexity and O(1) space complexity.
 *
 * Examples:
 * Given [1, 2, 3, 4, 5],
 * return true.
 *
 * Given [5, 4, 3, 2, 1],
 * return false.
 */
public class Solution {
    public boolean increasingTriplet(int[] nums) {
        if(nums.length<3) return false;
        Integer a = nums[0], b = null, c = null;
        for(int i=1;i<nums.length;i++){
            int d = nums[i];
            if(b==null){
                if(d>a) b = d;
                else a = d;
            }
            else if(d>b) return true;
            else if(c!=null){
                if(d>c){
                    a = c;
                    b = d;
                    c = null;
                }
                else c = d;
            }
            else if(d>a) b = d;
            else c = d;
        }
        return false;
    }

    // solution above too long
    // could be much simpler!
    public boolean increasingTriplet2(int[] nums){
        int a = Integer.MAX_VALUE, b = Integer.MAX_VALUE;
        for(int i:nums){
            if(i<=a) a = i;
            else if(i<=b) b = i;
            else return true;
        }
        return false;
    }


    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.increasingTriplet(new int[]{2,1,5,0,3}));
    }
}
