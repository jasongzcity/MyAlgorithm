package RomanToInteger_13;

import java.util.HashMap;
import java.util.Map;

public class Solution
{
    public static int romanToInt(String s)
    {
        Map<Character,Integer> map = new HashMap<>();
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);
        map.put('L',50);
        map.put('C',100);
        map.put('D',500);
        map.put('M',1000);
        int sum = 0;
        int i=0;
        for(;i<s.length()-1;i++)
        {
            Character c = s.charAt(i);
            Character n = s.charAt(i+1);
            int current = map.get(c).intValue();
            int next = map.get(n).intValue();
            if(current<next)
                sum -= current;
            else
                sum += current;
        }
        sum += map.get(s.charAt(i)).intValue();
        return sum;
    }
}
