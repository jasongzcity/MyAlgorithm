package LargestDivisibleSubset_368;

import java.util.*;

/**
 * Given a set of distinct positive integers,
 * find the largest subset such that every pair (Si, Sj) of elements
 * in this subset satisfies: Si % Sj = 0 or Sj % Si = 0.
 *
 * If there are multiple solutions, return any subset is fine.
 *
 * Example 1:
 * nums: [1,2,3]
 * Result: [1,2] (of course, [1,3] will also be ok)
 *
 * Example 2:
 * nums: [1,2,4,8]
 * Result: [1,2,4,8]
 */
public class Solution {

    // DFS with memo
    public List<Integer> largestDivisibleSubset3(int[] a){
        int len = a.length;
        if(len<2){
            if(len==1) return Collections.singletonList(a[0]);
            else return Collections.emptyList();
        }
        Arrays.sort(a);
        int[] memo = new int[len],memo2 = new int[len];
        List<Integer> list = new ArrayList<>(a.length);
        int maxLen = 0,beginIndex = -1;
        for(int i=0;i+maxLen<a.length;i++){
            if(memo[i]==0){
                dfs(a,i,memo,memo2);
                if(memo[i]>maxLen){
                    maxLen = memo[i];
                    beginIndex = i;
                }
            }
        }
        int p = beginIndex;
        while(memo2[p]!=-1){
            list.add(a[p]);
            p = memo2[p];
        }
        list.add(a[p]);
        return list;
    }

    private void dfs(int[] a,int begin,int[] memo,int[] memo2){
        for(int i=begin+1;i<a.length;i++){
            if(a[i]%a[begin]==0){
                if(memo[i]==0) dfs(a,i,memo,memo2);
                if(memo[i]+1>memo[begin]){
                    memo[begin] = memo[i]+1;
                    memo2[begin] = i;
                }
            }
        }
        if(memo[begin]==0){
            memo[begin] = 1;
            memo2[begin] = -1;
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = new int[]{2,3,6,9,12,21,24,27,30,54,108};
        int[] a2 = new int[]{3};
        int[] a3 = new int[]{1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,
                16384,32768,65536,131072,262144,524288,1048576,2097152,
                4194304,8388608,16777216,
                33554432,67108864,134217728,268435456,536870912,1073741824};
//        System.out.println(s.largestDivisibleSubset(a).toString());
        System.out.println(s.largestDivisibleSubset(a2).toString());
//        System.out.println(s.largestDivisibleSubset(a3).toString());
    }

    // It can be better solved with DP.
    public List<Integer> largestDivisibleSubset(int[] a){
        int len = a.length,maxLen = 0,begin = -1;
        if(len==0) return Collections.emptyList();
        Arrays.sort(a);
        int[] steps = new int[len],next = new int[len];
        for(int i=0;i<a.length;i++){
            next[i] = -1;
            for(int j=0;j<i;j++){
                if(a[i]%a[j]==0&&steps[i]<steps[j]+1){
                    steps[i] = steps[j]+1;
                    next[i] = j;
                    if(steps[i]>maxLen){
                        begin = i;
                        maxLen = steps[i];
                    }
                }
            }
        }
        if(begin==-1) return Collections.singletonList(a[0]);
        LinkedList<Integer> rs = new LinkedList<>();
        int p = begin;
        while(p!=-1){
            rs.addFirst(a[p]);
            p = next[p];
        }
        return rs;
    }

    // One super fast solution
    public List<Integer> largestDivisibleSubset2(int[] nums) {
        int n = nums.length, maxIdx = 0;
        List<Integer> ans = new LinkedList<>();
        if (n == 0) return ans;
        Arrays.sort(nums);
        int[] lens = new int[n], prevs = new int[n];
        Arrays.fill(prevs, -1);
        for(int i=0,max=nums[n-1]/2;nums[i]<=max;i++){
            for(int j=i+1,f=2;nums[i]<=nums[n-1]/f;f=(nums[j]+nums[i]-1)/nums[i]){
                int idx = Arrays.binarySearch(nums, j, n, f * nums[i]);
                if (idx > 0 && lens[idx] <= lens[i]) {
                    prevs[idx] = i;
                    lens[idx] = lens[i] + 1;
                    if (lens[idx] > lens[maxIdx]) maxIdx = idx;
                }
                j = idx >= 0 ? idx + 1 : -(idx + 1);
                if (j >= n) break;
            }
        }
        for (int i = maxIdx; i >= 0; i = prevs[i]) ans.add(0, nums[i]);
        return ans;
    }
}
