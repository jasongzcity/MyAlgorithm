package NQueen_51;

import java.util.ArrayList;
import java.util.List;

/**
 * Classic N-Queen problem
 *
 * Output solutions in a list
 */
public class Solution
{
    public static List<List<String>> solveNQueens(int n)
    {
        List<List<String>> rs = new ArrayList<>();
        List<Queen> queens = new ArrayList<>();
        putQueenInLine(rs,queens,1,n);
        return rs;
    }

    static void putQueenInLine(List<List<String>> rs, List<Queen> queens, int y, int target)
    {
        Queen q = new Queen(1,y);
        while(q.x<=target)
        {
            if(q.isAvailable(queens))
            {
                queens.add(q);
                if(y==target) dumpQueens(rs,queens,target); // get solution!
                else putQueenInLine(rs,queens,y+1,target);
                queens.remove(queens.size()-1);
            }
            q.x++;
        }
    }

    static void dumpQueens(List<List<String>> rs, List<Queen> queens,int target) {
        List<String> strs = new ArrayList<>();
        for(Queen q:queens)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < q.x; i++) sb.append('.');
            sb.append('Q');
            for (int i = q.x+1; i < target+1; i++) sb.append('.');
            strs.add(sb.toString());
        }
        rs.add(strs);
    }

    static class Queen
    {
        int x;
        int y;

        Queen(int x,int y)
        {
            this.x = x;
            this.y = y;
        }

        boolean isAvailable(List<Queen> list)
        {
            boolean rs = true;
            for(Queen q:list) rs &= isAvailable(q);
            return rs;
        }

        boolean isAvailable(Queen q)
        {
            return x!=q.x&&y!=q.y&&q.x-x!=q.y-y&&q.x-x!=-(q.y-y);
        }
    }

    public static void main(String[] args)
    {
        List<List<String>> list = solveNQueens(4);
        System.out.println(list);
//        Queen[] qs = new Queen[10];
//        Queen q = new Queen(0,1);
//        qs[0] = q;
//        q = new Queen(0,10);
//        qs[1] = q;
//        q = testReference(qs,1);
//        q.x++;
//        System.out.println(qs[1].x+" "+qs[1].y);
//
//        Integer[] ints = new Integer[]{0,1,2,3,4,888888888};
//        Integer i = testReference(ints,1);
//        i++;
//        System.out.println(ints[1]);
//        i = testReference(ints,5);
//        i++;
//        System.out.println(ints[5]);
    }


    private static Queen testReference(Queen[] qs,int i)
    {
        return qs[i];
    }

    private static Integer testReference(Integer[] qs,int i)
    {
        return qs[i];
    }
}
