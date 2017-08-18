package WordSearchII_212;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Given a 2D board and a list of words from the dictionary,
 * find all words in the board.
 *
 * Each word must be constructed from letters of sequentially adjacent cell,
 * where "adjacent" cells are those horizontally or vertically neighboring.
 * The same letter cell may not be used more than once in a word.
 *
 * For example,
 * Given words = ["oath","pea","eat","rain"] and board =
 * [
 *   ['o','a','a','n'],
 *   ['e','t','a','e'],
 *   ['i','h','k','r'],
 *   ['i','f','l','v']
 * ]
 * Return ["eat","oath"].
 * Note:
 * You may assume that all inputs are consist of lowercase letters a-z.
 */
public class Solution {

    private int[][] delta = new int[][]{
            {0,1},
            {0,-1},
            {1,0},
            {-1,0}
    };

    public List<String> findWords(char[][] board, String[] words) {
        if(board.length==0) return Collections.emptyList();
        int rows = board.length,cols = board[0].length;
        TrieNode root = buildTrie(words);
        List<String> rs = new ArrayList<>();
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                TrieNode cur = root.child[board[i][j]-'a'];
                if(cur!=null)
                    dfs(board,i,j,cur,rs);
            }
        }
        return rs;
    }

    private void dfs(char[][] board,int row,int col,TrieNode cur,List<String> rs){
        if(cur.str!=null){
            rs.add(cur.str);
            cur.str = null;
        }
        char tm = board[row][col];
        board[row][col] = ' ';
        for(int[] d:delta){
            int nextRow = row+d[0],nextCol = col+d[1];
            if(nextRow>-1&&nextRow<board.length&&nextCol>-1&&nextCol<board[0].length
                    &&board[nextRow][nextCol]!=' '&&cur.child[board[nextRow][nextCol]-'a']!=null)
                dfs(board,nextRow,nextCol,cur.child[board[nextRow][nextCol]-'a'],rs);
        }
        board[row][col] = tm;
    }

    private TrieNode buildTrie(String[] words){
        TrieNode root = new TrieNode();
        for(String s:words){
            TrieNode cur = root;
            for(int i=0;i<s.length();i++){
                int key = s.charAt(i)-'a';
                if(cur.child[key]==null) cur.child[key] = new TrieNode();
                cur = cur.child[key];
            }
            cur.str = s;
        }
        return root;
    }

    class TrieNode{
        TrieNode[] child = new TrieNode[26];
        String str = null;
        TrieNode(){}
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        char[][] b = new char[][]{
                "oaan".toCharArray(),
                "etae".toCharArray(),
                "ihkr".toCharArray(),
                "iflv".toCharArray()
        };
        String[] w = new String[]{ "oath","pea","eat","rain" };
        System.out.println(s.findWords(b,w));
    }
}
