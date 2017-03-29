package NextPermutation_31;

/**
 * Implement next permutation, which rearranges numbers
 * into the lexicographically next greater permutation of numbers.
 */
public class Solution
{
    public static void nextPermutation(int[] num)
    {
        int len = num.length;
        if(len<2) return;
        int p = len - 1;
        while(p>0&&num[p]<=num[p-1]) p--;
        if(p==0) // the array is sorted in descending order, rearrange it in ascending order
            reverse(num,0,len-1);
        else
        {
            p--; // now p should be swap.
            int i = len-1;
            while(i>p&&num[i]<=num[p]) i--;
            // now element i is the smallest element thats larger than element p
            swap(num,p,i);
            reverse(num,p+1,len-1);
        }
    }

    // reverse integer array, lo and hi inclusive
    static void reverse(int[] num, int lo, int hi)
    {
        while(lo<hi)
        {
            swap(num,lo,hi);
            lo++;
            hi--;
        }
    }

    static void swap(int[] num,int i,int j)
    {
        if(i==j) return;
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }

    public static void main(String[] args)
    {
        int[] num = new int[]{1,2,3,4,9,8,7,6,5,3,3,2,2,1};
        nextPermutation(num);
        for(int i:num)
            System.out.print(i+" ");
        System.out.println("\n");

        int[] nums = new int[]{1,2,3,1,2,6,3,5,7,8,8,9,9};
        prevPermutation(nums);
        for(int i:nums)
            System.out.print(i+" ");
        System.out.println();
    }

    /* extra mission: implement prev permutation */
    public static void prevPermutation(int[] num)
    {
        if(num.length<2) return;
        int p = num.length-1;
        while(p>0&&num[p]>=num[p-1]) p--;
        if(p==0) reverse(num,0,num.length-1); // ascending order, reverse it
        else
        {
            p--;
            int i = num.length-1;
            while(i>p&&num[i]>=num[p]) i--;
            swap(num,p,i);
            reverse(num,p+1,num.length-1);
        }
    }
}
