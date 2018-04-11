package TaskScheduler_621;

import java.util.*;

/**
 * Given a char array representing tasks CPU need to do.
 * It contains capital letters A to Z where different letters
 * represent different tasks.Tasks could be done without original order.
 * Each task could be done in one interval.
 * For each interval, CPU could finish one task or just be idle.
 *
 * However, there is a non-negative cooling interval n that means
 * between two same tasks, there must be at least n intervals that
 * CPU are doing different tasks or just be idle.
 *
 * You need to return the least number of intervals the CPU
 * will take to finish all the given tasks.
 *
 * Example 1:
 * Input: tasks = ['A','A','A','B','B','B'], n = 2
 * Output: 8
 * Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
 *
 * Note:
 * The number of tasks is in the range [1, 10000].
 * The integer n is in the range [0, 100].
 */
public class Solution {

    // second session
    // leetcode most voted solution redo
    public int leastIntervalII(char[] tasks, int n) {
        int[] map = new int[26];
        for(char c:tasks) map[c-'A']++;
        Arrays.sort(map);
        int most = map[25]-1, emptySlots = most*n;
        for(int i=24; i>-1&&map[i]>0; i--){
            emptySlots -= Math.min(most, map[i]);
        }
        return emptySlots>0 ? tasks.length+emptySlots:tasks.length;
    }

    // Intuitive way
    // not accepted but close.
    public int leastInterval2(char[] tasks, int n) {
        if(n==0) return tasks.length;
        int count = 0;
        int[] map = new int[128];
        for(char c:tasks){
            count = Math.max(count,++map[c]);
        }
        return Math.max((count-1)*(n+1)+1,tasks.length);
    }

    // unaccepted..
    // consider [A*6 B - G],n = 5
    public int leastInterval3(char[] tasks, int n){
        int[] posi = new int[128], count = new int[128];
        Arrays.fill(posi,-1000);
        List<Character> l = new ArrayList<>();
        for(char c:tasks) if(count[c]++==0) l.add(c);
        int counting = 0,p = 0;
        while(l.size()!=0){
            Character c = l.get(p);
            if(counting-posi[c]-1>=n){
                if(--count[c]==0)
                    l.remove(p--);
                posi[c] = counting;
                if(++p>=l.size()) p = 0;
            }
            ++counting;
        }
        return counting;
    }

    // a more "brute-force" way
    public int leastInterval4(char[] tasks, int n){
        int[] count = new int[26];
        for(char c:tasks) count[c-'A']++;
        PriorityQueue<Integer> pq = new PriorityQueue<>(26,
                Collections.reverseOrder());
        for(int i:count)
            if(i>0)
                pq.add(i);
        int counting = 0;
        while(!pq.isEmpty()){
            List<Integer> l = new ArrayList<>(); // temp
            for(int i=0;i<=n;i++){
                if(!pq.isEmpty()){
                    Integer ii = pq.poll();
                    if(ii-1>0) l.add(ii-1);
                }
                ++counting;
                if(l.isEmpty()&&pq.isEmpty()) return counting;
            }
            pq.addAll(l);
        }
        return counting;
    }

    // "idle_slots" solution - I could have think of!
    // https://leetcode.com/problems/task-scheduler/solution/
    // use the picture to better understand this.
    public int leastInterval(char[] tasks, int n){
        int[] map = new int[26];
        for(char c:tasks) ++map[c-'A'];
        Arrays.sort(map);
        int appearance = map[25]-1, emptySlot = appearance*n;
        for(int i=24;i>-1&&map[i]>0;i--){
            emptySlot-=Math.min(map[i], appearance);
        }
        return emptySlot>0?emptySlot+tasks.length:tasks.length;
    }


    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.leastInterval(
                new char[]{'A','B','C','D','E','A','B','C','D','E'},4));
    }
}
