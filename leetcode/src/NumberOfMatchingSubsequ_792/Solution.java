package NumberOfMatchingSubsequ_792;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    // TLE ..
    public int numMatchingSubseq(String S, String[] words) {
        List<Integer> l = new ArrayList<>(words.length);
        for (int i = 0; i < words.length; i++) {
            l.add(0);
        }

        int rs = 0;
        for (char c : S.toCharArray()) {
            for (int i = 0; i < words.length; i++) {
                int index = l.get(i);
                if (index < words[i].length()) {
                    if (c == words[i].charAt(index)) {
                        l.set(i, index + 1);
                        if (index == words[i].length() - 1) {
                            // match  the last char
                            rs++;
                        }
                    }
                }
            }
        }
        return rs;
    }

    class Node {
        char c;
        Node[] child = new Node[26];
        int wordCount = 0;
    }

    // build a trie of words???
    public int numMatchingSubseq2(String S, String[] words) {
        Node root = new Node(), cur = root;
        for (String word : words) {
            cur = root;
            for (char c : word.toCharArray()) {
                if (cur.child[c - 'a'] == null) cur.child[c - 'a'] = new Node();
                cur = cur.child[c - 'a'];
            }
            cur.wordCount++;
        }

        List<Node> potentials = new ArrayList<>();
        int rs = 0;
        potentials.add(root);
        for (char c : S.toCharArray()) {
            int size = potentials.size();
            int removeCount = 0;
            for (int i = 0, ptr = 0; i < size; i++) {
                Node n = potentials.get(ptr);
                Node next = n.child[c - 'a'];
                if (next != null){
                    potentials.add(next);
                    rs += next.wordCount;
                }
                n.child[c - 'a'] = null;
                boolean delete = true;
                for (Node nn : n.child) {
                    if (nn != null) {
                        delete = false;
                        break;
                    }
                }
                if (delete) {
                    potentials.remove(ptr);
                } else {
                    ptr++;
                }

            }
        }
        return rs;
    }

    // similar idea but much faster
    // it's like putting pointers on every words,
    // but you only need to scan part of them
    public int numMatchingSubseq3(String S, String[] words) {
        // use array to store pairs <word, index>
        // if using CPP could use pair, but here we can only use integer array
        List<Integer[]>[] letters = new List[128];

        for (int i = 'a'; i <= 'z'; i++) letters[i] = new ArrayList<>();

        // initialize words' positions
        for (int i = 0; i < words.length; i++) letters[words[i].charAt(0)].add(new Integer[]{i, 1});

        int rs = 0;
        for (char c : S.toCharArray()) {
            List<Integer[]> copy = new ArrayList<>(letters[c]);
            letters[c].clear(); // update all
            for (Integer[] pair : copy) {
                if (pair[1] == words[pair[0]].length()) ++rs;   // we got a matched sequence
                else letters[words[pair[0]].charAt(pair[1]++)].add(pair);   // move pointer
            }
        }

        return rs;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.numMatchingSubseq2("abcde", new String[]{"a", "bb", "acd", "ace"}));
    }
}
