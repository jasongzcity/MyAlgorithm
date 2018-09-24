//
//  main.cpp
//  FindTheCelebrity_277
//
//  Created by Wenzhe Lu on 2018/3/1.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

#include <iostream>
#include <vector>

using namespace std;

//Suppose you are at a party with n people (labeled from 0 to n - 1) and among them,
//there may exist one celebrity. The definition of a celebrity is
//that all the other n - 1 people know him/her but he/she does not know any of them.
//
//Now you want to find out who the celebrity is or verify that there is not one.
//The only thing you are allowed to do is to ask questions
//like: "Hi, A. Do you know B?" to get information of whether A knows B.
//You need to find out the celebrity (or verify there is not one)
//by asking as few questions as possible (in the asymptotic sense).
//
//You are given a helper function bool knows(a, b) which tells you whether A knows B.
//Implement a function int findCelebrity(n), your function should minimize the number of calls to knows.
//
//Note: There will be exactly one celebrity if he/she is in the party.
//Return the celebrity's label if there is a celebrity in the party. If there is no celebrity, return -1.

// dummy
bool knows(int a, int b) {
    return false;
}

class Solution {
public:
    // intuitive:
    // you will need O(n^2) time to solve this
    // asking everyone about knowing all others
    // should have better solution
    
    // types of people:
    // 1. normal people - know some and some know him
    // below are some types of people in some way seems like celebrities
    // 2. geek - know none but somebody may know him(not all)(confuse with celeb because he knows nobody)
    // 3. celebrity - know none and all know him
    // 4. party animal - all know him and he know somebody(confuse with celeb because all know him)
    
    // once you know somebody, you are no longer celeb.
    // once somebody don't know you, you are no longer celeb
    // you have to keep track of how many people know somebody,
    // or you may confuse celeb with geek...
    
    // BUT:
    // you can't have both geek and celeb in the party.
    // because all know celeb and geek know none
    
    // this use the same idea of "find majority"
    // VERY SMARTLY using one pass to find potential candidates
    // and use another pass to eliminate the possibilty of geek(not all know him)
    int findCelebrity(int n) {
        int candidate = 0;
        // the smartest part is: through this one pass
        // there can be only one candidate left, which is candidate
        for (int pt = 1; pt < n; pt++) {
            if (knows(candidate, pt))
                candidate = pt;
        }
        
        // now check this candidate again
        for (int i = 0; i < n; i++) {
            if (i != candidate && (knows(candidate, i) || !knows(i, candidate))) {
                return -1;
            }
        }
        return candidate;
    }
};

int main(int argc, const char * argv[]) {
    
    return 0;
}
