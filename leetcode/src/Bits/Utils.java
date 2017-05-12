package Bits;

/**
 * Created by Administrator on 2017/5/8.
 */
public class Utils {
    public static String toBits(int i){
        StringBuilder sb = new StringBuilder();
        while(i!=0){
            sb.append(i&1);
            i>>>=1;
        }
        return sb.reverse().toString();
    }
}
