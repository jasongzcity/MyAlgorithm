package PalindromePairs_336;

import java.util.*;

/**
 * Given a list of unique words, find all pairs of distinct indices (i, j) in the given list,
 * so that the concatenation of the two words, i.e. words[i] + words[j] is a palindrome.
 *
 * Example 1:
 * Given words = ["bat", "tab", "cat"]
 * Return [[0, 1], [1, 0]]
 * The palindromes are ["battab", "tabbat"]
 *
 * Example 2:
 * Given words = ["abcd", "dcba", "lls", "s", "sssll"]
 * Return [[0, 1], [1, 0], [3, 2], [2, 4]]
 * The palindromes are ["dcbaabcd", "abcddcba", "slls", "llssssll"]
 */
public class Solution {
    // Most intuitive thought:
    // check if all the combinations are palindrome
    // Of course, it's TLE
    public List<List<Integer>> palindromePairs3(String[] words) {
        List<List<Integer>> rs = new ArrayList<>();
        for(int i=0;i<words.length;i++){
            for(int j=0;j<words.length;j++){
                if(i!=j && isPalindrome(words[i]+words[j])){
                    List<Integer> l = new ArrayList<>();
                    l.add(i);
                    l.add(j);
                    rs.add(l);
                }
            }
        }
        return rs;
    }

    private boolean isPalindrome(String s){
        int left = 0, right = s.length()-1;

        while(left<right)
            if(s.charAt(left++) != s.charAt(right--))
                return false;
        return true;
    }

    private boolean isPalindrome(String s, int i, int j){
        while(i<j)
            if(s.charAt(i++) != s.charAt(j--))
                return false;
        return true;
    }

    // Improvement
    // Using map to find palindrome
    public List<List<Integer>> palindromePairs2(String[] words){
        List<List<Integer>> rs = new ArrayList<>();
        if(words.length<2) return rs;

        Map<String, Integer> map = new HashMap<>(words.length*2);
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }
        for(int j=0;j<words.length;j++){
            for(int i=0;i<words[j].length();i++){
                if(isPalindrome(words[j], 0, i)){
                    String s2rsv = new StringBuilder(words[j].substring(i+1)).reverse().toString();
                    Integer posi = map.get(s2rsv);
                    if(posi!=null&&posi!=j){
                        rs.add(Arrays.asList(posi, j));
                    }
                }

                if(isPalindrome(words[j], i+1, words[j].length()-1)){
                    String s1rsv = new StringBuilder(words[j].substring(0, i+1)).reverse().toString();
                    Integer posi = map.get(s1rsv);
                    if(posi!=null&&posi!=j){
                        rs.add(Arrays.asList(j, posi));
                    }
                }
            }

            Integer empty = map.get("");
            if(empty!=null && empty != j && isPalindrome(words[j], 0, words[j].length()-1)){
                rs.add(Arrays.asList(j, empty));
            }
        }
        return rs;
    }

    // Improvement:
    // use Trie instead of map
    class Node{
        Node[] child = new Node[26];
        int wholeInd = -1;
        List<Integer> l = new ArrayList<>();
    }

    private Node r = new Node();

    private void addWord(String[] words, int index){
        Node cur = r;
        for(int i=words[index].length()-1; i>=0; i--){
            int tm = words[index].charAt(i) - 'a';
            if(isPalindrome(words[index], 0, i)) cur.l.add(index); // words[j]+word[index] potential
            if(cur.child[tm]==null) cur.child[tm] = new Node();
            cur = cur.child[tm];
        }

        cur.wholeInd = index;
        cur.l.add(index);
    }

    private void search(String[] words, int index, List<List<Integer>> rs){
        Node cur = r;
        for(int i=0;i<words[index].length();i++){
            if(cur.wholeInd>=0&&cur.wholeInd!=index&&isPalindrome(words[index], i, words[index].length()-1)){
                // words[index] + words[cur.wholeInd]
                rs.add(Arrays.asList(index, cur.wholeInd));
            }
            cur = cur.child[words[index].charAt(i) - 'a'];
            if(cur==null) return;
        }

        for(int j:cur.l)
            if(index!=j)    // consider there is a "s" in the words.
                rs.add(Arrays.asList(index, j));
    }

    public List<List<Integer>> palindromePairs(String[] words){
        List<List<Integer>> rs = new ArrayList<>();
        if(words.length < 2) return rs;

        for(int i=0;i<words.length;i++) addWord(words, i);
        for(int i=0;i<words.length;i++) search(words, i, rs);

        return rs;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.palindromePairs2(new String[]{"a",""}));
    }
}
