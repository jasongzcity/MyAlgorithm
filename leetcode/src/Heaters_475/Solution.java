package Heaters_475;

import java.util.Arrays;

/**
 * Winter is coming! Your first job during the contest is to design a
 * standard heater with fixed warm radius to warm all the houses.
 *
 * Now, you are given positions of houses and heaters on a horizontal line,
 * find out minimum radius of heaters so that all houses could be
 * covered by those heaters.
 *
 * So, your input will be the positions of houses and heaters seperately,
 * and your expected output will be the minimum radius standard of heaters.
 *
 * Note:
 * Numbers of houses and heaters you are given are non-negative and
 * will not exceed 25000.
 * Positions of houses and heaters you are given are
 * non-negative and will not exceed 10^9.
 * As long as a house is in the heaters' warm radius range, it can be warmed.
 * All the heaters follow your radius standard and the warm radius will the same.
 *
 * Example 1:
 * Input: [1,2,3],[2]
 * Output: 1
 * Explanation: The only heater was placed in the position 2,
 * and if we use the radius 1 standard, then all the houses can be warmed.
 *
 * Example 2:
 * Input: [1,2,3,4],[1,4]
 * Output: 1
 * Explanation: The two heater was placed in the position 1 and 4.
 * We need to use radius 1 standard, then all the houses can be
 */
public class Solution {
    public int findRadius2(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int maxDis = 0;
        for(int i=0;i<houses.length;i++){
            int posi = binarySearch(heaters,0,heaters.length,houses[i]);
            int disToleft = posi>-1?houses[i]-heaters[posi]:Integer.MAX_VALUE;
            int disToRight = posi<heaters.length-1?heaters[posi+1]-houses[i]:Integer.MAX_VALUE;
            int min = disToleft<disToRight?disToleft:disToRight;
            if(min>maxDis) maxDis = min;
        }
        return maxDis;
    }

    // hi exclusive
    private int binarySearch(int[] a,int lo,int hi,int target){
        while(lo<hi){
            int mid = (lo+hi)>>>1;
            if(a[mid]>target) hi = mid;
            else lo = mid+1;
        }
        return lo-1;
    }

    // Two pointer solution
    public int findRadius(int[] houses, int[] heaters){
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int phouses = 0,pheat = 0,maxDis = 0;
        if(houses[0]<=heaters[0]) maxDis = heaters[0]-houses[0];
        while(phouses<houses.length&&houses[phouses]<=heaters[0]) ++phouses;
        while(phouses<houses.length&&pheat+1<heaters.length){
            while(phouses<houses.length&&houses[phouses]<=heaters[pheat+1]){
                int min = Math.min(houses[phouses]-heaters[pheat],heaters[pheat+1]-houses[phouses]);
                if(min>maxDis) maxDis = min;
                ++phouses;
            }
            ++pheat;
        }
        if(houses[houses.length-1]-heaters[heaters.length-1]>maxDis)
            maxDis = houses[houses.length-1]-heaters[heaters.length-1];
        return maxDis;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findRadius(new int[]{2,5},new int[]{2}));
    }
}
