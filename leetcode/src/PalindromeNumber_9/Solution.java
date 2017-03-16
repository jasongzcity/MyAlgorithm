package PalindromeNumber_9;

/**
 * Some hints:

 Could negative integers be palindromes? (ie, -1)

 If you are thinking of converting the integer to string,
 note the restriction of using extra space.

 You could also try reversing an integer. However,
 if you have solved the problem "Reverse Integer",
 you know that the reversed integer might overflow.
 How would you handle such case?

 There is a more generic way of solving this problem.

 */
public class Solution
{
    /* "makeup" a half of the input integer */
    public boolean isPalindrome(int x)
    {
        if(x<0||(x!=0&&x%10==0)) return false;  // the latter circumstances is to deal
                                        // with situations that x=100,1000,10000
                                        // in those situations, reverse/10=0=x
        int reverse = 0;
        while(x>reverse)  // get half of the reverse value
        {
            reverse = reverse*10+x%10;
            x /= 10;
        }
        return (x==reverse)||(reverse/10==x);
    }
}
