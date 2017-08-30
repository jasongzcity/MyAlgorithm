package JudgeRouteCircle_657;

import java.util.HashSet;
import java.util.Set;

/**
 * Initially, there is a Robot at position (0, 0).
 * Given a sequence of its moves, judge if this robot makes a circle,
 * which means it moves back to the original place.
 *
 * The move sequence is represented by a string.
 * And each move is represent by a character.
 * The valid robot moves are R (Right), L (Left), U (Up) and D (down).
 * The output should be true or false representing whether the robot makes a circle.
 *
 * Example 1:
 * Input: "UD"
 * Output: true
 *
 * Example 2:
 * Input: "LL"
 * Output: false
 */
public class Solution {
    // naive solution using hashset
    // Unaccepted, the problem says the ORIGINAL PLACE
    public boolean judgeCircle2(String moves) {
        Set<Point> set = new HashSet<>(moves.length());
        set.add(new Point(0,0));
        int x = 0,y = 0;
        for(char c:moves.toCharArray()){
            if(c=='L') --x;
            else if(c=='R') ++x;
            else if(c=='U') ++y;
            else --y;
            if(!set.add(new Point(x,y))) return true;
        }
        return false;
    }

    class Point{
        int x;
        int y;

        public Point(int x,int y){ this.x = x; this.y = y; }

        @Override
        public int hashCode(){
            return x+y;
        }

        @Override
        public boolean equals(Object p) {
            return p instanceof Point && ((Point) p).x == x && ((Point) p).y == y;
        }
    }

    public boolean judgeCircle3(String moves) {
        int x = 0,y = 0;
        for(char c:moves.toCharArray()){
            if(c=='L') --x;
            else if(c=='R') ++x;
            else if(c=='U') ++y;
            else --y;
        }
        return x==0&&y==0;
    }

    public boolean judgeCircle(String moves){
        int[] set = new int[128];
        for(char c:moves.toCharArray()) set[c]++;
        return set['D']==set['U']&&set['L']==set['R'];
    }
}
