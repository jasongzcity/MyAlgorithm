package MedianOf2SortedArrays_4;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/7.
 */
public class Solution {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2)
    {
        int sum = nums1.length+nums2.length;
        int[] shorter,longer;
        if(nums1.length>nums2.length) {
            longer = nums1;
            shorter = nums2;
        } else {
            longer = nums2;
            shorter = nums1;
        }
        int shortLen = shorter.length,longLen = longer.length;// pointer.
        // binary search in shorter array
        int lo = 0,hi = shortLen,half = (shortLen+longLen+1)>>1;//keeps at the "right-half"
        int minRight = 0;
        int maxLeft = 0;
        while(lo<=hi) // binary search no include hi
        {
            int sp = (lo+hi)>>1;//shorter pointer.
            int lp = half - sp;//longer pointer
            if(sp<shortLen&&longer[lp-1]>shorter[sp])// the mid point is larger than sp
                lo = sp+1;
            else if(sp>0&&shorter[sp-1]>longer[lp])//mid point is less than sp
                hi = sp-1;
            else //check boundary conditions
            {
                if(sp==0)
                    maxLeft = longer[lp-1];
                else if(lp==0)
                    maxLeft = shorter[sp-1];
                else
                    maxLeft = Math.max(shorter[sp-1],longer[lp-1]);
                if(sum%2==1)
                    return maxLeft;
                if(sp==shortLen) //
                    minRight = longer[lp];
                else if(lp==longLen)
                    minRight = shorter[sp];
                else
                    minRight = Math.min(longer[lp],shorter[sp]);
                return (double)(maxLeft+minRight)/2.0;
            }
        }
        return (double)(maxLeft+minRight)/2.0;
    }

    public static void main(String[] args)
    {
        Random rand = new Random(47);
        int range1 = rand.nextInt(20);
        int range2 = rand.nextInt(25);
        int[] nums1 = new int[range1];
        int[] nums2 = new int[range2];
        for(int i=0;i<range1;i++)
            nums1[i] = rand.nextInt(200);
        for(int i=0;i<range2;i++)
            nums2[i] = rand.nextInt(200);
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        System.out.println("array1: "+Arrays.toString(nums1)+", length:"+ nums1.length);
        System.out.println("array2: "+Arrays.toString(nums2)+", length:"+ nums2.length);
        System.out.println("result: "+findMedianSortedArrays(nums1,nums2));
    }


}
