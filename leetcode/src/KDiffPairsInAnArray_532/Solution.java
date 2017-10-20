package KDiffPairsInAnArray_532;

import java.util.Arrays;

/**
 * Given an array of integers and an integer k,
 * you need to find the number of unique k-diff pairs in the array.
 * Here a k-diff pair is defined as an integer pair (i, j),
 * where i and j are both numbers in the array and their absolute difference is k.
 *
 * Example 1:
 * Input: [3, 1, 4, 1, 5], k = 2
 * Output: 2
 * Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
 *
 * Although we have two 1s in the input, we should only return the number of unique pairs.
 * Example 2:
 * Input:[1, 2, 3, 4, 5], k = 1
 * Output: 4
 * Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).
 *
 * Example 3:
 * Input: [1, 3, 1, 5, 4], k = 0
 * Output: 1
 * Explanation: There is one 0-diff pair in the array, (1, 1).
 *
 * Note:
 * The pairs (i, j) and (j, i) count as the same pair.
 * The length of the array won't exceed 10,000.
 * All the integers in the given input belong to the range: [-1e7, 1e7].
 */
public class Solution {
    // trivial way: sort and find
    public int findPairs2(int[] nums, int k) {
        if(nums.length<2) return 0;
        Arrays.sort(nums);
        // O(n^2) finding.
        int count = 0;
        for(int i=0;i<nums.length;i++){
            if(i==0||nums[i]!=nums[i-1]){
                int j = i+1;
                while(j<nums.length&&nums[j]-nums[i]<=k){
                    if(nums[j]-nums[i]<k) ++j;
                    else count++;
                }
            }
        }
        return count;
    }

    // second way: deduplicate then find pairs.
    // because we find that we maybe spending to much time on duplicate elements.
    // it has bugs -- consider [1,1,1] k = 0, it will return 2.
    public int findPairs3(int[] nums, int k){
        if(nums.length<2) return 0;
        Arrays.sort(nums);
        int preal = 1, count = 0;
        for(int i=1;i<nums.length;i++){
            if(nums[i]!=nums[i-1])
                nums[preal++] = nums[i];
            else
                count++;
        }
        if(k==0) return count;
        // now preal is the length of the deduplicated array
        // it becomes a two pointer problem
        count = 0;
        int left = 0,right = 1;
        while(right<preal){
            if(nums[right]-nums[left]<k) ++right;
            else if(nums[right]-nums[left]==k){
                ++count;
                ++right;
            }else{
                ++left;
            }
        }
        return count;
    }

    // a even simpler two pointer solution
    // it's not easy to do it because of the ambiguous
    // situations of k=0 and getting rid of the duplicate pairs.
    // There is much simpler code on lc..
    public int findPairs(int[] nums, int k){
        if(nums.length<2||k<0) return 0;
        Arrays.sort(nums);
        int count = 0;
        if(k==0){
            int p = 0;
            while(p+1<nums.length){
                if(nums[p]==nums[p+1]) ++count;
                while(p+1<nums.length&&nums[p]==nums[p+1]) ++p;
                ++p;
            }
        }else{
            int left = 0, right = 1;
            while(true){
                // skip duplicate situations
                while(left!=0&&nums[left]==nums[left-1]) ++left;
                while(right<nums.length&&nums[right]==nums[right-1]) right++;
                if(right==nums.length) break;
                if(nums[right]-nums[left]<k) ++right;
                else if(nums[right]-nums[left]>k) ++left;
                else{
                    ++count;
                    ++right;
                }
            }
        }
        return count;
    }

    // there is also an easy solution using map

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findPairs(new int[]{1,1,1,2,2,3,3,4,5,5,6,6,6,6,6},0));
    }
}
