package SortColor_75;

import java.util.Arrays;

/**
 * Given an array with n objects colored red, white or blue,
 * sort them so that objects of the same color are adjacent,
 * with the colors in the order red, white and blue.
 *
 * Here, we will use the integers 0, 1, and 2 to represent the color
 * red, white, and blue respectively.
 *
 * Note:
 * You are not suppose to use the library's sort function for this problem.
 *
 * Follow up:
 * A rather straight forward solution is a two-pass algorithm using counting sort.
 * First, iterate the array counting number of 0's, 1's, and 2's,
 * then overwrite array with total number of 0's, then 1's and followed by 2's.
 *
 * Could you come up with an one-pass algorithm using only constant space?
 */
public class Solution {
    // two-pass solution
    public static void sortColors(int[] nums) {
        int redcount = 0,whitecount = 0,bluecount = 0;
        for(int i:nums){
            if(i==0) ++redcount;
            else if(i==1) ++whitecount;
            else ++bluecount;
        }
        int i=0;
        for(int j=0;j<redcount;j++) nums[i++] = 0;
        for(int j=0;j<whitecount;j++) nums[i++] = 1;
        for(int j=0;j<bluecount;j++) nums[i++] = 2;
    }

    public static void sortColorsOnePass(int[] nums){
        int ones = -1,twos = -1,zeroes = -1;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]==2){
                if(twos!=-1) twos++;
                else twos = i;
            }else if(nums[i]==1){
                if(ones==-1){
                    swap(nums,zeroes+1,i);
                    ones = zeroes+1;
                }else{
                    swap(nums,ones+1,i);
                    ones++;
                }
                if(twos!=-1) twos++;
            }else{
                ++zeroes;
                if(i!=zeroes){
                    swap(nums,i,zeroes);
                    --i; // check this position again.
                }
            }
        }
    }

    private static void swap(int[] a,int i,int j){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static boolean smartswap(int[] a,int i,int j){
        if(a[i]==a[j]) return true;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
        return false;
    }

    // second session..
    public static void sortColors2(int[] nums){
        if(nums.length==0) return;
        int zeroes = 0, two = nums.length-1;
        for(int i=0;i<=two;){
            if(nums[i]==0){
                swap(nums, i++, zeroes++);
            }else if(nums[i]==2){
                swap(nums, i, two--);
            }else i++;
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{0,1,0,0,0,1,0,2,0,2,2,2,2,1,2,0,2,1,0};
        sortColorsOnePass(a);
        System.out.println(Arrays.toString(a));
        int[] a2 = new int[]{2,1,0,0,0,0,0,0,2,2,2,2,2,2};
        sortColorsOnePass(a2);
        System.out.println(Arrays.toString(a2));
        int[] a3 = new int[]{2,2,2,2,2,2,1,1,1,1,1,1,1,1};
        sortColorsOnePass(a3);
        System.out.println(Arrays.toString(a3));

        int[] a4 = new int[]{1,1,1,1,2,2,2,2,0,0,0,0};
        sortColorsOnePass(a4);
        System.out.println(Arrays.toString(a4));
    }
}
