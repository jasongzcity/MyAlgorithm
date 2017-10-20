package CombinationSumII_40;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a collection of candidate numbers (C) and a target number (T),
 * find all unique combinations in C where the candidate numbers sums to T.
 *
 * Each number in C may only be used once in the combination.
 *
 * Note:
 *
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * For example, given candidate set [10, 1, 2, 7, 6, 1, 5] and target 8,
 * A solution set is:
 *
 * [
 *   [1, 7],
 *   [1, 2, 5],
 *   [2, 6],
 *   [1, 1, 6]
 * ]
 */
public class Solution
{
    // second session
    public static List<List<Integer>> combinationSum2II(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> rs = new ArrayList<>();
        dfs(candidates,0,0,target,new ArrayList<>(),rs);
        return rs;
    }

    private static void dfs(int[] a, int begin, int cursum, int tar, List<Integer> l,
                                List<List<Integer>> rs){
        if(cursum==tar){
            rs.add(new ArrayList<>(l));
            return;
        }
        for(int i=begin;i<a.length&&cursum+a[i]<=tar;i++){
            if(i==begin||(a[i]!=a[i-1])){
                l.add(a[i]);
                dfs(a,i+1,cursum+a[i],tar,l,rs);
                l.remove(l.size()-1);
            }
        }
    }


    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> rs = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        getComb(candidates,0,target,list,rs);
        return rs;
    }

    private static void getComb(int[] a,int begin,int target,List<Integer> list,
                                List<List<Integer>> rs){
        if(target==0){
            rs.add(new ArrayList<>(list));
            return;
        }

        for(int i=begin;i<a.length&&a[i]<=target;i++){
            if(i>begin&&a[i]==a[i-1]) continue; // skip when not first met
            list.add(a[i]);
            getComb(a,i+1,target-a[i],list,rs);
            list.remove(list.size()-1);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{10, 1, 2, 7, 6, 1, 5};
        List<List<Integer>> rs = combinationSum2(nums,8);

        for(Object o:rs.toArray()){
            for(Object obj: ((List<Integer>)o).toArray()){
                System.out.print((Integer)obj+" ");
            }
            System.out.println();
        }
    }
}
