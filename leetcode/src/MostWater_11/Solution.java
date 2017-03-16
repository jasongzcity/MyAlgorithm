package MostWater_11;


public class Solution
{
    // O(n) complexity
    public static int maxArea(int[] height)
    {
        // assure height not null
        int len = height.length;
        //if(len<=1) return 0; height is at least 2
        int ph = 0,pt = len-1;// pointer to head and tail
        int maxArea = -1;
        while(ph<pt)
        {
            maxArea = Math.max(maxArea,(pt-ph)*Math.min(height[ph],height[pt]));
            // search for a higher line for the container
            if(height[ph]<height[pt])
                ph++;
            else
                pt--;
        }
        return maxArea;
    }
}
