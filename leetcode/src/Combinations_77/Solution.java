package Combinations_77;

import java.util.ArrayList;
import java.util.List;

/**
 *  Given two integers n and k, return all possible combinations
 *  of k numbers out of 1 ... n.
 *
 * For example,
 * If n = 4 and k = 2, a solution is:
 *
 *  [
 *    [2,4],
 *    [3,4],
 *    [2,3],
 *    [1,2],
 *    [1,3],
 *    [1,4],
 *  ]
 */
public class Solution
{
    // This is a slow method.
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> rs = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        getCombs(1,n,k,list,rs);
        return rs;
    }

    private static void getCombs(int begin,int limit,int nums,List<Integer> list,
                                 List<List<Integer>> rs){
        if(nums==0){ // we get enough numbers, add to list and return.
            rs.add(new ArrayList<>(list));
            return;
        }
        for(int i=begin;i<=limit-nums+1;i++){
            list.add(i);
            getCombs(i+1,limit,nums-1,list,rs);
            list.remove(list.size()-1);
        }
    }

    public static void main(String[] args) {
        List rs = combine(4,2);

        for(Object o:rs.toArray()){
            for(Object obj: ((List<Integer>)o).toArray()){
                System.out.print((Integer)obj+" ");
            }
            System.out.println();
        }
    }
}
