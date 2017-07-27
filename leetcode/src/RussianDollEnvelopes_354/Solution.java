package RussianDollEnvelopes_354;

import java.util.Arrays;
import java.util.Comparator;

/**
 * You have a number of envelopes with widths and heights
 * given as a pair of integers (w, h).
 * One envelope can fit into another if and only if both the width and height
 * of one envelope is greater than the width and height of the other envelope.
 *
 * What is the maximum number of envelopes can you Russian doll? (put one inside other)
 *
 * Example:
 * Given envelopes = [[5,4],[6,4],[6,7],[2,3]],
 * the maximum number of envelopes you can Russian doll is 3
 * ([2,3] => [5,4] => [6,7]).
 */
public class Solution {
    // The "stack" solution used in Longest Increasing
    // Subsequence(#300) cannot apply here.
    // so a memo-DFS(or DP) is needed.
    // Program runs really slow when you use comparator.....
    public int maxEnvelopes2(int[][] envelopes) {
        int len = envelopes.length,maxlen = 1;
        if(len==0) return 0;
        int[] dp = new int[len];
        dp[0] = 1;
        Arrays.sort(envelopes,(e1,e2)->(e1[0]-e2[0]));
        for(int i=1;i<len;i++){
            int tempLen = 0;
            for(int j=0;j<i;j++){
                if(envelopes[i][0]>envelopes[j][0]&&envelopes[i][1]>envelopes[j][1]
                        &&dp[j]>tempLen) tempLen = dp[j];
            }
            dp[i] = tempLen+1;
            if(tempLen+1>maxlen) maxlen = tempLen+1;
        }
        return maxlen;
    }

    // Return 1 if env2 fits into env1, return 2 if both not fit
    // each other, return -1 if env1 fits into env2
    private int compareEnv(int[] env1,int[] env2){
        if(env1[0]>env2[0]&&env1[1]>env2[1]) return 1;
        else if(env1[0]<env2[0]&&env1[1]<env2[1]) return -1;
        else return 2;
    }

    // top-down memoization DP
    // O(n^2)
    // TLE....
    public int maxEnvelopes4(int[][] envelopes){
        if(envelopes.length==0) return 0;
        int[] dp = new int[envelopes.length];
        int[][] comp = new int[envelopes.length][envelopes.length];
        for(int i=0;i<envelopes.length;i++) comp[i][i] = 2;
        int maxlen = 1;
        for(int i=0;i<envelopes.length;i++)
            if(dp[i]==0)
                maxlen = Math.max(maxlen,dfs(envelopes,i,comp,dp));
        return maxlen;
    }

    // O(n)
    private int dfs(int[][] env,int cur,int[][] comp,int[] dp){
        int sublen = 0;
        for(int i=0;i<env.length;i++){
            if(comp[cur][i]==0){
                int tm = compareEnv(env[cur],env[i]);
                if(tm==2) comp[cur][i] = comp[i][cur] = 2;
                else{
                    comp[cur][i] = tm;
                    comp[i][cur] = -tm;
                }
            }
            if(comp[cur][i]==1){
                if(dp[i]==0) dfs(env,i,comp,dp);
                if(dp[i]>sublen) sublen = dp[i];
            }
        }
        dp[cur] = sublen+1;
        return sublen+1;
    }

    public static void main(String[] args) {
        int[][] m = new int[][]{
                {3419, 8423}, {164, 2627}, {3139, 7517}, {1751, 4154}, {9489, 86}, {6637, 3814},
                {8056, 4472}, {1361, 7808}, {8951, 7291}, {2729, 3128}, {3981, 8815}, {492, 3230},
                {8077, 8257}, {1046, 8951}, {7601, 3995}, {8077, 2335}, {7913, 6100}, {2120, 552},
                {8052, 7377}, {3699, 4314}, {4956, 9383}, {1398, 9645}, {5612, 3194}, {1041, 7969},
                {9353, 9504}, {3498, 6074}, {3778, 6387}, {6444, 926}, {8188, 3068}, {7573, 9346},
                {1947, 2744}, {3161, 1583}, {6931, 2466}, {3123, 2746}, {3063, 9465}, {5436, 4950},
                {2706, 2321}, {705, 4817}, {6848, 3079}, {7513, 2377}, {3314, 3572}, {1932, 2535},
                {2635, 6677}, {31, 1250}, {3553, 3075}, {8978, 2303}, {6216, 5277}, {6544, 5712},
                {8566, 6145}, {3541, 9898}, {7554, 842}, {1566, 7443}, {3891, 2823}, {8698, 8532},
                {1692, 6859}, {4094, 8342}, {5207, 9744}, {3020, 3461}, {4752, 6631}, {2033, 1323},
                {6094, 3315}, {16, 8209}, {3776, 2627}, {8889, 2818}, {755, 2167}, {9267, 8809},
                {9701, 777}, {9464, 9921}, {2657, 9329}, {8606, 8059}, {6912, 9454}, {133, 6606},
                {7149, 5042}, {7765, 6180}, {5361, 4592}, {7149, 1438}, {847, 9109}, {732, 786},
                {9824, 548}, {9833, 2443}, {9920, 232}, {9957, 8386}, {1792, 5312}, {2950, 3834},
                {3650, 7948}, {900, 5644}, {9741, 7375}, {964, 6898}, {7896, 1036}, {2274, 3628},
                {5552, 3985}, {7669, 8468}, {5923, 2191}, {8154, 1137}, {7929, 9618}, {7960, 4999},
                {3502, 9719}, {516, 2896}, {4059, 2421}, {3576, 1295}, {4835, 20}, {5679, 8186},
                {7977, 1570}, {6570, 8718}, {5669, 7159}, {1610, 6839}, {5384, 9486}, {9839, 1342},
                {5313, 3512}, {7560, 7329}, {3341, 4860}, {7253, 8250}, {4720, 2680}, {935, 6817},
                {5006, 9489}, {3847, 7892}, {8131, 367}, {2769, 8649}, {7110, 6644}, {1890, 5743},
                {1839, 7297}, {3431, 1254}, {5021, 4290}, {2902, 1220}, {4068, 7124}, {7223, 1315},
                {5996, 7738}, {847, 9773}, {4553, 7993}, {3469, 952}, {3515, 7868}, {7258, 8196},
                {9604, 9484}, {6500, 397}, {6891, 2653}, {495, 4697}, {3922, 6202}, {9333, 956},
                {3946, 3785}, {1766, 4052}, {9708, 5744}, {7309, 1019}, {5938, 3607}, {1017, 5011},
                {5151, 6079}, {4784, 6132}, {4469, 8449}, {8021, 8735}, {7943, 2157}, {5530, 880},
                {2771, 3653}};
        Solution s = new Solution();
        System.out.println(s.maxEnvelopes(m));
    }

    // fastest solution
    // it uses radix sort.
    private static int[][] sort(int[][] envelopes, int minValue, int range, int sortBy) {

        int[] count = new int[range + 1];

        for (int[] envelope : envelopes) {
            count[envelope[sortBy] - minValue + 1]++;
        }

        for (int i = 0; i < count.length - 1; i++) {
            count[i + 1] += count[i];
        }

        int[][] sortedEnvelopes = new int[envelopes.length][envelopes[0].length];

        for (int[] envelope : envelopes) {
            sortedEnvelopes[count[envelope[sortBy] - minValue]][0] = envelope[0];
            sortedEnvelopes[count[envelope[sortBy] - minValue]++][1] = envelope[1];
        }

        if(sortBy == 0){
            return sortedEnvelopes;
        }

        int[] tmp;
        int j =  sortedEnvelopes.length - 1;
        for (int i = 0; i < (sortedEnvelopes.length >> 1); i++, j--) {
            tmp = sortedEnvelopes[i];
            sortedEnvelopes[i] = sortedEnvelopes[j];
            sortedEnvelopes[j] = tmp;
        }
        return sortedEnvelopes;
    }

    public static int[][] radixSort(int[][] envelopes) {
        int minW = Integer.MAX_VALUE, maxW = Integer.MIN_VALUE;
        int minH = Integer.MAX_VALUE, maxH = Integer.MIN_VALUE;
        for (int[] envelope : envelopes) {
            minW = minW < envelope[0] ? minW : envelope[0];
            maxW = maxW > envelope[0] ? maxW : envelope[0];
            minH = minH < envelope[1] ? minH : envelope[1];
            maxH = maxH > envelope[1] ? maxH : envelope[1];
        }
        envelopes = sort(envelopes, minH, maxH - minH + 1, 1);
        envelopes = sort(envelopes, minW, maxW - minW + 1, 0);
        return envelopes;
    }

    public int maxEnvelopes3(int[][] envelopes) {
        if (envelopes == null || envelopes.length < 1){
            return 0;
        }

        envelopes = radixSort(envelopes);

        int length = 0;
        int[] dp = new int[envelopes.length];
        for (int[] envelope : envelopes) {
            int index = Arrays.binarySearch(dp, 0, length, envelope[1]);
            if (index < 0){
                index = -(index + 1);
            }
            dp[index] = envelope[1];
            if (index == length){
                length++;
            }
        }
        return length;
    }

    // thought:
    // sort the envelops first according to their width.
    // tricky! When width the same, sort height in descend order.
    // then find the longest increasing subsequence(#300)
    // Additionally, using lambda is slower on leetcode OJ..(don't know why..)
    public int maxEnvelopes(int[][] envelopes){
        int len = envelopes.length;
        if(len==0) return 0;
        Arrays.sort(envelopes,(o1, o2) -> {
            if(o1[0]==o2[0]) return o2[1]-o1[1]; // Tricky!
            return o1[0]-o2[0];
        });
        int[] dp = new int[len];
        int p = 1;
        dp[0] = envelopes[0][1];
        for(int i=1;i<len;i++){
            int val = envelopes[i][1],lo = 0,hi = p;
            // then do binary search
            while(lo<hi){
                int mid = (lo+hi)>>>1;
                if(val>dp[mid]) lo = mid+1;
                else hi = mid;
            }
            dp[lo] = val;
            if(lo==p) ++p;
        }
        return p;
    }
}
