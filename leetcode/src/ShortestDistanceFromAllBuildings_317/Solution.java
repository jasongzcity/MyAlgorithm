package ShortestDistanceFromAllBuildings_317;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * You want to build a house on an empty land which reaches all buildings
 * in the shortest amount of distance. You can only move up, down, left and right.
 * You are given a 2D grid of values 0, 1 or 2, where:
 *
 * Each 0 marks an empty land which you can pass by freely.
 * Each 1 marks a building which you cannot pass through.
 * Each 2 marks an obstacle which you cannot pass through.
 * For example, given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2):
 *
 * 1 - 0 - 2 - 0 - 1
 * |   |   |   |   |
 * 0 - 0 - 0 - 0 - 0
 * |   |   |   |   |
 * 0 - 0 - 1 - 0 - 0
 * The point (1,2) is an ideal empty land to build a house,
 * as the total travel distance of 3+3+1=7 is minimal. So return 7.
 *
 * Note:
 * There will be at least one building.
 * If it is not possible to build such house according to the above rules, return -1.
 */
public class Solution {

    private int[] delta = {0,1,0,-1,0};

    // BFS from each '1'
    public int shortestDistance(int[][] g) {
        if(g.length==0||g[0].length==0) return -1;
        int m = g.length,n = g[0].length;
        List<Integer> starts = new ArrayList<>(36);
        int[][] hit = new int[m][n];
        for(int i=0;i<m;i++) {
            for (int j = 0; j < n; j++) {
                if (g[i][j] == 1) {
                    starts.add(i);
                    starts.add(j);
                }
            }
        }

        int num = starts.size()>>1;
        for(int i=0;i<starts.size();i+=2)
            if(BFS(g,hit,starts.get(i),starts.get(i+1))!=num)
                return -1;

        for(int i=0;i<starts.size();i+=2) hit[starts.get(i)][starts.get(i+1)]++;


        int max = Integer.MIN_VALUE;
        for(int i=0;i<m;i++)
            for (int j = 0; j < n; j++)
                if (hit[i][j] == num && g[i][j] > max)
                    max = g[i][j];
        return -max;
    }

    private int BFS(int[][] g,int[][] hit,int x,int y){
        int count1 = 1,dis = 1;
        Queue<Integer> q = new LinkedList<>();
        q.add(x);
        q.add(y);
        boolean check = false;
        int hitTime = ++hit[x][y];
        while(!q.isEmpty()){
            int size = q.size()>>1;
            for(int i=0;i<size;i++){
                int xx = q.poll(),yy = q.poll();
                for(int j=0;j<4;j++){
                    int nextx = xx+delta[j],nexty = yy+delta[j+1];
                    if(nextx>-1&&nextx<g.length&&nexty>-1&&nexty<g[0].length&&hit[nextx][nexty]==hitTime-1){
                        if(g[nextx][nexty]==1){
                            ++count1;
                            ++hit[nextx][nexty];
                        }else if(g[nextx][nexty]<=0){
                            q.add(nextx);
                            q.add(nexty);
                            ++hit[nextx][nexty];
                            g[nextx][nexty]-=dis;
                            check = true; // check for empty space
                        }
                    }
                }
            }
            ++dis;
        }
        return check?count1:-1;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] g = {{0,2,0,2,2,0,2,2},{0,2,2,2,1,0,1,2},{0,0,0,1,0,2,0,0},
                {2,0,0,2,0,2,2,0},{0,0,0,2,0,0,0,0}};
        System.out.println(s.shortestDistance(g));
    }

}
