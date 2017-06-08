package ReadNCharsGivenRead4_157;

/**
 * The API: int read4(char *buf) reads 4 characters at a time from a file.
 *
 * The return value is the actual number of characters read.
 * For example, it returns 3 if there is only 3 characters left in the file.
 *
 * By using the read4 API, implement the function
 * int read(char *buf, int n)
 * that reads n characters from the file.
 *
 * Note:
 * The read function will only be called once for each test case.
 */
public class Solution {
    // dummy method to avoid compile error
    int read4(char[] buf){ return -1; }

    /**
     * @param buf Destination buffer
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */
    public int read(char[] buf, int n) {
        int total = 0;
        boolean eof = false;
        char[] tm = new char[4];
        while(!eof&&total<n){
            int count = read4(tm);
            eof = count<4;
            if(n-total<count) count=n-total; // maximum characters allowed
            for(int i=0;i<count;i++) buf[total++] = tm[i];
        }
        return total;
    }

    private int tmpptr = 0;
    private int tmpcount = 0;
    private char[] tmp = new char[4];

    // call multiple times
    // most voted solution on leetcode
    // use a pointer and a counter maintain temp buff status
    public int read2(char[] buf,int n){
        int total = 0;
        while(total<n){
            if(tmpptr==0) tmpcount = read4(tmp);
            if(tmpcount==0) break;
            while(total<n&&tmpptr<tmpcount) buf[total++] = tmp[tmpptr++];
            if(tmpptr==tmpcount) tmpptr = 0;
        }
        return total;
    }
}
