package PalindromePartitioning_131;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a string s, partition s such that every substring of the partition
 * is a palindrome.
 *
 * Return all possible palindrome partitioning of s.
 *
 * For example, given s = "aab",
 * Return
 * [
 *  ["aa","b"],
 *  ["a","a","b"]
 * ]
 */
public class Solution {
    // DFS with memo
    public List<List<String>> partition(String s) {
        int len = s.length();
        if(len==0) return new ArrayList<>();
        int[][] memo = new int[len][len];
        for(int i=0;i<len;i++) memo[i][i] = 1;
        List<String> list = new ArrayList<>(s.length());
        List<List<String>> rs = new ArrayList<>();
        helper(s.toCharArray(),0,memo,list,rs);
        return rs;
    }

    private void helper(char[] arr,int begin,int[][] memo,
                               List<String> list,List<List<String>> rs){
        if(begin==arr.length){
            rs.add(new ArrayList<>(list));
            return;
        }

        // flags in the memo:
        // memo[i,j](i<=j) represents the s.subString(i,j+1)
        // 0 undecided, 1 palindrome, 2 not palindrome
        for(int i=begin;i<arr.length;i++){
            if(memo[begin][i]==2) continue; // not palindrome
            if(memo[begin][i]==0){
                if(isPalindrome(arr,begin,i)){
                    memo[begin][i] = 1;
                }else{
                    memo[begin][i] = 2;
                    continue;
                }
            }
            list.add(new String(arr,begin,i-begin+1));
            helper(arr,i+1,memo,list,rs);
            list.remove(list.size()-1);
        }
    }

    // notice begin & end inclusive
    private boolean isPalindrome(char[] a,int begin,int end){
        while(begin<end)
            if(a[begin++]!=a[end--])
                return false;
        return true;
    }
//
//    public static void main(String[] args) {
//        String s = "aabaa";
//        List<List<String>> rs = partition(s);
//        System.out.println(rs.toString());
//    }
}
