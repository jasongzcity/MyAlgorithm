package WordBreak_139;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Given a non-empty string s and a dictionary wordDict containing
 * a list of non-empty words,
 * determine if s can be segmented into a space-separated sequence of
 * one or more dictionary words.
 * You may assume the dictionary does not contain duplicate words.
 *
 * For example, given
 * s = "leetcode",
 * dict = ["leet", "code"].
 *
 * Return true because "leetcode" can be segmented as "leet code".
 */
public class Solution {
    // Unaccepted. TLE....
    public boolean wordBreak(String s, List<String> wordDict) {
        int size = wordDict.size(),len = s.length();
        if(size==0) return false;
        Map<Integer,Integer> wordsMap = new HashMap<>(size<<2);
        for(String word:wordDict){
            int begin = 0,index;
            while(begin<len){
                index = s.indexOf(word,begin);
                if(index==-1) break;
                begin = index+1; // if matched, keep searching( the pattern could match elsewhere )
                int endIndex = index+word.length();
                while(wordsMap.containsKey(index)) index += (len<<1); // index the word
                wordsMap.put(index,endIndex);
            }
        }
        // Now do DFS search
        return walk(wordsMap,0,len);
    }

    private boolean walk(Map<Integer,Integer> wordsMap,Integer cur,int len){
        if(cur==len) return true;
        Integer next = wordsMap.get(cur); // next target.

        boolean found = false;
        while(next!=null&&!found){
            found = walk(wordsMap,next,len);
            cur+=(len<<1);
            next = wordsMap.get(cur);
        }
        return found;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String[] a = new String[]{
                "a","aa","aaa","aaaa","aaaaaa"
        };
        String[] b = new String[]{
                "aba","ba","baba","ban","dk","dnkba"
        };

        List<String> list = Arrays.asList(a);
        System.out.println(s.wordBreak2(str,list));
        System.out.println(s.wordBreakDP2(str,list));
        list = Arrays.asList(b);
        System.out.println(s.wordBreakDP2(str,list));
//        list = Arrays.asList(b);
//        System.out.println(s.wordBreak2(str,list));
    }

    // BFS
    public boolean wordBreak2(String s, List<String> wordDict){
        int size = wordDict.size(),len = s.length();
        Set<String> words = new HashSet<>(size);
        Set<Integer> visited = new HashSet<>(size);
        for(String str:wordDict) words.add(str);
        Queue<Integer> indice = new ArrayDeque<>();
        indice.add(0);
        char[] value = s.toCharArray();
        while(!indice.isEmpty()){
            Integer cur = indice.poll();
            if(!visited.contains(cur)){
                visited.add(cur);
                for(int i=cur;i<len;i++){
                    String newStr = new String(value,cur,i-cur+1); // "neighbors"
                    if(words.contains(newStr)){
                        if(i==len-1) return true;
                        indice.add(i+1);
                    }
                }
            }
        }
        return false;
    }

    // DP
    public boolean wordBreakDP(String s, List<String> wordDict){
        int size = wordDict.size(),len = s.length();
        if(size==0) return false;
        Map<Integer,Integer> wordsMap = new HashMap<>(size<<2);
        for(String word:wordDict){
            int begin = 0,index;
            while(begin<len){
                index = s.indexOf(word,begin);
                if(index==-1) break;
                begin++;
                int end = index+word.length();
                while(wordsMap.containsKey(end)) end+=(len<<1);
                wordsMap.put(end,index);
            }
        }
        // DP
        boolean[] dp = new boolean[len+1];
        dp[0] = true;
        for(int i=1;i<dp.length;i++){
            Integer v = wordsMap.get(i),key = i;
            while(v!=null&&!dp[i]){
                dp[i] = dp[v];
                key += (len<<1);
                v = wordsMap.get(key);
            }
        }
        return dp[len];
    }

    // optimized of the solution above
    public boolean wordBreakDP2(String s, List<String> wordDict){
        int size = wordDict.size(),len = s.length();
        if(size==0) return false;
        Map<Integer,List<Integer>> wordsMap = new HashMap<>(size<<2);
        for(String word:wordDict){
            int begin = 0,index;
            while(begin<len){
                index = s.indexOf(word,begin);
                if(index==-1) break;
                begin++;
                int end = index+word.length();
                List<Integer> list = wordsMap.getOrDefault(end,new ArrayList<>());
                list.add(index);
                wordsMap.putIfAbsent(end,list);
            }
        }
        // DP
        boolean[] dp = new boolean[len+1];
        dp[0] = true;
        for(int i=1;i<dp.length;i++){
            List<Integer> list = wordsMap.get(i);
            if(list==null) continue;
            int j = 0;
            while(j<list.size()&&!dp[i]) dp[i] = dp[list.get(j++)];
        }
        return dp[len];
    }

    // most voted DP solution
    // This would produce a lot of garbage...
    // This solution perform really well!
    public boolean wordBreakDP3(String s, List<String> wordDict){
        int len = s.length(),maxLength = 0;
        Set<String> words = new HashSet<>();
        for(String str:wordDict){
            words.add(str);
            if(str.length()>maxLength) maxLength = str.length();
        }
        boolean[] dp = new boolean[len+1];
        dp[0] = true;
        for(int i=1;i<=len;i++){
            int start = Math.max(0,i-maxLength);
            for(int j=start;j<i;j++){
                if(dp[j]&&words.contains(s.substring(j,i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[len];
    }
}
