package AddAndSearchWord_DSDesign_211;

import java.util.LinkedList;

/**
 * Design a data structure that supports the following two operations:
 *
 * void addWord(word)
 * bool search(word)
 * search(word) can search a literal word or a regular expression
 * string containing only letters a-z or .
 * A . means it can represent any one letter.
 *
 * For example:
 *
 * addWord("bad")
 * addWord("dad")
 * addWord("mad")
 * search("pad") -> false
 * search("bad") -> true
 * search(".ad") -> true
 * search("b..") -> true
 * Note:
 * You may assume that all words are consist of lowercase letters a-z.
 *
 * You should be familiar with how a Trie works.
 * If not, please work on this problem: Implement Trie (Prefix Tree) first.
 */
public class WordDictionary {

    private Node root = new Node();

    /** Initialize your data structure here. */
    public WordDictionary(){}

    /** Adds a word into the data structure. */
    public void addWord(String word) {
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

    /** Returns if the word is in the data structure.
     *  A word could contain the dot character '.' to represent any one letter. */
    public boolean search2(String word) {
        return dfs(root,word.toCharArray(),-1);
    }

    // Recursive solution
    private boolean dfs(Node root,char[] s,int cur){
        if(root==null) return false;
        if(cur==s.length-1) return root.str;
        char key = s[cur+1];
        if(key=='.'){
            for(int i=0;i<26;i++)
                if(root.children[i]!=null&&dfs(root.children[i],s,cur+1))
                    return true;
            return false;
        }else{
            return dfs(root.children[key-'a'],s,cur+1);
        }
    }

    class Node{
        Node[] children = new Node[26];
        boolean str = false;
        Node(){}
    }
}
