package LetterCombOfPhoneNo_17;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a digit string, return all possible letter combinations that the number
 * could represent.
 */
public class Solution
{
    /* Should consider what will "1" and "0" represent? */
    public static List<String> letterCombinations(String digits)
    {
        // construct arrays.
        List<String> rs = new ArrayList<>();
        if(digits==null||digits.length()==0) return rs;
        char[] arrayZero = {};
        char[] arrayOne = {};
        char[] arrayTwo = {'a','b','c'};
        char[] arrayThree = {'d','e','f'};
        char[] arrayFour = {'g','h','i'};
        char[] arrayFive = {'j','k','l'};
        char[] arraySix = {'m','n','o'};
        char[] arraySeven = {'p','q','r','s'};
        char[] arrayEight = {'t','u','v'};
        char[] arrayNine = {'w','x','y','z'};
        Object[] arrays = new Object[]{arrayZero,arrayOne,arrayTwo
                            ,arrayThree,arrayFour,arrayFive,arraySix
                            ,arraySeven,arrayEight,arrayNine};
        List<StringBuilder> sbs = new LinkedList<>();// perform better when inserting frequently
        sbs.add(new StringBuilder(""));// init
        for(int i=0;i<digits.length();i++)
        {
            int digitnum = digits.charAt(i) - '0';
            char[] mapLetter = (char[])arrays[digitnum];
            int times = mapLetter.length;
            for(int j=0;j<sbs.size();)
            {
                StringBuilder sb = sbs.get(j);
                for(int time=1;time<=times;time++)
                {
                    if(time==times)
                    {
                        sb.append(mapLetter[time-1]);
                    }
                    else
                    {
                        StringBuilder newsb = new StringBuilder(sb);// copy
                        newsb.append(mapLetter[time-1]);
                        sbs.add(j,newsb);
                    }
                }
                j+=times;
            }
        }
        for(StringBuilder sb : sbs)
        {
            rs.add(sb.toString());
        }
        return rs;
    }

    public List<String> letterCombinationsFifo(String digits) {
        LinkedList<String> ans = new LinkedList<String>();
        String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        for(int i =0; i<digits.length();i++){
            int x = Character.getNumericValue(digits.charAt(i));
            while(ans.peek().length()==i){
                String t = ans.remove();
                for(char s : mapping[x].toCharArray())
                    ans.add(t+s);
            }
        }
        return ans;
    }

//    vector<string> letterCombinations(string digits) {
//        vector<string> res;
//        string charmap[10] = {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
//        res.push_back("");
//        for (int i = 0; i < digits.size(); i++)
//        {
//            vector<string> tempres;
//            string chars = charmap[digits[i] - '0'];
//            for (int c = 0; c < chars.size();c++)
//                for (int j = 0; j < res.size();j++)
//                    tempres.push_back(res[j]+chars[c]);
//            res = tempres;
//        }
//        return res;
//    }
}
