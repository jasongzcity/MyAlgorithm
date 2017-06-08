package WordLadder_127;

import java.util.*;

/**
 * Given two words (beginWord and endWord), and a dictionary's word list,
 * find the length of shortest transformation sequence from beginWord to endWord,
 * such that:
 * Only one letter can be changed at a time.
 * Each transformed word must exist in the word list.
 * Note that beginWord is not a transformed word.
 *
 * For example,
 * Given:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log","cog"]
 *
 * As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * return its length 5.
 *
 * Note:
 * Return 0 if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * You may assume no duplicates in the word list.
 * You may assume beginWord and endWord are non-empty and are not the same.
 */
public class Solution {
    // This solution is unaccepted: TLE
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        wordList.add(beginWord);
        int distance = dfs(wordList,endWord,wordList.size()-1,
                new boolean[wordList.size()],new int[wordList.size()][wordList.size()]);
        return distance==-1?0:distance;
    }

    private int dfs(List<String> list,String endWord,int begin,
                    boolean[] visiting,int[][] access){
        // flags in memo: memo[i] represents the the distance
        // from the ith word(in the list) to the endWord( the end of the dfs)
        // 0 represents undecided, -1 represents can't access endWord
        // access represents if the i word can go to j word.
        // 0 represents undecided, -1 represents i can't access j directly,
        // 1 represents i can access j directly
        String beginWord = list.get(begin);
        if(beginWord.equals(endWord)) return 1;

        visiting[begin] = true;
        int min = Integer.MAX_VALUE;
        for(int i=0;i<list.size()-1;i++){
            if(visiting[i]||access[begin][i]==-1) continue;
            if(access[begin][i]==0){
                if(checkString(beginWord,list.get(i))){
                   access[begin][i] = 1;
                   access[i][begin] = 1;
                }else{
                    access[begin][i] = -1;
                    access[i][begin] = -1;
                    continue;
                }
            }
            int distance = dfs(list,endWord,i,visiting,access);
            if(distance!=-1) min = Math.min(min,distance);
        }
        min = min==Integer.MAX_VALUE?-1:min+1;
        visiting[begin] = false;
        return min;
    }

    // This check is very slow..........
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
//        System.out.println(s.ladderLength("hit","cog",wordList));
        System.out.println(s.ladderLength2("hit","cog",wordList));
    }

    // BFS using queue
    // This solution is unaccepted: TLE
    public int ladderLength2(String beginWord, String endWord, List<String> wordList){
        Queue<Integer> q = new ArrayDeque<>();
        wordList.add(beginWord);
        int count = 0,listSize = wordList.size();
        q.add(listSize-1);
        boolean[] visiting = new boolean[listSize];
        int[][] access = new int[listSize][listSize];
        while(!q.isEmpty()){
            int size = q.size();
            count++;
            for(int i=0;i<size;i++){ // get current "level"
                int index = q.poll();
                String cur = wordList.get(index);
                visiting[index] = true;
                for(int j=0;j<listSize-1;j++){
                    if(visiting[j]||access[index][j]==-1) continue;
                    if(access[index][j]==0){
                        if(checkString(cur,wordList.get(j))){
                            access[index][j] = 1;
                            access[j][index] = 1;
                        }else{
                            access[index][j] = -1;
                            access[j][index] = -1;
                            continue;
                        }
                    }
                    if(wordList.get(j).equals(endWord)) return count+1;
                    q.add(j);
                }
            }
        }
        return 0;
    }

    // optimized BFS (so-called two end)
    public int ladderLength3(String beginWord, String endWord, List<String> wordList){
        int wordLen = wordList.get(0).length();
        int size = wordList.size(),level = 1;
        Set<String> set1 = new HashSet<>(size),set2 = new HashSet<>(size),dict = new HashSet<>(size);
        set1.add(beginWord);
        set2.add(endWord);
        for(String s:wordList) dict.add(s);
        if(!dict.contains(endWord)) return 0;
        dict.remove(endWord);
        while(!set1.isEmpty()&&!set2.isEmpty()){
            boolean firstLarger = set1.size()>=set2.size();
            Set<String> smaller = firstLarger?set2:set1;
            Set<String> larger = firstLarger?set1:set2;
            Set<String> next = new HashSet<>();
            ++level;
            for(String s:smaller){
                StringBuilder sb = new StringBuilder(s);
                for(int i=0;i<wordLen;i++){
                    char backup = s.charAt(i);
                    for(char c='a';c<='z';c++){
                        sb.setCharAt(i,c);
                        String key = sb.toString();
                        if(larger.contains(key)) return level;
                        if(dict.contains(key)){
                            next.add(key);
                            dict.remove(key); // visited
                        }
                    }
                    sb.setCharAt(i,backup);
                }
            }
            // BFS, becomes its neighbors
            if(firstLarger) set2 = next;
            else set1 = next;
        }
        return 0;
    }
}
