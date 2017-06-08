package WordBreakII_140;

import java.util.*;

/**
 * Given a non-empty string s and a dictionary wordDict containing a list of
 * non-empty words, add spaces in s to construct a sentence
 * where each word is a valid dictionary word.
 * You may assume the dictionary does not contain duplicate words.
 *
 * Return all such possible sentences.
 *
 * For example, given
 * s = "catsanddog",
 * dict = ["cat", "cats", "and", "sand", "dog"].
 *
 * A solution is ["cats and dog", "cat sand dog"].
 */
public class Solution {
    // This solution is unaccepted because its too slow
    // while constructing the graph(and it can't stop while
    // the string is unbreakable)
    public List<String> wordBreak2(String s, List<String> wordDict) {
        int size = wordDict.size(),len = s.length();
        List<String> rs = new ArrayList<>();
        boolean breakable = false;
        Map<Integer,List<Integer>> graph = new HashMap<>(size<<2);
        // construct a graph
        for(String str:wordDict){
            int begin = 0,index;
            while(begin<len){
                index = s.indexOf(str,begin);
                if(index==-1) break;
                begin = index+1;
                int end = index+str.length();
                if(end==len) breakable = true;
                List<Integer> list = graph.getOrDefault(index,new ArrayList<>());
                list.add(end);
                graph.putIfAbsent(index,list);
            }
        }

        // DFS search
        if(!breakable) return rs;
        dfs(rs,new StringBuilder(len<<1),s.toCharArray(),graph,0);
        return rs;
    }

    private void dfs(List<String> rs,StringBuilder sb,char[] value,
                     Map<Integer,List<Integer>> graph,Integer cur){
        if(cur==value.length){
            sb.delete(sb.length()-1,sb.length()); // delete the ending space
            rs.add(sb.toString());
            sb.append(' ');
            return;
        }
        List<Integer> nexts = graph.get(cur);
        if(nexts==null) return;
        for(Integer i:nexts){
            int length = i-cur;
            sb.append(value,cur,length).append(' ');
            dfs(rs,sb,value,graph,i);
            sb.delete(sb.length()-length-1,sb.length()); // backtrack
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "catsanddog";
        String[] strs = new String[]{
                "cat", "cats", "and", "sand", "dog"
        };
        List<String> dict = Arrays.asList(strs);
        System.out.println(solution.wordBreak(s,dict).toString());
    }

    // use BFS to construct graph instead
    // This solution performs well on the OJ
    public List<String> wordBreak(String s, List<String> wordDict){
        if(wordDict.size()==0) return new ArrayList<>();
        int len = s.length(),maxWord = 0;
        Set<String> dict = new HashSet<>(wordDict.size()<<1);
        Set<Integer> vistited = new HashSet<>(len);
        for(String str:wordDict){
            dict.add(str);
            if(str.length()>maxWord) maxWord = str.length();
        }
        Map<Integer,List<Integer>> graph = new HashMap<>();
        List<String> rs = new ArrayList<>();
        boolean breakable = false;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(0);
        while(!q.isEmpty()){
            int cur = q.poll(),length = 1,endIndex;
            List<Integer> list = graph.getOrDefault(cur,new ArrayList<>());
            for(;length<=maxWord;length++){
                endIndex = cur+length;
                if(endIndex>len) break;
                if(dict.contains(s.substring(cur,endIndex))){
                    list.add(endIndex);
                    if(!vistited.contains(endIndex)){ // not visit the position twice
                        vistited.add(endIndex);
                        q.add(endIndex);
                        if(endIndex==len) breakable = true;
                    }
                }
            }
            graph.putIfAbsent(cur,list);
        }
        if(!breakable) return rs;
        dfs(rs,new StringBuilder(len<<1),s.toCharArray(),graph,0);
        return rs;
    }
}
