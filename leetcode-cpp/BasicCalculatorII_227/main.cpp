// Implement a basic calculator to evaluate a simple expression string.

// The expression string contains only non-negative integers, +, -, *, / operators 
// and empty spaces.
// The integer division should truncate toward zero.

// Example 1:

// Input: "3+2*2"
// Output: 7

// Example 2:

// Input: " 3/2 "
// Output: 1

// Example 3:

// Input: " 3+5 / 2 "
// Output: 5

// Note:

//     You may assume that the given expression is always valid.
//     Do not use the eval built-in library function.

#include <string>
#include <iostream>
#include <stack>

using std::string;
using std::stol;
using std::stack;
using std::cout;
using std::endl;

class Solution {
public:
    // intuitive two pass solution:
    // deal with times and division first
    int calculate(string &s) {
        int total = 0, number = 0, prev_number = 0, prev_op = '+';
        s.append("++"); // Preform last operation & include last number in total
    
        for (auto &i : s) {
            switch (isdigit(i) ? 'N' : isspace(i) ? 'S' : prev_op) {
                case 'N':
                    number = (number * 10) + (i - '0');
                break;
                case 'S':
                break;
                case '+':
                case '-':
                    total += prev_number;
                    prev_number = '+' - prev_op + 1; // 1 or -1 => number or -number
                default:
                    prev_number = prev_op != '/' ? prev_number * number : prev_number / number;
                    number = 0;
                    prev_op = i;
                    cout << prev_number << endl;
                break;
            }
        }
		
        return total;
    }

    // try not use stack
    int calculateII(string& s) {
        size_t pt = 0;
        skipSpaces(s, &pt);
        long pprev = 0, prev = getNumber(s, &pt);
        bool plus = true; // 0 for plus, 1 for minus, 2 for times, 3 for div
        while (1) {
            skipSpaces(s, &pt);
            if (pt == s.size()) break;
            switch (s[pt]) {
                case '+' :
                case '-' :
                    pprev = plus ? pprev + prev : pprev - prev;
                    plus = s[pt++] == '+';
                    skipSpaces(s, &pt);
                    prev = getNumber(s, &pt);
                    break;
                case '*' :
                case '/' :
                    bool times = s[pt++] == '*';
                    skipSpaces(s, &pt);
                    prev = times ? prev * getNumber(s, &pt) : prev / getNumber(s, &pt);
                    break;
            }
        }
        return plus ? pprev + prev : pprev - prev;
    }

    void skipSpaces(const string& s, size_t *ppt) {
        size_t tm = *ppt;
        while (tm < s.size() && s[tm] == ' ') tm++;
        *ppt = tm;
    }

    int getNumber(const string& s, size_t *ppt) {
        size_t tm = *ppt;
        size_t number_begin = tm;
        while (tm < s.size() && s[tm] >= '0' && s[tm] <= '9') tm++;
        *ppt = tm;
        return stoi(s.substr(number_begin, tm - number_begin));
    }
};

int main (int argc, char ** argv) {
    Solution s;
    string str("7+3*2");
    std::cout << s.calculate(str);
}
