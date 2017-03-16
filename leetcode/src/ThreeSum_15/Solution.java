package ThreeSum_15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 Given an array S of n integers, are there elements a, b, c
 in S such that a + b + c = 0? Find all unique triplets in the array which
 gives the sum of zero.
 Note: The solution set must not contain duplicate triplets.
*/
public class Solution
{
    public static List<List<Integer>> threeSum(int[] nums)
    {
        /* it's a brute force method...*/
        int[] copy = Arrays.copyOf(nums,nums.length);
        List<List<Integer>> rs = new ArrayList<>();
        Arrays.sort(copy);
        int pt = copy.length-1; // tail pointer
        while(pt>0)
        {
            if(pt!=copy.length-1&&copy[pt]==copy[pt+1])
            {
                pt--;
                continue;
            }
            for(int i=0,j=copy.length-1;i<j;) // search in the sorted array.
            {
                if(i==pt||copy[i]+copy[j]+copy[pt]<0)
                {
                    i++;
                    continue;
                }
                else if(j==pt||copy[i]+copy[j]+copy[pt]>0)
                {
                    j--;
                    continue;
                }
                else //get the answer!
                {
                    while(i<j&&(copy[i]==copy[i+1])&&(copy[j]==copy[j-1]))//escape duplicate triplets
                    {
                        i++;
                        j--;
                    }
                    List<Integer> list = new ArrayList<>();
                    list.add(copy[pt]);
                    list.add(copy[i]);
                    list.add(copy[j]);
                    rs.add(list);
                }
                i++;
                j--;
            }
            pt--;
        }
        return rs;
    }

    public static void main(String[] args)
    {
        int[] a = new int[]{-1,0,1,2,-1,-4};
        threeSum(a);
    }
}
