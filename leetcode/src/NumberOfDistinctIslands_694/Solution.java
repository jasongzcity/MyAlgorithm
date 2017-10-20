package NumberOfDistinctIslands_694;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's
 * (representing land) connected 4-directionally (horizontal or vertical.)
 * You may assume all four edges of the grid are surrounded by water.
 *
 * Count the number of distinct islands.
 * An island is considered to be the same as another
 * if and only if one island can be translated
 * (and not rotated or reflected) to equal the other.
 *
 * Example 1:
 * 11000
 * 11000
 * 00011
 * 00011
 * Given the above grid map, return 1.
 *
 * Example 2:
 * 11011
 * 10000
 * 00001
 * 11011
 * Given the above grid map, return 3.
 *
 * Notice that:
 * 11
 * 1
 * and
 * 1
 * 11
 * are considered different island shapes,
 * because we do not consider reflection / rotation.
 * Note: The length of each dimension in the given grid does not exceed 50.
 */
public class Solution {

    // Redo
    public int numDistinctIslands(int[][] g){
        if(g.length==0) return 0;
        int rows = g.length, cols = g[0].length;
        Set<List<Integer>> comb = new HashSet<>();
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(g[i][j]==1){
                    List<Integer> l = new ArrayList<>();
                    dfs2(g,i,j,l,4);
                    comb.add(l);
                }
            }
        }
        return comb.size();
    }

    private void dfs2(int[][] g, int i, int j, List<Integer> l, int posi){
        if(i>=0&&j>=0&&i<g.length&&j<g[0].length&&g[i][j]==1){
            g[i][j] = 0;
            l.add(posi);
            dfs2(g,i-1,j,l,0);
            dfs2(g,i+1,j,l,1);
            dfs2(g,i,j-1,l,2);
            dfs2(g,i,j+1,l,3);
            l.add(4);
        }
    }

    // first thought: use some kind of mechanism to "memorize" the islands.
    // My first thought was something like a "Trie"
    // This is not accepted.......
    // you stuck in the wrong hole, again....
    // Think of some method to transform the dots in the matrix to coordinates.
    // then put coordinates into set(or in python we can use pair)
    // notice: don't use list, lists' hashCode is related to the elements'
    // sequences.
    public int numDistinctIslands2(int[][] g) {
        if(g.length==0) return 0;
        Node root = new Node();
        int count = 0;
        for(int i=0;i<g.length;i++){
            for(int j=0;j<g[0].length;j++){
                if(g[i][j]==1){
                    if(!dfs(g,i,j,root.child,0)){
                        // first met
                        ++count;
                    }
                }
            }
        }
        return count;
    }

    private boolean dfs(int[][] g, int i, int j, Node[] cur, int posi){
        // now we know g[i][j] == 1
        // set it to zero for further dfs
        g[i][j] = 0;
        boolean noNeighbor = true, eqNeigh = true;
        Node newNode = cur[posi];
        if(newNode==null){
            eqNeigh = false;
            newNode = new Node();
            cur[posi] = newNode;
        }
        if(valid(g,i,j-1)){
            noNeighbor = false;
            eqNeigh &= dfs(g,i,j-1,cur[posi].child,0);
        }else{
            eqNeigh &= (cur[posi].child[0]==null);
        }
        if(valid(g,i-1,j)){
            noNeighbor = false;
            eqNeigh &= dfs(g,i-1,j,cur[posi].child,1);
        }else{
            eqNeigh &= (cur[posi].child[1]==null);
        }
        if(valid(g,i,j+1)){
            noNeighbor = false;
            eqNeigh &= dfs(g,i,j+1,cur[posi].child,2);
        }else{
            eqNeigh &= (cur[posi].child[2]==null);
        }
        if(valid(g,i+1,j)){
            noNeighbor = false;
            eqNeigh &= dfs(g,i+1,j,cur[posi].child,3);
        }else{
            eqNeigh &= (cur[posi].child[3]==null);
        }
        if(noNeighbor){
            // special case
            boolean back = cur[posi].end;
            cur[posi].end = true;
            return back;
        }
        return eqNeigh;
    }

    private boolean valid(int[][] g, int i, int j){
        return (i>=0&&j>=0&&i<g.length&&j<g[0].length&&g[i][j]==1);
    }

    class Node{
        public Node[] child = new Node[4]; // left/up/right/down
        boolean end = false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.numDistinctIslands(new int[][]{
                {1,1,0,1,1},{1,0,0,0,0},{0,0,0,0,1},{1,1,0,1,1}
        }));
    }
}
