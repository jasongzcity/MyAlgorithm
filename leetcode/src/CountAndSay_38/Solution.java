package CountAndSay_38;

/**
 * The count-and-say sequence is the sequence of integers beginning as follows:
 * 1, 11, 21, 1211, 111221, ...
 *
 * 1 is read off as "one 1" or 11.
 * 11 is read off as "two 1s" or 21.
 * 21 is read off as "one 2, then one 1" or 1211.
 *
 * Given an integer n, generate the nth sequence.
 *
 * Note: The sequence of integers will be represented as a string.
 */
public class Solution
{
    public static String countAndSay(int n){
        //String prev,current = "1";
        StringBuilder current = new StringBuilder("1"),prev = new StringBuilder(" ");
        int count = 1;
        while(count<n){
            ++count;
            prev.delete(0,prev.length());
            prev.append(current.subSequence(0,current.length()));
            current.delete(0,current.length());
            char currentChar = prev.charAt(0);
            int currentCount = 0;
            for (int i = 0; i < prev.length(); i++) {
                if(prev.charAt(i)!=currentChar){
                    current.append(currentCount).append(currentChar);
                    currentChar = prev.charAt(i);
                    currentCount = 1;
                }
                else currentCount++;
            }
            current.append(currentCount).append(currentChar);
        }
        return current.toString();
    }

    public static void main(String[] args) {
        System.out.println(countAndSay(1));
        System.out.println(countAndSay(2));
        System.out.println(countAndSay(5));
    }
}
