package BinaryWatch_401;

import java.util.ArrayList;
import java.util.List;

/**
 * A binary watch has 4 LEDs on the top which represent the hours (0-11),
 * and the 6 LEDs on the bottom represent the minutes (0-59).
 *
 * Each LED represents a zero or one, with the least significant bit on the right.
 *
 * For example, the above binary watch reads "3:25".
 *
 * Given a non-negative integer n which represents the
 * number of LEDs that are currently on,
 * return all possible times the watch could represent.
 *
 * Example:
 * Input: n = 1
 * Return: ["1:00", "2:00", "4:00", "8:00", "0:01",
 *          "0:02", "0:04", "0:08", "0:16", "0:32"]
 * Note:
 * The order of output does not matter.
 * The hour must not contain a leading zero,
 * for example "01:00" is not valid, it should be "1:00".
 * The minute must be consist of two digits and may contain a leading zero,
 * for example "10:2" is not valid, it should be "10:02".
 */
public class Solution {
    public List<String> readBinaryWatch(int n) {
        List<String> rs = new ArrayList<>();
        for(int i=0;i<=n;i++){
            if(i>=4||n-i>=6) continue;
            for(int j=0;j<hours[i].length;j++){
                for(int k=0;k<mins[n-i].length;k++){
                    rs.add(hours[i][j]+mins[n-i][k]);
                }
            }
        }
        return rs;
    }

    private String[][] hours = {
            {"0:"},
            {"1:","2:","4:","8:"},
            {"3:","5:","9:","6:","10:"},
            {"11:","7:"},
    };

    private String[][] mins = {
            {"00"},
            {"01","02","04","08","16","32"},
            {"03","05","09","17","33","06","10","18","34","12","20","36","24","40","48"},
            {"07","11","19","35","13","21","37","25","41","49","14","22","38",
                    "26","42","50","28","44","52","56"},
            {"15","23","39","27","43","51","29","45","53","57","30","46","54","58"},
            {"31","47","55","59"},
    };

    // better solution
    public List<String> readBinaryWatch2(int num) {
        List<String> res=new ArrayList<>();
        // 720 minutes total
        for(int i=0;i<60*12;i++){
            int h=i/60;
            h=h%12;
            int m=i%60;
            String addLeadingZero = "";
            if(m<10) addLeadingZero = "0";
            if(num==(Integer.bitCount(h)+ Integer.bitCount(m)))
                res.add(h+":"+addLeadingZero+m);
        }
        return res;
    }
}
