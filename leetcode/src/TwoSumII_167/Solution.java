package TwoSumII_167;

import java.util.Arrays;
import java.util.Random;

/**
 * Given an array of integers that is already sorted in ascending order,
 * find two numbers such that they add up to a specific target number
 */
public class Solution
{
    /**
     * O(n) solution.
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum(int[] numbers,int target)
    {
        int lo=0,hi = numbers.length-1;
        int[] rs = new int[2];
        while(numbers[lo]+numbers[hi]!=target)
        {
            if(numbers[lo]+numbers[hi]>target) hi--;
            else lo++;
        }
        rs[0] = lo+1;
        rs[1] = hi+1;
        return rs;
    }

    public static void main(String[] args)
    {
        /* Simple test case. */
        Random rand = new Random(47);
        int[] source = new int[10];
        for(int i=0;i<10;i++)
        {
            source[i] = rand.nextInt(100000);//must set bound or it might overflow
        }
        Arrays.sort(source);
        System.out.println("Twosum using binarySearch:");
        int[] rs = twoSum(source,source[7]+source[9]);
        System.out.println(Arrays.toString(rs));// suppose to be 8 & 10
        rs = twoSum(source,source[0]+source[9]);
        System.out.println(Arrays.toString(rs));// suppose to be 1 & 10
    }
}
