package SongList;

import java.util.Scanner;

public class Solution {

    final static int MOD = 1000000007;

    long pick(int n, int m) {
        long tm = 1;
        for (int i = 0; i < m; i++) tm *= n - i;
        for (int i = 1; i <= m; i++) tm /= i;
        return tm;
    }

    int songList_2(int la, int x, int lb, int y, int N) {
        int numa = N / la, rs = 0;
        for (int i = numa; i >= 0; i--) {
            if ((N - i * la) % lb == 0) {
                int numb = (N - i * la) / lb;
                long tm = pick(x, i) * pick(y, numb);
                rs = (int) ((tm + rs) % MOD);
            }
        }

        return rs;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int la = 0, x = 0, lb = 0, y = 0, N = 0;
        Scanner reader = new Scanner(System.in);
        String str = reader.nextLine();
        N = Integer.parseInt(str);
        str = reader.nextLine();
        String[] strs = str.split(" ");
        la = Integer.parseInt(strs[0]);
        x = Integer.parseInt(strs[1]);
        lb = Integer.parseInt(strs[2]);
        y = Integer.parseInt(strs[3]);


        System.out.println(s.songList_2(la, x, lb, y, N));
//        System.out.println(s.flipArray(8, 2));
    }

//    int flipArray(int n, int m) {
//        int groupSum = m * m;
//        return n / (2 * m) * groupSum;
//    }
}
