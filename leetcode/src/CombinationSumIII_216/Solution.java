package CombinationSumIII_216;

import java.util.ArrayList;
import java.util.List;

/**
 * Find all possible combinations of k numbers that add up to a number n,
 * given that only numbers from 1 to 9 can be used and
 * each combination should be a unique set of numbers.
 *
 * Example 1:
 * Input: k = 3, n = 7
 *
 * Output:
 * [[1,2,4]]
 *
 * Example 2:
 * Input: k = 3, n = 9
 *
 * Output:
 * [[1,2,6], [1,3,5], [2,3,4]]
 */
public class Solution
{
    // Second Session
    public static List<List<Integer>> combinationSum3II(int k, int n){
        List<List<Integer>> rs = new ArrayList<>();
        dfs(1,0,n,new ArrayList<>(k),rs,k);
        return rs;
    }

    private static void dfs(int begin, int cur, int tar, List<Integer> l,
                            List<List<Integer>> rs, int k){
        if(l.size()==k){
            if(cur==tar){
                rs.add(new ArrayList<>(l));
            }
            return;
        }
        for(int i=begin;i<=9&&cur+i<=tar;i++){
            l.add(i);
            dfs(i+1,cur+i,tar,l,rs,k);
            l.remove(l.size()-1);
        }
    }

    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<Integer> list = new ArrayList<>();
        List<List<Integer>> rs = new ArrayList<>();
        getComb(1,n,k,list,rs);
        return rs;
    }

    private static void getComb(int begin,int target,int nums,List<Integer> list,
                                List<List<Integer>> rs){
        for(int i=begin;i<=9&&i<=target;i++){
            if(nums==1){
                if(i<target) continue;// search for the last number
                list.add(i);
                rs.add(new ArrayList<>(list));
                list.remove(list.size()-1);
                return;
            }else{
                list.add(i);
                getComb(i+1,target-i,nums-1,list,rs);
                list.remove(list.size()-1);
            }
        }
    }

    public static void main(String[] args) {
        List rs = combinationSum3(3,9);

        for(Object o:rs.toArray()){
            for(Object obj: ((List<Integer>)o).toArray()){
                System.out.print((Integer)obj+" ");
            }
            System.out.println();
        }
    }
}
