package IntegerToEnglishWord_273;

/**
 * Convert a non-negative integer to its english words representation.
 * Given input is guaranteed to be less than 2^31 - 1.
 *
 * For example,
 * 123 -> "One Hundred Twenty Three"
 * 12345 -> "Twelve Thousand Three Hundred Forty Five"
 * 1234567 -> "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 */
public class Solution {
    public String numberToWords(int num) {
        if(num==0) return "Zero";
        int mask = 1000000000;
        StringBuilder sb = new StringBuilder();
        while(mask>0){
            int thousand = num/mask;
            sb.append(thousandToStr(thousand));
            if(thousand!=0){
                if(mask==1000000000) sb.append("Billion ");
                else if(mask==1000000) sb.append("Million ");
                else if(mask==1000) sb.append("Thousand ");
            }
            num %= mask;
            mask /= 1000;
        }
        sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }

    private String thousandToStr(int num){
        // 0 <= num <= 999
        int mask = 100;
        StringBuilder sb = new StringBuilder();
        while(num>0){
            int digit = num/mask;
            if(digit!=0) {
                if(mask==100||mask==1){
                    sb.append(ltens[digit-1]).append(' ');
                    if(mask==100) sb.append("Hundred").append(' ');
                }else{
                    // ten
                    if(digit>1){
                        sb.append(mtens[digit-2]).append(' ');
                    }else{
                        // digit 1
                        sb.append(tens[num-10]).append(' ');
                        break;
                    }
                }
            }
            // ignore digit = 0
            num %= mask;
            mask /= 10;
        }
        return sb.toString();
    }

    private static String[] tens =
            {"Ten","Eleven", "Twelve","Thirteen","Fourteen","Fifteen","Sixteen",
            "Seventeen","Eighteen","Nineteen"};
    private static String[] mtens =
            {"Twenty", "Thirty","Forty","Fifty","Sixty","Seventy",
                    "Eighty","Ninety"};
    private static String[] ltens = {"One","Two","Three","Four","Five","Six",
                                        "Seven","Eight","Nine"};

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.numberToWords(123456));
    }
}
