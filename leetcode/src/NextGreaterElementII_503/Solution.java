package NextGreaterElementII_503;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Given a circular array (the next element of the last element is the
 * first element of the array), print the Next Greater Number for every element.
 * The Next Greater Number of a number x is the first greater number
 * to its traversing-order next in the array,
 * which means you could search circularly to find its next greater number.
 * If it doesn't exist, output -1 for this number.
 *
 * Example 1:
 * Input: [1,2,1]
 * Output: [2,-1,2]
 * Explanation: The first 1's next greater number is 2;
 * The number 2 can't find next greater number;
 * The second 1's next greater number needs to search circularly, which is also 2.
 *
 * Note: The length of given array won't exceed 10000.
 */
public class Solution {
    public int[] nextGreaterElements(int[] a) {
        boolean second = true;
        int[] rs = new int[a.length];
        LinkedList<Integer> s1 = new LinkedList<>(),s2 = new LinkedList<>();
        do{
            second^=true; // toggle
            int i = 0;
            while(i<a.length&&(!second||i-1!=s2.getLast())){
                while(s1.size()!=0&&a[i]>s1.peek()){
                    s1.pop();
                    rs[s2.pop()] = a[i];
                }
                s1.push(a[i]);
                s2.push(i++);
            }
        }while(!second);
        while(s2.size()!=0) rs[s2.pop()] = -1;
        return rs;
    }

    // super fast leetcode solution
    public int[] nextGreaterElements2(int[] nums) {
        if(nums.length == 0) return nums;
        int max = Integer.MIN_VALUE,index = -1;
        for(int i = 0;i<nums.length;i++){
            if(max<nums[i]){
                max = nums[i];
                index = i;
            }
        }
        int[] res = new int[nums.length];
        res[index] = -1;
        for(int i=index-1;i != index;i--){
            if(i == -1) i = nums.length -1;
            if(i == index) break;
            int j = i+1;
            if(j >= nums.length) j = 0;
            while(nums[i]>=nums[j] && nums[j]!=max){
                j = res[j];
                if(j >= nums.length) j = 0;
            }
            if(nums[i] == max) res[i] = -1;
            else res[i] = j;
        }
        int[] Greater = new int[res.length];
        for(int i=0;i<res.length;i++){
            if(res[i] == -1) Greater[i] = -1;
            else Greater[i] = nums[res[i]];
        }
        return Greater;
    }

    // another smart solution
    public int[] nextGreaterElements3(int[] nums) {
        int n = nums.length, next[] = new int[n];
        Arrays.fill(next, -1);
        Stack<Integer> stack = new Stack<>(); // index stack
        for (int i = 0; i < n * 2; i++) {
            int num = nums[i % n];
            while (!stack.isEmpty() && nums[stack.peek()] < num)
                next[stack.pop()] = num;
            if (i < n) stack.push(i);
        }
        return next;
    }
}
