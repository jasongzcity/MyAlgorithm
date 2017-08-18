package ReverseBits_190;

import javax.sound.midi.Soundbank;

/**
 * Reverse bits of a given 32 bits unsigned integer.
 *
 * For example, given input 43261596 (represented in binary as
 * 00000010100101000001111010011100),
 * return 964176192 (represented in binary as 00111001011110000010100101000000).
 *
 * Follow up:
 * If this function is called many times, how would you optimize it?
 *
 * Related problem: Reverse Integer
 */
public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int rs = 0;
        for(int i=0;i<32;i++){
            rs = (rs<<1)+(n&1);
            n>>>=1;
        }
        return rs;
    }

    // bit trick
    // O(1)
    public int reverseBits2(int n){
        n = ((n&0xaaaaaaaa)>>>1)|((n&0x55555555)<<1); // reverse every one bit
        n = ((n&0xcccccccc)>>>2)|((n&0x33333333)<<2); // reverse every two bit
        n = ((n&0xf0f0f0f0)>>>4)|((n&0x0f0f0f0f)<<4); // reverse every 4 bit
        n = ((n&0xff00ff00)>>>8)|((n&0x00ff00ff)<<8); // reverse every 8 bit
        n = ((n&0xffff0000)>>>16)|((n&0x0000ffff)<<16); // reverse every 16 bit
        return n;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.reverseBits(1));
    }
}
