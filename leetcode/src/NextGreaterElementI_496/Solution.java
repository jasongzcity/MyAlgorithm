package NextGreaterElementI_496;

import java.util.*;

/**
 * You are given two arrays (without duplicates) nums1 and nums2
 * where nums1’s elements are subset of nums2.
 * Find all the next greater numbers for nums1's elements
 * in the corresponding places of nums2.
 *
 * The Next Greater Number of a number x in nums1 is the
 * first greater number to its right in nums2.
 * If it does not exist, output -1 for this number.
 *
 * Example 1:
 * Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
 * Output: [-1,3,-1]
 * Explanation:
 * For number 4 in the first array, you cannot find the next greater number
 * for it in the second array, so output -1.
 * For number 1 in the first array, the next greater number for it
 * in the second array is 3.
 * For number 2 in the first array, there is no next greater number for it
 * in the second array, so output -1.
 *
 * Example 2:
 * Input: nums1 = [2,4], nums2 = [1,2,3,4].
 * Output: [3,-1]
 * Explanation:
 * For number 2 in the first array, the next greater number for it
 * in the second array is 3.
 * For number 4 in the first array, there is no next greater number for it
 * in the second array, so output -1.
 *
 * Note:
 * All elements in nums1 and nums2 are unique.
 * The length of both nums1 and nums2 would not exceed 1000.
 */
public class Solution {
    // hidden binary search actually
    // get the wrong idea of the question.....
    public int[] nextGreaterElement2(int[] findNums, int[] nums) {
        Map<Integer,Integer> map = new HashMap<>(findNums.length<<1);
        for(int i=0;i<findNums.length;i++) map.put(findNums[i],i);
        List<Integer> right = new ArrayList<>(nums.length);
        int count = 0;
        for(int i=nums.length-1;i>-1;i--){
            int posi = binarySearch(right,nums[i]);
            Integer findPosi = map.get(nums[i]);
            if(findPosi!=null){
                // exist in nums1
                if(++posi==right.size()) findNums[findPosi] = -1;
                else findNums[findPosi] = right.get(posi);
                if(++count==findNums.length) return findNums;
            }
        }
        return findNums;
    }

    // insert the target element in the array and return its position
    private int binarySearch(List<Integer> right,int target){
        int lo = 0,hi = right.size(),mid;
        while(lo<hi){
            mid = lo+((hi-lo)>>1);
            if(right.get(mid)<target) lo = mid+1;
            else hi = mid;
        }
        right.add(lo,target);
        return lo;
    }

    public int[] nextGreaterElement(int[] findNums, int[] nums){
        if(findNums.length==0) return findNums;
        Map<Integer,Integer> map = new HashMap<>(findNums.length<<1);
        for(int i=0;i<findNums.length;i++) map.put(findNums[i],i);
        LinkedList<Integer> stack = new LinkedList<>();
        for(int c:nums){
            while(stack.size()!=0&&c>stack.peek()){
                stack.pop();
                findNums[stack.pop()] = c;
            }
            Integer posi = map.get(c);
            if(posi!=null){
                stack.push(posi);
                stack.push(c);
            }
        }
        while(stack.size()!=0){
            stack.pop();
            findNums[stack.pop()] = -1;
        }
        return findNums;
    }
}
