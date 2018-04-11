package BrickWall_554;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * There is a brick wall in front of you. The wall is rectangular and has several rows of bricks.
 * The bricks have the same height but different width. You want to draw a vertical line
 * from the top to the bottom and cross the least bricks.
 *
 * The brick wall is represented by a list of rows.
 * Each row is a list of integers representing the width of each brick in this row from left to right.
 *
 * If your line go through the edge of a brick, then the brick is not considered as crossed.
 * You need to find out how to draw the line to cross the least bricks and return the number of crossed bricks.
 *
 * You cannot draw a line just along one of the two vertical edges of the wall,
 * in which case the line will obviously cross no bricks.
 *
 * Example:
 * Input:
 * [[1,2,2,1],
 * [3,1,2],
 * [1,3,2],
 * [2,4],
 * [3,1,2],
 * [1,3,1,1]]
 * Output: 2
 *
 * Note:
 * The width sum of bricks in different rows are the same and won't exceed INT_MAX.
 * The number of bricks in each row is in range [1,10,000].
 * The height of wall is in range [1,10,000]. Total number of bricks of the wall won't exceed 20,000.
 */
public class Solution {
    // intuitive thought:
    // the line must lies on the "gaps" between bricks
    // if not the line must crossed the number of rows.
    // there are at most n-1 gaps in one row(n is the width of the row)
    // imagine 1,1,1,1,1 (four gaps)
    // memory limit exceeded..
    public int leastBricks2(List<List<Integer>> wall) {
        if(wall.size()==0) return 0;
        int width = 0;
        for(int i:wall.get(0)) width += i;
        // so there are gaps 1, 2, ..., width - 1
        // now we calculate the bricks across the gap, then return the gap with
        // least crossing brick
        // map it using array.
        int[] map = new int[width];
        for(List<Integer> l:wall){
            int tm = 0;
            for(Integer b:l){
                for(int p=tm+1;p<tm+b;p++){
                    map[p]++;
                }
                tm+=b;
            }
        }
        int rs = Integer.MAX_VALUE;
        for(int i=1;i<width;i++)
            if(map[i]<rs)
                rs = map[i];
        return rs;
    }

    // the opposite way:
    // record down how many times every gap been reached.
    // the most "reached" gap is the answer. And the answer is
    // rows - most reached times
    public int leastBricks(List<List<Integer>> wall){
        if(wall.size()==0) return 0;
        int width = 0, maxTouch = 0;
        for(int i:wall.get(0)) width += i;
        Map<Integer, Integer> map = new HashMap<>();
        for(List<Integer> l:wall){
            int p = 0;
            for(int i:l){
                if((p+=i) != width){
                    Integer gap = map.get(p);
                    if(gap==null) gap = 0;
                    map.put(p, ++gap);
                    maxTouch = Math.max(maxTouch, gap);
                }
            }
        }
        return wall.size() - maxTouch;
    }
}
