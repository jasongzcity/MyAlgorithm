package TheSkylineProblem_218;

import java.util.*;

public class Solution {
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> rs = new ArrayList<>(), heights = new ArrayList<>(buildings.length << 1);

        // tricky part here
        for (int[] arr : buildings) {
            heights.add(new int[]{arr[0], -arr[2]});    // start of the building
            heights.add(new int[]{arr[1], arr[2]});     // end of the building
        }

        Collections.sort(heights, (arr1, arr2) -> {
            return arr1[0] != arr2[0] ? arr1[0] - arr2[0] : arr1[1] - arr2[1];
        });

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);

        pq.add(0);
        int prev = 0;
        for (int[] arr : heights) {
            if (arr[1] < 0) pq.add(-arr[1]);
            else pq.remove(arr[1]);
            // suppose buildingA with height 8 and buildingB with height 4 both ending at position 6
            // the iteration will first run into buildingB, then the height does not change,
            // then buildingA, the height will change.
            // work around this problem very subtly!
            if (prev != pq.peek()) {
                int tm = pq.peek();
                rs.add(new int[]{arr[0], tm});
                prev = tm;
            }
        }

        return rs;
    }

    // divide and conquer solution
    public List<int[]> getSkyline2(int[][] buildings) {
        return merge(buildings, 0, buildings.length-1);
    }

    private LinkedList<int[]> merge(int[][] buildings, int lo, int hi) {
        LinkedList<int[]> res = new LinkedList<>();
        if(lo > hi) {
            return res;
        } else if(lo == hi) {
            res.add(new int[]{buildings[lo][0], buildings[lo][2]});
            res.add(new int[]{buildings[lo][1], 0});
            return res;
        }
        int mid = lo+(hi-lo)/2;
        LinkedList<int[]> left = merge(buildings, lo, mid);
        LinkedList<int[]> right = merge(buildings, mid+1, hi);
        int leftH = 0, rightH = 0;
        while(!left.isEmpty() || !right.isEmpty()) {
            long x1 = left.isEmpty()? Long.MAX_VALUE: left.peekFirst()[0];
            long x2 = right.isEmpty()? Long.MAX_VALUE: right.peekFirst()[0];
            int x = 0;
            if(x1 < x2) {
                int[] temp = left.pollFirst();
                x = temp[0];
                leftH = temp[1];
            } else if(x1 > x2) {
                int[] temp = right.pollFirst();
                x = temp[0];
                rightH = temp[1];
            } else {
                x = left.peekFirst()[0];
                leftH = left.pollFirst()[1];
                rightH = right.pollFirst()[1];
            }
            int h = Math.max(leftH, rightH);
            if(res.isEmpty() || h != res.peekLast()[1]) {
                res.add(new int[]{x, h});
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        s.getSkyline2(new int[][]{
                new int[]{2, 9, 10},
                new int[]{3, 7, 15}
        });
    }
}
