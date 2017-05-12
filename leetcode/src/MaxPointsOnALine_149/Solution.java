package MaxPointsOnALine_149;

import TwoDPoint.Point;

import java.util.HashMap;
import java.util.Map;

/**
 * Given n points on a 2D plane,
 * find the maximum number of points that lie on the same straight line.
 */
public class Solution {
    // inspired by most voted solution on leetcode.
    public static int maxPoints(Point[] points) {
        int max = 0;
        for(int i=0;i<points.length;i++){
            int currentMax = 0,samePoint = 1;
            Point p = points[i];
            Map<Double,Integer> map = new HashMap<>(points.length);
            for(int j=i+1;j<points.length;j++) {
                double slope;
                Point p2 = points[j];
                if(p2.y==p.y&&p2.x==p.x){ ++samePoint;continue; }
                else if(p2.x==p.x) slope = Double.MAX_VALUE;
                else slope = (double)(p2.y-p.y)/(double)(p2.x-p.x); // notice: may have -0.0 in Java
                if(slope==-0.0) slope = 0.0;
                Integer count = map.getOrDefault(slope, 0) + 1;
                if(count>currentMax) currentMax = count;
                map.put(slope,count);
            }
            if(samePoint+currentMax>max) max = samePoint+currentMax;
        }
        return max;
    }

    public static void main(String[] args) {
        Point[] p = new Point[3];
        p[0] = new Point(2,3);
        p[1] = new Point(3,3);
        p[2] = new Point(-5,3);
        System.out.println(maxPoints(p));
    }

    // Accepted solution
    public int maxPoints2(Point[] points) {
        if (points==null) return 0;
        if (points.length<=2) return points.length;

        Map<Integer,Map<Integer,Integer>> map = new HashMap<>();
        int result=0;
        for (int i=0;i<points.length;i++){
            map.clear();
            int overlap=0,max=0;
            for (int j=i+1;j<points.length;j++){
                int x=points[j].x-points[i].x;
                int y=points[j].y-points[i].y;
                if (x==0&&y==0){
                    overlap++;
                    continue;
                }
                int gcd=generateGCD(x,y);
                if (gcd!=0){
                    x/=gcd;
                    y/=gcd;
                }

                if (map.containsKey(x)){
                    if (map.get(x).containsKey(y)){
                        map.get(x).put(y, map.get(x).get(y)+1);
                    }else{
                        map.get(x).put(y, 1);
                    }
                }else{
                    Map<Integer,Integer> m = new HashMap<>();
                    m.put(y, 1);
                    map.put(x, m);
                }
                max=Math.max(max, map.get(x).get(y));
            }
            result=Math.max(result, max+overlap+1);
        }
        return result;


    }
    private int generateGCD(int a,int b){

        if (b==0) return a;
        else return generateGCD(b,a%b);

    }
}
