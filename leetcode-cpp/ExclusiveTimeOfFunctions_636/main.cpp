//
//  main.cpp
//  ExclusiveTimeOfFunctions_636
//
//  Created by Wenzhe Lu on 2018/1/31.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given the running logs of n functions that are executed in a nonpreemptive single threaded CPU,
//find the exclusive time of these functions.
//
//Each function has a unique id, start from 0 to n-1. A function may be called recursively or by another function.
//
//A log is a string has this format : function_id:start_or_end:timestamp.
//For example, "0:start:0" means function 0 starts from the very beginning of time 0.
//"0:end:0" means function 0 ends to the very end of time 0.
//
//Exclusive time of a function is defined as the time spent within this function,
//the time spent by calling other functions should not be considered as this function's exclusive time.
//You should return the exclusive time of each function sorted by their function id.
//
//Example 1:
//Input:
//n = 2
//logs =
//["0:start:0",
// "1:start:2",
// "1:end:5",
// "0:end:6"]
//Output:[3, 4]
//
//Explanation:
//Function 0 starts at time 0, then it executes 2 units of time and reaches the end of time 1.
//Now function 0 calls function 1, function 1 starts at time 2, executes 4 units of time and end at time 5.
//Function 0 is running again at time 6, and also end at the time 6, thus executes 1 unit of time.
//So function 0 totally execute 2 + 1 = 3 units of time, and function 1 totally execute 4 units of time.
//Note:
//Input logs will be sorted by timestamp, NOT log id.
//Your output should be sorted by function id, which means the 0th element of your output
//corresponds to the exclusive time of function 0.
//Two functions won't start or end at the same time.
//Functions could be called recursively, and will always end.
//1 <= n <= 100

#include <vector>
#include <unordered_map>
#include <stack>
#include <string>
#include <iostream>

// this solution is a good example for string
// manipulating in C++
using namespace std;

class Solution {
public:
    // first thought:
    // this question is not that hard, but you have to figure out all the corner cases,
    // and find a suitable data structure to solve it.
    // special case 1: recursive call, even cross-recursion has to be considered.
    // intuitively we can use a stack
    // do some annoying string parsing...
    vector<int> exclusiveTime(int n, vector<string>& logs) {
        vector<int> rs(n, 0);
        stack<pair<int, int>> stk;
        for(string& str : logs) {
            string::size_type f = str.find(':'), s = str.find(':', f + 1);
            string op = str.substr(f + 1, s - f - 1);
            int fid = stoi(str.substr(0, f)), time = stoi(str.substr(s + 1));
            if (op.compare("start") == 0) {
                // now calculate the runnung time for the previous function, but don't pop
                if (!stk.empty()) {
                    rs[stk.top().first] += time - stk.top().second;
                }
                stk.emplace(fid, time);
            } else {
                // it's end, pop out one pair and rewrite the new top pair's time
                rs[stk.top().first] += time - stk.top().second + 1;
                stk.pop();
                if (!stk.empty()) {
                    stk.top().second = time + 1;
                }
            }
        }
        return rs;
    }
    
    void kk(const int& in) {
        cout << "const" << endl;
    }
    void kk(int& in) {
        cout << "reference" << endl;
    }
};

int main(int argc, const char * argv[]) {
    Solution s;
    vector<string> v{"0:start:0","1:start:2","1:end:5","0:end:6"};
    vector<int> vec = s.exclusiveTime(2, v);
//    vec = s.exclusiveTime(2, vector<string> {"0:start:0","1:start:2","1:end:5","0:end:6"});
    int i = 21;
    s.kk(21);  // constants can't be passed into function with non-const reference.
    // imaging a function which may need to forward either rvalue or left forward
    // example here:https://eli.thegreenplace.net/2014/perfect-forwarding-and-universal-references-in-c/#id7
    // especially to notice this part: "Solving perfect forwarding with std::forward"
    // notice this wrapper just forwarding.
    // we need to write four of this kind of thing??
    // no!
    // using forward function and the so-called type collapse rule
    // can solve this problem perfectly.
    s.kk(i);
    return 0;
}
