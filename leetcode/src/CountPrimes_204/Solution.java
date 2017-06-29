package CountPrimes_204;

/**
 * Count the number of prime numbers less than a non-negative number, n.
 */
public class Solution {
    // This solution is pretty naive...
    public int countPrimes2(int n) {
        boolean[] notPrimes = new boolean[n];
        int count = 0;
        for(int i=2;i<n;i++){
            if(!notPrimes[i]){
                ++count;
                // if it is prime, set all its multiples not primes
                for(int j=2;j*i<n;j++) notPrimes[i*j] = true;
            }
        }
        return count;
    }

    // beats 99%, optimal solution
    public int countPrimes(int n){
        if(n<3) return 0; // deal with n = 2;
        boolean[] notPrimes = new boolean[n];
        int count = n>>1;
        for(int i=3;i*i<n;i+=2){
            if(!notPrimes[i]){
                for(int j=i*i;j<n;j+=2*i){
                    if(!notPrimes[j]){
                        // j is not prime
                        --count;
                        notPrimes[j] = true;
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.countPrimes(50000));
    }
}
