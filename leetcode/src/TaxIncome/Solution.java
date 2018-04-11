package TaxIncome;

import java.util.List;

// from facebook fulltime interview
// given some one's income and tax level, calculate his total paying tax
// the level would be like : [[100000, 0.2], [20000, 0.3], [INT_MAX, 0.4]]
// the last level means that you will need to pay exceed income using this rate
public class Solution {
    public int count(int income, List<List<Integer>> levels) {
        int pay = 0, i = 0;
        while (true) {
            if (income <= levels.get(i).get(0)) {
                // not exceed this level, can break
                pay += income * levels.get(i).get(1);
                break;
            } else {
                // exceed, you will have to pay full tax
                pay += levels.get(i).get(0) * levels.get(i).get(1);
                income -= levels.get(i).get(0);
            }
            i++;
        }
        return pay;
    }

    public int calculate(int income, List<List<Integer>> levels) {
        int pay = 0, level = 0;
        while (true) {
            List<Integer> cur = levels.get(level);
            if (cur.get(0) == null || income <= cur.get(0)) {
                // the last level or income smaller than threshold, can break
                pay += income * cur.get(1);
                break;
            } else {
                pay += cur.get(0) * cur.get(1);
                level++;
                income -= cur.get(0);
            }
        }
        return pay;
    }
}
