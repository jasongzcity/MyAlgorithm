package GroupAnagrams_49;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given an array of strings, group anagrams together.
 *
 * For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"],
 * Return:
 * [
 *   ["ate", "eat","tea"],
 *   ["nat","tan"],
 *   ["bat"]
 * ]
 * Note: All inputs will be in lower-case
 */
public class Solution {
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> strMap = new HashMap<>();
        for (String s : strs) {
            String revised = strSort(s); // O(2n),better than sort(s.toCharArray())
            List<String> list = strMap.getOrDefault(revised, new ArrayList<>());
            list.add(s);
            strMap.putIfAbsent(revised, list);
        }

        List<List<String>> rs = new ArrayList<>(strMap.values());
        return rs;
    }

    // inspire by the most voted c++ solution on leetcode
    // since we already know the range of the elements in the
    // char array, we can use bucket sort.
    private static String strSort(String s) {
        int[] buckets = new int[26];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            buckets[c - 'a']++; // get appearance times
        }
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < buckets.length; i++)
            for (int j = 0; j < buckets[i]; j++)
                sb.append(((char) (i + 'a')));
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] strs = new String[]{"b","a"};
        List<List<String>> rs = groupAnagrams(strs);
        for (List<String> l : rs) {
            for (String s : l) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }
}