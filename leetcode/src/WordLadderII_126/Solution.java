package WordLadderII_126;

import java.util.*;

/**
 * Given two words (beginWord and endWord), and a dictionary's word list,
 * find all shortest transformation sequence(s) from beginWord to endWord, such that:
 *
 * Only one letter can be changed at a time
 * Each transformed word must exist in the word list.
 * Note that beginWord is not a transformed word.
 *
 * For example,
 * Given:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log","cog"]
 *
 * Return
 * [
 *  ["hit","hot","dot","dog","cog"],
 *  ["hit","hot","lot","log","cog"]
 * ]
 *
 * Note:
 * Return an empty list if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * You may assume no duplicates in the word list.
 * You may assume beginWord and endWord are non-empty and are not the same.
 */
public class Solution {
    // Imma gonna try DFS with memo again.
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList){
        List<List<String>> rs = new ArrayList<>();
        List<String> list = new ArrayList<>(wordList.size());
        wordList.add(beginWord);
        int size = wordList.size();
        dfs(wordList,list,rs,size-1,new boolean[size],new int[size][size]);
        return rs;
    }

    private void dfs(List<String> words,List<String> list,List<List<String>> rs,
                     int current,boolean[] visiting,int[][] access){
        String begin = words.get(current);
        list.add(begin);
        if(current==words.size()-2){
            rs.add(new ArrayList<>(list));
            list.remove(list.size()-1);
            return;
        }
        visiting[current] = true;
        for(int i=0;i<words.size()-1;i++){
            if(visiting[i]||access[current][i]==-1) continue;
            if(access[current][i]==0){
                if(checkString(begin,words.get(i))){
                    access[current][i] = 1;
                    access[i][current] = 1;
                }else{
                    access[current][i] = -1;
                    access[i][current] = -1;
                    continue;
                }
            }
            dfs(words,list,rs,i,visiting,access);
        }
        visiting[current] = false;
        list.remove(list.size()-1);
    }

    private boolean checkString(String s1,String s2){
        // words in same length is assured.
        int len = s1.length(),count = 0;
        for (int i = 0; i < len; i++) {
            if(s1.charAt(i) != s2.charAt(i)) {
                if (count == 1) return false;
                else count++;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        List<String> wordList = new ArrayList<>();
        wordList.add("hot");
        wordList.add("dot");
        wordList.add("dog");
        wordList.add("lot");
        wordList.add("log");
        wordList.add("cog");
//        System.out.println(s.findLadders2("hit","cog",wordList).toString());
        System.out.println(s.findLadders3("hit","cog",wordList).toString());
    }

    // BFS then DFS
    // single end BFS
    // This solution is unaccepted. TLE
    public List<List<String>> findLadders2(String beginWord, String endWord, List<String> wordList){
        int size = wordList.size(),wordLen = beginWord.length();
        Set<String> beginSet = new HashSet<>(size),dict = new HashSet<>(size);
        Map<String,String> tracking = new HashMap<>(size);
        for(String s:wordList) dict.add(s);
        beginSet.add(beginWord);
        int count = 1;
        List<List<String>> rs = new ArrayList<>();
        boolean found = false;
        outer:
        while(!beginSet.isEmpty()&&!found){
            ++count;
            Set<String> next = new HashSet<>(); // notice: this is much faster than using a queue
            for(String s:beginSet){
                StringBuilder sb = new StringBuilder(s);
                for(int i=0;i<wordLen;i++){
                    char backup = s.charAt(i);
                    for(char c='a';c<='z';c++){
                        sb.setCharAt(i,c);
                        String key = sb.toString();
                        if(dict.contains(key)){
                            tracking.put(key,s);
                            if(key.equals(endWord)){ // ladder found!
                                List<String> list = new LinkedList<>();
                                while(key!=null){
                                    list.add(0,key);
                                    key = tracking.get(key);
                                }
                                System.out.println("output 1:"+list.toString()); // output one ladder
                                found = true;
                                break outer;
                            }else{
                                next.add(key);
                                dict.remove(key);
                            }
                        }
                    }
                    sb.setCharAt(i,backup);
                }
            }
            beginSet = next;
        }
        if(!found) return rs;
        // now use dfs to find ladders..
        List<String> list = new ArrayList<>(count);
        list.add(beginWord);
        for(String s:wordList) dict.add(s);
        findNext(rs,list,beginWord,endWord,count-1,dict,wordLen);
        return rs;
    }

    private void findNext(List<List<String>> rs,List<String> list,String current,
                          String endWord,int steps,Set<String> dict,int wordLen){
        if(current.equals(endWord)){
            rs.add(new ArrayList<>(list));
            return;
        }
        if(steps==0) return;
        StringBuilder sb = new StringBuilder(current);
        for(int i=0;i<wordLen;i++){
            char backup = current.charAt(i);
            for(char c='a';c<='z';c++){
                sb.setCharAt(i,c);
                String key = sb.toString();
                if(dict.contains(key)){
                    dict.remove(key);
                    list.add(key);
                    findNext(rs,list,key,endWord,steps-1,dict,wordLen);
                    list.remove(list.size()-1);
                    dict.add(key); // backtrack
                }
            }
            sb.setCharAt(i,backup);
        }
    }

    // make a graph
    public List<List<String>> findLadders3(String beginWord, String endWord, List<String> words){
        words.add(beginWord);
        int size = words.size(),wordLen = beginWord.length();
        Map<String,Set<String>> graph = new HashMap<>(size);
        Set<String> dict = new HashSet<>(size),beginSet = new HashSet<>();
        for(String s:words) dict.add(s);
        if(!dict.contains(endWord)) return new ArrayList<>();
        // O(n^2) time to construct a graph
        for(String s:dict){
            StringBuilder sb = new StringBuilder(s);
            Set<String> neighbors = new HashSet<>();
            graph.put(s,neighbors);
            for(int i=0;i<wordLen;i++){
                char backup = s.charAt(i);
                for(char c='a';c<='z';c++){
                    if(c==backup) continue;
                    sb.setCharAt(i,c);
                    String neighbor = sb.toString();
                    if(dict.contains(neighbor)){
                        neighbors.add(neighbor);
                    }
                }
                sb.setCharAt(i,backup);
            }
        }
        beginSet.add(beginWord);
        dict.remove(beginWord);
        int count = 1;
        boolean found = false;
        outer:
        while(!beginSet.isEmpty()&&!found){
            ++count;
            HashSet<String> next = new HashSet<>();
            for(String s:beginSet) {
                Set<String> neighbors = graph.get(s);
                for(String neighbor:neighbors){
                    if(neighbor.equals(endWord)) {
                        found = true;
                        break outer;
                    }
                    if(dict.contains(neighbor)){ // unvisited
                        next.add(neighbor);
                        dict.remove(neighbor);
                    }
                }
            }
            beginSet = next;
        }
        if(!found) return new ArrayList<>();
        // now dfs search
        List<List<String>> rs = new ArrayList<>();
        List<String> list = new ArrayList<>(count);
        list.add(beginWord);
        for(String s:words) dict.add(s);
        dict.remove(beginWord);
        dfs2(rs,list,graph,count-1,dict,beginWord,endWord);
        return rs;
    }

    private void dfs2(List<List<String>> rs,List<String> list,Map<String,Set<String>> graph,
                      int step,Set<String> dict,String current,String endWord){
        Set<String> neighbors = graph.get(current);
        if(neighbors.contains(endWord)){
            List<String> newList = new ArrayList<>(list);
            newList.add(endWord);
            rs.add(new ArrayList<>(newList));
            return;
        }
        if(step==1) return;
        for(String neighbor:neighbors){
            if(dict.contains(neighbor)){
                list.add(neighbor);
                dict.remove(neighbor);
                dfs2(rs,list,graph,step-1,dict,neighbor,endWord);
                dict.add(neighbor);
                list.remove(list.size()-1); // backtrack
            }
        }
    }
}