package KMP;

/**
 * Implement KMP String search algorithm.
 */
public class Kmp
{
    /*
     * I think it will be more straightforward to
     * search table[i] (unlike table[i-1] on Introduction To Algorithm)
     * when the match failed at character i.
     */
    public static int[] getKMPTable(String s)
    {
        int[] rs = new int[s.length()];
        rs[0] = -1; // This means "if you jump back to the first char and still get no luck,just skip it"
        int index = -1,pointer = 0;
        while(pointer<s.length()-1)
        {
            if(index<0||s.charAt(index)==s.charAt(pointer))
            {
                index++;
                pointer++;
                rs[pointer] = index;  // the prefix of pointer matches, so jump to index if not matches.
            }
            else index = rs[index];   // jump back
        }
        return rs;
    }

    public static int kmpMatch(String text,String pattern,int beginIndex)
    {
        int[] kmp = getKMPTable(pattern);
        int pt = beginIndex,pp = 0;
        while(pt<text.length()&&pp<pattern.length())
        {
            if(pp<0||pattern.charAt(pp)==text.charAt(pt))//pp=-1 means "no luck, just skip it"
            {
                pp++;
                pt++;
            }
            else pp = kmp[pp];
        }
        return pp==pattern.length()?pt-pp:-1;
    }

    public static void main(String[] args)
    {
        String text = "adwejjfmsababcabdnuiabababwqnfababbcabddfabababcab";
        String pattern = "abababcab";
        for(int i:getKMPTable(pattern))
            System.out.print(i+" ");
        System.out.println();
        pattern = pattern+'#'+new StringBuilder(pattern).reverse().toString();
        for(int i:getKMPTable(pattern))
            System.out.print(i+" ");
        System.out.println();
        System.out.println("below is new pattern:");
        pattern = "daqqadiwuuurf";
        for(int i:getKMPTable(pattern))
            System.out.print(i+" ");
        System.out.println();
        pattern = pattern+'#'+new StringBuilder(pattern).reverse().toString();
        for(int i:getKMPTable(pattern))
            System.out.print(i+" ");
        //System.out.println(kmpMatch(text,pattern,2));
    }
}
