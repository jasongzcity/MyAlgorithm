package PermutationsII_47;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a collection of numbers that might contain duplicates,
 * return all possible unique permutations.
 *
 * For example,
 * [1,1,2] have the following unique permutations:
 *
 * [
 *  [1,1,2],
 *  [1,2,1],
 *  [2,1,1]
 * ]
 */
public class Solution {
    public static List<List<Integer>> permuteUnique(int[] nums){
        Arrays.sort(nums);
        List<List<Integer>> rs = new ArrayList<>();
        getPermute2(nums,nums.length,new boolean[nums.length],new ArrayList<>(),rs);
        return rs;
    }

    private static void getPermute2(int[] a,int nums,boolean[] repeatFlag,
                                    List<Integer> list,List<List<Integer>> rs){
        if(nums==0){
            rs.add(new ArrayList<>(list));
            return;
        }

        for (int i = 0; i < a.length; i++){
            if(repeatFlag[i]) continue;
            if(i>0&&a[i]==a[i-1]&&!repeatFlag[i-1]) continue;
            list.add(a[i]);
            repeatFlag[i] = true;
            getPermute2(a,nums-1,repeatFlag,list,rs);
            repeatFlag[i] = false;
            list.remove(list.size()-1);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2, 2, 1, 1};

        List<List<Integer>> rs = permuteUnique(nums);
        for (Object o : rs.toArray()) {
            for (Object obj : ((List<Integer>) o).toArray()) {
                System.out.print((Integer) obj + " ");
            }
            System.out.println();
        }
    }
}