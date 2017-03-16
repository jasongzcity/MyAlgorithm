import java.util.Arrays;
import java.util.Random;

public class Solution {
    public static int[] twoSum(int[] numbers, int target)
    {
        /* use binary search */
        /*
         *  each iteration will cost O(lgn) in worst case.
         *  So the worst case will be O(nlgn)
         */
        int[] rs = new int[2];
        for(int i=0;i<numbers.length;i++)
        {
            int diff = target - numbers[i];
            int result = -1;
            result = binarySearch(numbers,diff,i+1,numbers.length);
            if(result!=-1)
            {
                rs[0] = i+1;//non-zero based
                rs[1] = result+1;
                return rs;
            }
        }
        return null;
    }

    /**
     * binary Search target in the integer array.
     * Cost O(lgn) each search.
     * @param numbers source integer array
     * @param high the high indice of range of the source array (not inclusive)
     * @param low the low indice of range of the source array (inclusive)
     * @param target target number
     * @return the target's indice.
     */
    public static int binarySearch(int[] numbers,int target,int low,int high)
    {
        if(low==high)// can't find the target
            return -1;
        int mid = (low+high)>>>1;
        if(numbers[mid]==target)
            return mid;
        if(numbers[mid]>target)
            return binarySearch(numbers,target,low,mid);
        else
            return binarySearch(numbers,target,mid+1,high);
    }

    /**
     * O(n) solution.
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum2(int[] numbers,int target)
    {
        int lo=0,hi = numbers.length-1;
        int[] rs = new int[2];
        while(numbers[lo]+numbers[hi]!=target)
        {
            if(numbers[lo]+numbers[hi]>target)
                hi--;
            else
                lo++;
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

        System.out.println("Twosum cost O(n):");
        rs = twoSum2(source,source[7]+source[9]);
        System.out.println(Arrays.toString(rs));// suppose to be 8 & 10
        rs = twoSum2(source,source[0]+source[9]);
        System.out.println(Arrays.toString(rs));// suppose to be 1 & 10

    }


}