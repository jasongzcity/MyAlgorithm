package CombinationSum_39;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a set of candidate numbers (C) (without duplicates)
 * and a target number (T), find all unique combinations in C
 * where the candidate numbers sums to T.
 *
 * The same repeated number may be chosen from C unlimited number of times.
 *
 * Note:
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 *
 * For example, given candidate set [2, 3, 6, 7] and target 7,
 * A solution set is:
 * [
 *   [7],
 *   [2, 2, 3]
 * ]
 */
public class Solution
{
    // second session
    public static List<List<Integer>> combinationSumII(int[] candidates, int target){
        List<List<Integer>> rs = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, 0, 0, target, new ArrayList<>(), rs);
        return rs;
    }

    private static void dfs(int[] a, int begin, int cur, int tar, List<Integer> l,
                                List<List<Integer>> rs){
        if(cur==tar){
            rs.add(new ArrayList<>(l));
            return;
        }
        for(int i=begin; i<a.length&&cur+a[i]<=tar; i++){
            l.add(a[i]);
            dfs(a,i,cur+a[i],tar,l,rs);
            l.remove(l.size()-1);
        }
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> rs = new ArrayList<>();
        if(candidates.length==0) return rs;
        Arrays.sort(candidates);
        int hiBound = candidates.length-1;
        while(hiBound>-1&&candidates[hiBound]>target) hiBound--;
        if(hiBound<0) return rs;
        List<Integer> list = new ArrayList<>();
        getTargetInRange(candidates,0,hiBound,target,list,rs);
        return rs;
    }

    private static void getTargetInRange(int[] a,int lo,int hi,int target,
                                         List<Integer> list,List<List<Integer>> rs){
        if(lo==hi){
            int times = 1;
            while(a[lo]*times<target) times++;
            if(a[lo]*times==target){
                for (int i = 0; i < times; i++) list.add(a[lo]);
                rs.add(list);
            }
        }else{
            while(hi>-1&&a[hi]>target) hi--;
            if(hi==-1) return;
            int times = 0;
            while(a[hi]*times<=target){
                if(a[hi]*times==target) {
                    List<Integer> l = new ArrayList<>(list);
                    for (int i = 0; i < times; i++) l.add(a[hi]);
                    rs.add(l);
                    return;
                }else{
                    List<Integer> l = new ArrayList<>(list);
                    for (int i = 0; i < times; i++) l.add(a[hi]);
                    getTargetInRange(a,lo,hi-1,target-a[hi]*times,l,rs);
                }
                ++times;
            }
        }
    }

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<Integer> list = new ArrayList<>();
        List<List<Integer>> rs = new ArrayList<>();
        getTargetInRange(candidates,0,target,list,rs);
        return rs;
    }

    private static void getTargetInRange(int[] a,int lo,int target,
                                         List<Integer> list,List<List<Integer>> rs) {
        if (target == 0) {
            rs.add(new ArrayList<Integer>(list)); // copy the whole list until it's necessary
            return;
        }
        // contain backtrack implicitly.
        for (int i = lo; i < a.length && a[i] <= target; i++) {
            list.add(a[i]);
            getTargetInRange(a, i, target - a[i], list, rs);
            list.remove(list.size()-1);
        }

        // Notice: the method below is slower because
        // it may cause unnecessary copying at line 92
//        for (int i = lo; i < a.length && a[i] <= target; i++) {
//            List<Integer> l = new ArrayList<>(list);
//            l.add(a[i]);
//            getTargetInRange(a, i, target - a[i], l, rs);
//        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,3,7};
//        List<List<Integer>> rs = combinationSum(nums,18);
//        for(Object o:rs.toArray()){
//            for(Object obj: ((List<Integer>)o).toArray()){
//                System.out.print((Integer)obj+" ");
//            }
//            System.out.println();
//        }

//        System.out.println();
        List<List<Integer>> rs = combinationSum2(nums,18);

        for(Object o:rs.toArray()){
            for(Object obj: ((List<Integer>)o).toArray()){
                System.out.print((Integer)obj+" ");
            }
            System.out.println();
        }
    }
}
