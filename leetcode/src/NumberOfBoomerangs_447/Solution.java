package NumberOfBoomerangs_447;

import java.util.HashMap;
import java.util.Map;

/**
 * Given n points in the plane that are all pairwise distinct,
 * a "boomerang" is a tuple of points (i, j, k)
 * such that the distance between i and j equals the distance
 * between i and k (the order of the tuple matters).
 *
 * Find the number of boomerangs.
 * You may assume that n will be at most 500 and
 * coordinates of points are all in the range [-10000, 10000] (inclusive).
 *
 * Example:
 * Input:
 * [[0,0],[1,0],[2,0]]
 * Output:
 * 2
 *
 * Explanation:
 * The two boomerangs are [[1,0],[0,0],[2,0]] and [[1,0],[2,0],[0,0]]
 */
public class Solution {
    public int numberOfBoomerangs(int[][] points) {
        int count = 0;
        Map<Integer,Integer> dis = new HashMap<>();
        for(int i=0;i<points.length;i++){
            for(int j=0;j<points.length;j++){
                if(i==j) continue;
                int sqr = (points[i][0]-points[j][0])*(points[i][0]-points[j][0])+
                        (points[i][1]-points[j][1])*(points[i][1]-points[j][1]);
                dis.put(sqr,dis.getOrDefault(sqr,0)+1);
            }
            for(Integer val:dis.values())
                if(val>1)
                    count+=val*(val-1);
            dis.clear();
        }
        return count;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.numberOfBoomerangs(new int[][]{
                {0,0},
                {0,1},
                {0,2}
        }));
    }
}
