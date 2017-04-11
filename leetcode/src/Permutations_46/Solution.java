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

    // most voted solution on leetcode, same thought but
    // more concise & a little bit faster.
    private static void getPermute2(int[] a,int begin,List<List<Integer>> rs){
        if(begin==a.length){
            List<Integer> l = new ArrayList<>(a.length);
            for(int i:a) l.add(i);
            rs.add(new ArrayList<>(l));
            return;
        }

        for (int i = begin; i < a.length; i++) {
            swap(a,begin,i);
            getPermute2(a,begin+1,rs);
            swap(a,begin,i);
        }
    }

    private static void swap(int[] a,int i,int j){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static List<List<Integer>> optimalCaller(int[] nums) {
        List<List<Integer>> rs = new ArrayList<>();
        getPermute2(nums,0,rs);
        return rs;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3};

        List rs = permute(nums);

//        for(Object o:rs.toArray()){
//            for(Object obj: ((List<Integer>)o).toArray()){
//                System.out.print((Integer)obj+" ");
//            }
//            System.out.println();
//        }

        rs = optimalCaller(nums);
        for(Object o:rs.toArray()){
            for(Object obj: ((List<Integer>)o).toArray()){
                System.out.print((Integer)obj+" ");
            }
            System.out.println();
        }
    }
}
