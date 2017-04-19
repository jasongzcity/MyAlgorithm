package Subset_78;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a set of distinct integers, nums, return all possible subsets.
 * Note: The solution set must not contain duplicate subsets.
 *
 * For example,
 * If nums = [1,2,3], a solution is:
 *
 * [
 *  [3],
 *  [1],
 *  [2],
 *  [1,2,3],
 *  [1,3],
 *  [2,3],
 *  [1,2],
 *  []
 * ]
 */
public class Solution {
    public static List<List<Integer>> subsets(int[] nums) {
        List<Integer> l = new ArrayList<>(nums.length);
        List<List<Integer>> rs = new ArrayList<>();
        rs.add(new ArrayList<>());
        getSubsets(nums,0,l,rs);
        return rs;
    }

    private static void getSubsets(int[] a,int begin,List<Integer> list,
                                   List<List<Integer>> rs){
        for(int i=begin;i<a.length;i++){
            list.add(a[i]);
            rs.add(new ArrayList<>(list));
            getSubsets(a,i+1,list,rs);
            list.remove(list.size()-1);
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,2,3};
        List l = subsets(a);
        for(Object list:l){
            for(Integer i:(List<Integer>)list){
                System.out.print(i+" ");
            }
            System.out.println();
        }
    }
}
