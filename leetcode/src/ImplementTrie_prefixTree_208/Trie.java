package ImplementTrie_prefixTree_208;

/**
 * Implement a trie with insert, search, and startsWith methods.
 *
 * Note:
 * You may assume that all inputs are consist of lowercase letters a-z.
 */
public class Trie {

    // Second session
    // Redo data structure design
    class Node2 {
        boolean flag = false;
        Node2[] child = new Node2[26];
    }

    private Node2 r = new Node2();

    public Trie(int dummy){}

    public void insert2(String word){
        int p = 0;
        Node2 cur = r;
        while(p<word.length()){
            int ind = word.charAt(p++) - 'a';
            if(cur.child[ind] == null){
                cur.child[ind] = new Node2();
            }
            cur = cur.child[ind];
        }
        cur.flag = true;
    }

    public boolean search2(String word){
        int p = 0;
        Node2 cur = r;
        while(p<word.length()){
            int ind = word.charAt(p++) - 'a';
            if(cur.child[ind] == null)
                return false;
            cur = cur.child[ind];
        }
        return cur.flag;
    }

    // this is almost the same with search method
    public boolean startsWith2(String word){
        int p = 0;
        Node2 cur = r;
        while(p<word.length()){
            int ind = word.charAt(p++) - 'a';
            if(cur.child[ind] == null)
                return false;
            cur = cur.child[ind];
        }
        return true;
    }

    // below are first session code
    private Node root = new Node();

    /** Initialize your data structure here. */
    public Trie(){}

    /** Inserts a word into the trie. */
    public void insert(String word) {
        Node cur = root;
        char[] s = word.toCharArray();
        int pointer = 0;
        while(pointer<s.length){
            int key = s[pointer++]-'a';
            if(cur.children[key]==null) cur.children[key] = new Node();
            cur = cur.children[key];
        }
        cur.str = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node cur = root;
        char[] s = word.toCharArray();
        int pointer = 0;
        while(pointer<s.length){
            int key = s[pointer++]-'a';
            if(cur.children[key]==null) return false;
            cur = cur.children[key];
        }
        return cur.str;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node cur = root;
        char[] s = prefix.toCharArray();
        int pointer = 0;
        while(pointer<s.length){
            int key = s[pointer++]-'a';
            if(cur.children[key]==null) return false;
            cur = cur.children[key];
        }
        return true;
    }

    class Node{
        Node[] children = new Node[26];
        boolean str = false;
        Node(){}
    }
}
