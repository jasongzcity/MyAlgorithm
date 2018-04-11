package BinarySubstring;

// from contest
public class Solution {

    public int substring(String input) {
        if (input.length() < 2) return 0;
        int count = 0,end = 1;
        int curcount = 1, nextcount = 0;
        char[] ca = input.toCharArray();
        while (end != ca.length && ca[end] == ca[end - 1]) {
            curcount++;
            end++;
        }

        while (end != ca.length) {
            end++;
            nextcount = 1;
            while (end != ca.length && ca[end] == ca[end - 1]) {
                nextcount++;
                end++;
            }
            count += Math.min(nextcount, curcount);

            curcount = nextcount;
        }



        return count;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.substring("10001"));
    }

}
