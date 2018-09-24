//We stack glasses in a pyramid, where the first row has 1 glass,
//the second row has 2 glasses, and so on until the 100th row.  Each glass holds one cup (250ml) of champagne.
//
//Then, some champagne is poured in the first glass at the top.
//When the top most glass is full, any excess liquid poured will fall equally to the glass
//immediately to the left and right of it.
//When those glasses become full, any excess champagne will fall equally to the left and right of those glasses,
//and so on.  (A glass at the bottom row has it's excess champagne fall on the floor.)

//Example 1:
//
//Input: poured = 1, query_glass = 1, query_row = 1
//
//Output: 0.0
//
//Explanation: We poured 1 cup of champange to the top glass of the tower (which is indexed as (0, 0)).
//There will be no excess liquid so all the glasses under the top glass will remain empty.
//
//
//Example 2:
//
//Input: poured = 2, query_glass = 1, query_row = 1
//
//Output: 0.5
//
//Explanation: We poured 2 cups of champange to the top glass of the tower (which is indexed as (0, 0)).
//There is one cup of excess liquid. The glass indexed as (1, 0) and the
//glass indexed as (1, 1) will share the excess liquid equally, and each will get half cup of champange.
//

//poured will be in the range of [0, 10 ^ 9].
//
//query_glass and query_row will be in the range of [0, 99].

#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    // pouring champagne down, filling the current cup and excess champagne down
    // intuitive solution: use a big matrix to record champagne and then
    // do BFS
    
    // accepted
    double champagneTower2(int poured, int query_row, int query_glass) {
        if (query_row == 0) return poured >= 1;
        
        vector<double> a{(double) poured}, b{0, 0};
        vector<double> *cur = &a, *next = &b;
        int count = 0;
        // we can also get rid of the updated variable to make the code
        // simpler
        bool updated = true;
        while (updated && count < query_row) {
            next->clear();
            next->assign(count + 2, 0);
            updated = false;
            for (int i = 0; i <= count; i++) {
                if ((*cur)[i] > 1) {
                    updated = true;
                    (*next)[i] += ((*cur)[i] - 1) / 2;
                    (*next)[i + 1] += ((*cur)[i] - 1) / 2;
                    (*cur)[i] = 1;
                }
            }
            
            std::swap(cur, next);
            ++count;
        }
        if (count < query_row) return 0;
        else return min(cur->at(query_glass), 1.0);
    }
    
    // should use static length matrix to be faster
    // even better solution
    // accepted
    double champagneTower(int poured, int query_row, int query_glass) {
        double dp[100];
        memset(dp, 0, sizeof(double) * 100);
        dp[0] = poured;
        for (int row = 1; row <= query_row; row++) {
            for (int glass = row - 1; glass >= 0; glass--) {
                dp[glass + 1] += dp[glass] = dp[glass] > 1 ? (dp[glass] - 1) / 2 : 0;
            }
        }
        return min(dp[query_glass], 1.0);
    }
};

int main(int argc, const char * argv[]) {
    using std::cout;
    using std::endl;
    Solution s;
    cout << s.champagneTower(2, 1, 1) << endl;
    return 0;
}
