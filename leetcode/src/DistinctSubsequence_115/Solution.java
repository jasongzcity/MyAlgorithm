package DistinctSubsequence_115;

/**
 * Given a string S and a string T,
 * count the number of distinct subsequences of T in S.
 *
 * A subsequence of a string is a new string which is formed
 * from the original string by
 * deleting some (can be none) of the characters without
 * disturbing the relative positions of the remaining characters.
 * (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).
 *
 * Here is an example:
 * S = "rabbbit", T = "rabbit"
 *
 * Return 3.
 */
public class Solution {
    // DFS with memo
    // This solution beats 99% on leetcode OJ!!!!
    public static int numDistinct(String s, String t) {
        if(s.length()<t.length()) return 0;
        int rs = checkNext(t.toCharArray(),0,s.toCharArray(),0,new int[t.length()][s.length()]);
        return rs<0?0:rs;
    }

    private static int checkNext(char[] t,int tarindex,char[] s,int begin,
                                 int[][] memo){
        // flags in the memo: 0 undecided, -1 not match(return directly)
        // other integers represent the numbers of subsequences.
        if(tarindex==t.length) return 1;
        if(memo[tarindex][begin]!=0) return memo[tarindex][begin];
        int count = 0,remain = t.length-tarindex;
        for(int i=begin;i<=s.length-remain;i++){
            if(s[i]==t[tarindex]){
                int rs = checkNext(t,tarindex+1,s,i+1,memo);
                if(rs!=-1) count+=rs;
                else break; // no more subsequences, stop checking
            }
        }
        count = count==0?-1:count;
        memo[tarindex][begin] = count;
        return count;
    }

    public static void main(String[] args) {
        String s = "ABCDE",t = "ACE";
        System.out.println(numDistinct(s,t));
        s = "rabbbit";
        t = "rabbit";
        System.out.println(numDistinct(s,t));
        s = "abcde";
        t = "ABCDE";
        System.out.println(numDistinct(s,t));
        s = "rbbbt";
        t = "rbbt";
        System.out.println(numDistinct2(s,t));
    }

    // most voted solution on leetcode
    // very straight forward dp solution
    public static int numDistinct2(String s, String t) {
        int slen = s.length(),tlen = t.length();
        int[][] mem = new int[tlen+1][slen+1];

        // filling the first row with 1
        for(int j=0; j<=slen; j++) mem[0][j] = 1;

        for(int i=0; i<tlen; i++)
            for(int j=0; j<slen; j++)
                if(t.charAt(i) == s.charAt(j)) mem[i+1][j+1] = mem[i][j] + mem[i+1][j];
                else mem[i+1][j+1] = mem[i+1][j];
        return mem[tlen][slen];
    }
}
