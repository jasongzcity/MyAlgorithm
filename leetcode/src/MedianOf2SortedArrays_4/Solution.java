package MedianOf2SortedArrays_4;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 *
 * Find the median of the two sorted arrays.
 * The overall run time complexity should be O(log (m+n)).
 *
 * Example 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 * The median is 2.0
 *
 * Example 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * The median is (2 + 3)/2 = 2.5
 */
public class Solution {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2)
    {
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
        int lo = 0,hi = shortLen,sum = shortLen+longLen,half = (sum+1)>>1;//keeps at the "right-half"
        int minRight = 0;
        int maxLeft = 0;
        while(lo<=hi)
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

    public static void main(String[] args){
        int[] nums = new int[]{1,2,3};
        int[] nums2 = new int[]{4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        Solution s = new Solution();
        System.out.println(s.findMedianSortedArrays2(nums,nums2));
    }

    // rewrite
    public double findMedianSortedArrays2(int[] nums1, int[] nums2){
        int[] shorter,longer;
        if(nums1.length>nums2.length){
            shorter = nums2;
            longer = nums1;
        }else{
            shorter = nums1;
            longer = nums2;
        }
        int shorterLen = shorter.length,longerLen = longer.length;
        int lo = 0,hi = shorterLen,sum = shorterLen+longerLen,half = (sum+1)>>1;
        while(true){
            int sp = (lo+hi)>>1,lp = half-sp;
            // now that we keeps the left half and the right half "balanced"
            // len(0...sp-1)+len(0...lp-1) = len(sp...)+len(lp...)
            if(sp<shorterLen&&shorter[sp]<longer[lp-1]) lo = sp+1;
            else if(sp>0&&shorter[sp-1]>longer[lp]) hi = sp-1;
            else{
                int maxLeft,minRight;
                // position now is suitable
                if(sp==0) maxLeft = longer[lp-1];
                else if(lp==0) maxLeft = shorter[sp-1];
                else maxLeft = Math.max(longer[lp-1],shorter[sp-1]);

                if((sum&1)==1) return maxLeft;

                if(lp==longerLen) minRight = shorter[sp];
                else if(sp==shorterLen) minRight = longer[lp];
                else minRight = Math.min(shorter[sp],longer[lp]);

                return (double) (minRight+maxLeft)/2;
            }
        }
    }
}
