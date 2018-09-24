//
//  main.cpp
//  QueueReconstruByType_406
//
//  Created by Wenzhe Lu on 2018/3/14.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Suppose you have a random list of people standing in a queue.
//Each person is described by a pair of integers (h, k),
//where h is the height of the person and k is the number of people in front of
//this person who have a height greater than or equal to h. Write an algorithm to reconstruct the queue.
//
//Note:
//The number of people is less than 1,100.
//
//
//Example
//
//Input:
//[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
//
//Output:
//[[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]

#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    // hint: greedy
    // O(n^2)
    // not accpeted...
    // its a very subtle greedy problem...
    vector<pair<int, int>> reconstructQueue2(vector<pair<int, int>>& people) {
        sort(people.begin(), people.end(), [](pair<int, int>& p1, pair<int, int>& p2){
            return p1.first < p2.first;
        });     // sort the pairs using its first element

        vector<pair<int, int>> rs;
        rs.reserve(people.size());
        for (size_t i = people.size() ; i > 0; i--) {
            int count = 0, pt = 0;
            pair<int, int>& p = people[i - 1];
            while (pt < rs.size() && count < p.second) {
                if (rs[pt].first >= p.first)
                    count++;
                pt++;
            }
            rs.insert(rs.begin() + pt, p);
        }
        
        return rs;
    }
    
    // redo
    // still O(n^2)
    vector<pair<int, int>> reconstructQueue(vector<pair<int, int>>& people) {
        sort(people.begin(), people.end(), [](pair<int, int>& p1, pair<int, int>& p2){
            return p1.first > p2.first || (p1.first == p2.first && p1.second < p2.second);
        });
        // sort them in order that we insert the people with taller height
        // so that we can decided each people's position by their k, (because we have already put all
        // people taller than them into the vector.)
        // Note that with the same height, we should put people with smaller k, because smaller k
        // should be before larger k, and when we are putting smaller k
        // In this way, the later inserted people would not affect the people before
        vector<pair<int, int>> rs;
        rs.reserve(people.size());
        for (auto& p : people) {
            rs.insert(rs.begin() + p.second, p);
        }
        
        return rs;
    }
};

int main(int argc, const char * argv[]) {
    vector<pair<int, int>> people({pair<int, int>(7, 0), pair<int, int>(4, 4), pair<int, int>(7, 1), pair<int, int>(5, 0), pair<int, int>(6, 1), pair<int, int>(5, 2)});
    Solution s;
    s.reconstructQueue(people);
    
    return 0;
}
