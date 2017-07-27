package AlienDictionary_269;

import javax.sound.midi.Soundbank;

/**
 * There is a new alien language which uses the latin alphabet.
 * However, the order among letters are unknown to you.
 * You receive a list of non-empty words from the dictionary,
 * where words are sorted lexicographically by the rules of this new language.
 * Derive the order of letters in this language.
 *
 * Example 1:
 * Given the following words in dictionary,
 * [
 * "wrt",
 * "wrf",
 * "er",
 * "ett",
 * "rftt"
 * ]
 * The correct order is: "wertf".
 *
 * Example 2:
 * Given the following words in dictionary,
 * [
 * "z",
 * "x"
 * ]
 * The correct order is: "zx".
 *
 * Example 3:
 * Given the following words in dictionary,
 * [
 * "z",
 * "x",
 * "z"
 * ]
 * The order is invalid, so return "".
 *
 * Note:
 * You may assume all letters are in lowercase.
 * You may assume that if a is a prefix of b,
 * then a must appear before b in the given dictionary.
 * If the order is invalid, return an empty string.
 * There may be multiple valid order of letters, return any one of them is fine.
 */
public class Solution {
    public String alienOrder(String[] words) {
        if(words.length==0) return "";
        StringBuilder sb = new StringBuilder(words.length+1);
        // build the graph
        boolean[][] gragh = new boolean[26][26];
        // flags:
        // 0 visited/not in graph
        // 1 visiting
        // 2 not visited
        int[] visited = new int[26];
        String cur = words[0],next;
        for(int i=0;i<words.length-1;i++){
            next = words[i+1];
            int p = 0;
            // we assume len(cur) < len(next)
            while(p<cur.length()&&cur.charAt(p)==next.charAt(p))
                visited[cur.charAt(p++)-'a'] = 2;
            if(p<cur.length()) gragh[cur.charAt(p)-'a'][next.charAt(p)-'a'] = true;
            while(p<cur.length()) visited[cur.charAt(p++)-'a'] = 2;
            cur = next;
        }
        int p = 0;
        while(p<cur.length()) visited[cur.charAt(p++)-'a'] = 2; // mark the last string

        // then we do DFS
        for(int i=0;i<26;i++)
            if(!dfs(i,visited,gragh,sb))
                return "";
        return sb.reverse().toString();
    }

    private boolean dfs(int cur,int[] visited,boolean[][] graph,StringBuilder sb){
        if(visited[cur]==2){
            visited[cur] = 1;
            for(int i=0;i<26;i++)
                if(graph[cur][i]&&!dfs(i,visited,graph,sb))
                    return false;
            sb.append((char)(cur+'a'));
            visited[cur] = 0;
        }
        return visited[cur]!=1;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        String[] w1 = { "wrt", "wrf", "er", "ett", "rftt" };
        String[] w2 = {};
        String[] w3 = {"w","x","w"};
        System.out.println(s.alienOrder(w1));
        System.out.println(s.alienOrder(w2));
        System.out.println(s.alienOrder(w3));
    }
}
