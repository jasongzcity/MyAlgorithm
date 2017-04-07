package SubsetII_90;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a collection of integers that might contain duplicates, nums,
 * return all possible subsets.
 *
 * Note: The solution set must not contain duplicate subsets.
 *
 * For example,
 * If nums = [1,2,2], a solution is:
 *
 * [
 *   [2],
 *   [1],
 *   [1,2,2],
 *   [2,2],
 *   [1,2],
 *   []
 * ]
 */
public class Solution {
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> rs = new ArrayList<>();
        rs.add(new ArrayList<>());  // empty set
        List<Integer> list = new ArrayList<>();
        getSubset(nums,0,list,rs);
        return rs;
    }

    private static void getSubset(int[] a,int begin,List<Integer> list,
                                  List<List<Integer>> rs){
        for (int i = begin; i < a.length; i++) {
            if(i>begin&&a[i]==a[i-1]) continue; // skip when not first met
            list.add(a[i]);
            rs.add(new ArrayList<>(list));
            getSubset(a,i+1,list,rs);
            list.remove(list.size()-1); // backtrack
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,2};
        List<List<Integer>> rs = subsetsWithDup(nums);

        for(Object o:rs.toArray()){
            System.out.print("[ ");
            for(Object obj: ((List<Integer>)o).toArray()){
                System.out.print((Integer)obj+" ");
            }
            System.out.println(" ]");
        }
    }
}
