package TrappingRainWater_42;

import java.util.Stack;

/**
 * Given n non-negative integers representing an elevation map
 * where the width of each bar is 1, compute how much water
 * it is able to trap after raining.
 *
 * For example,
 * Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.
 */
public class Solution {

    public static int trap(int[] height) {
        int waterSize = 0;
        int maxHeight = 0;
        for (int i = 0; i < height.length; i++)
            if(height[i]>maxHeight)
                maxHeight = height[i];

        int[] heightMap = new int[maxHeight+1]; // height-index map, could replace with a hashmap
        Stack<Integer> stack = new Stack<>();   // stack for current pool
        for (int i = 0; i < height.length; i++) {
            if(height[i]==0) continue;
            if(!stack.empty()&&height[i]<=stack.peek()){
                waterSize += (i-heightMap[stack.peek()]-1)*height[i];
                stack.push(height[i]);
                heightMap[height[i]] = i;
            }else{ // backtrack or empty
                int budHeight = 0,sideHeight = 0;
                while(!stack.empty()&&height[i]>=stack.peek()){
                    budHeight = sideHeight;    // notice this could be the same
                    sideHeight = stack.pop();
                    waterSize += (i-heightMap[sideHeight]-1)*(sideHeight-budHeight);
                }
                if(!stack.empty())
                    waterSize += (i-heightMap[stack.peek()]-1)*(height[i]-sideHeight);
                stack.push(height[i]);
                heightMap[height[i]] = i;
            }
        }
        return waterSize;
    }

    public static void main(String[] args) {
        int[] height = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap(height));
        System.out.println(trap2(height));
    }

    // leetcode optimal solution
    // so elegant...
    public static int trap2(int[] height){
        int maxright = 0,maxleft = 0,rs = 0;
        int right = height.length-1,left = 0;
        while(left<=right){
            if(height[left]<=height[right]){
                if(height[left]>maxleft) maxleft = height[left];
                else rs += (maxleft-height[left]);
                left++;
            }else{
                if(height[right]>maxright) maxright = height[right];
                else rs += (maxright-height[right]);
                right--;
            }
        }
        return rs;
    }
}
