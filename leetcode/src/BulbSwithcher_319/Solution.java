package BulbSwithcher_319;

import java.util.Arrays;

/**
 * There are n bulbs that are initially off. You first turn on all the bulbs.
 * Then, you turn off every second bulb. On the third round,
 * you toggle every third bulb (turning on if it's off or turning off if it's on).
 * For the ith round, you toggle every i bulb. For the nth round,
 * you only toggle the last bulb. Find how many bulbs are on after n rounds.
 *
 * Example:
 *
 * Given n = 3.
 * At first, the three bulbs are [off, off, off].
 * After first round, the three bulbs are [on, on, on].
 * After second round, the three bulbs are [on, off, on].
 * After third round, the three bulbs are [on, off, off].
 *
 * So you should return 1, because there is only one bulb is on.
 */
public class Solution {
    // brute force
    // Without surprise, TLE
    public int bulbSwitch2(int n) {
        boolean[] ba = new boolean[n+1];
        Arrays.fill(ba,true);
        ba[0] = false;
        for(int i=2;i<=n;i++)
            for(int j=i;j<=n;j++)
                if(j%i==0)
                    ba[j]^=true; // toggle
        int count = 0;
        for(boolean b:ba)
            if(b)
                ++count;
        return count;
    }

    public int bulbSwitch(int n){
        return (int)Math.sqrt(n);
    }
}
