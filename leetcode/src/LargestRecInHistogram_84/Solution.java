package LargestRecInHistogram_84;

import java.util.Stack;

/**
 * Given n non-negative integers representing
 * the histogram's bar height where the width of each bar is 1,
 * find the area of largest rectangle in the histogram.
 *
 * For example,
 * Given heights = [2,1,5,6,2,3],
 * return 10.
 *
 * See pictures of example at:
 * https://leetcode.com/problems/largest-rectangle-in-histogram/#/description
 */
public class Solution{
    public static int largestRectangleArea(int[] height) {
        Stack<Integer> index= new Stack<>();
        index.push(-1);
        int max=0;

        for(int i=0;i<height.length;i++){
            while(index.peek()>-1) {
                if (height[index.peek()] > height[i]) {
                    int top = index.pop();
                    max = Math.max(max, height[top] * (i - 1 - index.peek()));
                } else {
                    break;
                }
            }
            index.push(i);
        }
        while(index.peek()!=-1){
            int top=index.pop();
            max=Math.max(max,height[top]*(height.length-1-index.peek()));
        }
        return max;
    }

    public static void main(String[] args) {
        int[] a = new int[]{2,1,5,6,2,3};
        System.out.println(largestRectangleArea(a));
    }

    // another solution but same idea.
//    public int largestRectangleArea(int[] height) {
//        int len = height.length;
//        Stack<Integer> s = new Stack<Integer>();
//        int maxArea = 0;
//        for(int i = 0; i <= len; i++){
//            int h = (i == len ? 0 : height[i]);
//            if(s.isEmpty() || h >= height[s.peek()]){
//                s.push(i);
//            }else{
//                int tp = s.pop();
//                maxArea = Math.max(maxArea, height[tp] * (s.isEmpty() ? i : i - 1 - s.peek()));
//                i--;
//            }
//        }
//        return maxArea;
//    }
}