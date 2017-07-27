package PalindromePermutationII_267;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Given a string s, return all the palindromic permutations (without duplicates) of it.
 * Return an empty list if no palindromic permutation could be form.
 *
 * For example:
 *
 * Given s = "aabb", return ["abba", "baab"].
 *
 * Given s = "abc", return [].
 */
public class Solution {
    public List<String> generatePalindromes(String s) {
        if(s.length()==0) return Collections.emptyList();
        if(s.length()==1) return Collections.singletonList(s);
        char[] a = s.toCharArray();
        List<String> rs = new ArrayList<>(a.length<<3);
        int[] map = new int[96]; // skip control character to speed up later iteration.
        for(char c:a) map[c-' ']++;
        Character mid = null;
        for(int i=0;i<map.length;i++){
            if(map[i]%2!=0){
                if(mid==null){
                    mid = (char)(i+' ');
                    map[i]--;
                }
                else return Collections.emptyList();
            }
        }
        dfs(rs,map,mid,new StringBuilder(a.length),a.length);
        return rs;
    }

    private void dfs(List<String> rs,int[] map,Character mid,StringBuilder sb,int len){
        boolean hasMid = mid!=null;
        if((sb.length()<<1)+(hasMid?1:0)==len){
            rs.add(sb.toString()+(hasMid?mid:"")+sb.reverse().toString());
            sb.reverse();
            return;
        }
        for(int i=0;i<map.length;i++){
            if(map[i]>0){
                sb.append((char)(i+' '));
                map[i]-=2;
                dfs(rs,map,mid,sb,len);
                map[i]+=2;
                sb.delete(sb.length()-1,sb.length());
            }
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
//        System.out.println(s.generatePalindromes("aabb").toString());
//        System.out.println(s.generatePalindromes("abb").toString());
//        System.out.println(s.generatePalindromes("a").toString());
        System.out.println(s.generatePalindromes("ababaaaaaabbb").toString());
    }
}
