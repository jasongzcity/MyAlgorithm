package WaterAndJugProblem_365;

/**
 * You are given two jugs with capacities x and y litres.
 * There is an infinite amount of water supply available.
 * You need to determine whether it is possible to measure
 * exactly z litres using these two jugs.
 *
 * If z liters of water is measurable,
 * you must have z liters of water contained within one or both buckets by the end.
 *
 * Operations allowed:
 * Fill any of the jugs completely with water.
 * Empty any of the jugs.
 * Pour water from one jug into another till the other jug is
 * completely full or the first jug itself is empty.
 *
 * Example 1: (From the famous "Die Hard" example)
 * Input: x = 3, y = 5, z = 4
 * Output: True
 *
 * Example 2:
 * Input: x = 2, y = 6, z = 5
 * Output: False
 */
public class Solution {
    // I peek the topic of the problem: Math.
    // Guessing: it's like a X*n +/- Y*m problem
    // where z can't larger than X+Y and
    // it's kind of like bulb switcher
    // not accepted....
    public boolean canMeasureWater2(int x, int y, int z) {
        if(z<0) return false;
        if(z==0) return true;
        if(z>x+y) return false;
        if(z==x+y) return true;
        int larger,smaller;
        if(x>y){
            larger = x;
            smaller = y;
        }else{
            larger = y;
            smaller = x;
        }
        if(z==2*larger-smaller||z==larger||z==smaller) return true;
        if(smaller==0) return false;
        for(int i=1,tm;(tm = i*smaller-larger)/smaller<=1;i++)
            if(Math.abs(tm)==z)
                return true;
        return false;
    }

    // optimal solution
    // explanation:
    // https://discuss.leetcode.com/topic/49238/math-solution-java-solution
    public boolean canMeasureWater(int x, int y, int z) {
        //limit brought by the statement that water is finallly in one or both buckets
        if(x + y < z) return false;
        //case x or y is zero
        if( x == z || y == z || x + y == z ) return true;

        //get GCD, then we can use the property of Bézout's identity
        return z%GCD(x, y) == 0;
    }

    public int GCD(int a, int b){
        while(b != 0){
            int temp = b;
            b = a%b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.GCD(96,36));
    }
}
