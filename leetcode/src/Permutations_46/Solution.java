package Permutations_46;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a collection of distinct numbers, return all possible permutations.
 *
 * For example,
 * [1,2,3] have the following permutations:
 *
 *  [
 *    [1,2,3],
 *    [1,3,2],
 *    [2,1,3],
 *    [2,3,1],
 *    [3,1,2],
 *    [3,2,1]
 *  ]
 */
public class Solution
{
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> rs = new ArrayList<>();
        List<Integer> list = new ArrayList<>(nums.length);
        boolean[] repeatFlag = new boolean[nums.length];
        getPermute(nums,nums.length,list,rs,repeatFlag);
        return rs;
    }

    private static void getPermute(int[] a,int nums,List<Integer> list,
                                   List<List<Integer>> rs,boolean[] repeatFlag){
        if(nums==0){ // get enough numbers, add to list and return.
            rs.add(new ArrayList<>(list));
            return;
        }

        for(int i=0;i<a.length;i++){
            if(repeatFlag[i]) continue; // repeated, skip this
            list.add(a[i]);
            repeatFlag[i] = true;
            getPermute(a,nums-1,list,rs,repeatFlag);
            list.remove(list.size()-1);
            repeatFlag[i] = false;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3};

        List rs = permute(nums);

        for(Object o:rs.toArray()){
            for(Object obj: ((List<Integer>)o).toArray()){
                System.out.print((Integer)obj+" ");
            }
            System.out.println();
        }
    }
}
