//
//  main.cpp
//  SkylineProblem_218
//
//  Created by Wenzhe Lu on 2018/3/18.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

// Must solved!

//A city's skyline is the outer contour of the silhouette formed
//by all the buildings in that city when viewed from a distance.
//Now suppose you are given the locations and height of all the buildings
//as shown on a cityscape photo (Figure A),
//write a program to output the skyline formed by these buildings collectively (Figure B).
//
//Buildings  Skyline Contour
//The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi],
//where Li and Ri are the x coordinates of the left and right edge of the ith building, respectively,
//and Hi is its height. It is guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0.
//You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
//
//For instance, the dimensions of all buildings in
//Figure A are recorded as: [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .
//
//The output is a list of "key points" (red dots in Figure B)
//in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ] that uniquely defines a skyline.
//A key point is the left endpoint of a horizontal line segment.
//Note that the last key point, where the rightmost building ends,
//is merely used to mark the termination of the skyline, and always has zero height.
//Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.
//
//For instance, the skyline in Figure B should be represented
//as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].
//
//Notes:
//
//The number of buildings in any input list is guaranteed to be in the range [0, 10000].
//The input list is already sorted in ascending order by the left x position Li.
//The output list must be sorted by the x position.
//There must be no consecutive horizontal lines of equal height in the output skyline.
//For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable;
//the three lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]

#include <iostream>
#include <vector>
#include <map>
#include <list>

using namespace std;

class Solution {
public:
    // thought: the result will be all the points of the changing height
    // divide and conquer solution
    // good example for list manipulation
    vector<pair<int, int>> getSkyline(vector<vector<int>>& buildings) {
        if (buildings.size() == 0) return vector<pair<int, int>>();
        vector<pair<int, int>> rs;
        list<pair<int, int>> l = divideAndMerge(buildings, 0, buildings.size() - 1);
        for (auto& p : l) {
            rs.emplace_back(std::move(p));
        }
        return rs;
    }
    
    list<pair<int, int>> divideAndMerge(vector<vector<int>>& b, int begin, int end) {
        list<pair<int, int>> rs;
        if (begin == end) {
            rs.emplace_back(b[begin][0], b[begin][2]);
            rs.emplace_back(b[begin][1], 0);
            return rs;
        }
        
        int mid = (begin + end) / 2;
        list<pair<int, int>> left = divideAndMerge(b, begin, mid), right = divideAndMerge(b, mid + 1, end);
        
        // merge left and right
        // the trick of keeping track of the left list and right list height is important!
        int leftH = 0, rightH = 0;  // keep track of current height of left and right list
        while (!left.empty() || !right.empty()) {
            // If use int_max will make the program think they are the same height and cause null pointer
            long leftx = left.empty() ? LONG_MAX : left.front().first;
            long rightx = right.empty() ? LONG_MAX : right.front().first;
            long x;
            if (leftx < rightx) {
                pair<int, int> p = left.front();
                leftH = p.second;
                x = p.first;
                left.pop_front();
            } else if (rightx < leftx) {
                pair<int, int> p = right.front();
                rightH = p.second;
                x = p.first;
                right.pop_front();
            } else {
                // the same in x position;
                rightH = right.front().second;
                leftH = left.front().second;
                x = right.front().first;
                left.pop_front();
                right.pop_front();
            }
            int height = std::max(leftH, rightH);
            if (rs.empty() || rs.back().second != height) {
                rs.emplace_back(x, height);
            }
        }
        
        return rs;
    }
    
    // thought: java solution can be much simpler,
    // because pq in java support removing and duplicate elements
    // also, use some technique to solve overlap boundary
    
    // actually this problem can be solved using simple heap
    // overthinking....
    // most important problem in this problem, how to solve the conflicts
    // of the buildings with the same height?
    // how to remove building on right edge?
    // using pair<height, count> pair in map
    // how can we delete and deal with duplicate height building using pq?
    // also pq does not support finding, so the pair technique is not helpful
    // accepted
    vector<pair<int, int>> getSkyline2(vector<vector<int>>& buildings) {
        vector<pair<int, int>> rs;
        if (buildings.size() == 0) return rs;
        map<int, int, std::greater<int>> m;
        vector<pair<int, pair<int, int>>> copy;
        copy.reserve(buildings.size() << 1);
        for (auto& b : buildings) {
            // 0 for in, 1 for out
            copy.emplace_back(b[0], pair<int, int>(b[2], 0));
            copy.emplace_back(b[1], pair<int, int>(b[2], 1));
        }
        // sort those pairs by their x-axis number
        // also, make adding pairs first!
        // consider [[0, 2, 3], [2, 5, 3]]
        // if not this input will generate redundant [2, 3]
        sort(copy.begin(), copy.end(), [](pair<int, pair<int, int>>& p1, pair<int, pair<int, int>>& p2){
            return p1.first < p2.first || (p1.first == p2.first && p1.second.second < p2.second.second);
        });
        
        m[copy[0].second.first] = 1;
        rs.emplace_back(copy[0].first, copy[0].second.first);
        for (int i = 1; i < copy.size(); i++) {
            int curHeight = rs.back().second;
            auto& p = copy[i];
            auto iter = m.find(p.second.first);
            // notice that no matter adding or deleting,
            // current height maybe equals to the highest building, so we do nothing
            if (iter == m.end()) {
                // adding new height and test is there is a new height
                m[p.second.first] = 1;
                if (m.begin()->first > curHeight) {
                    updateLast(rs, p.first, m.begin()->first);
                }
            } else {
                if (p.second.second == 1) {
                    if (iter->second-- == 1) {
                        // removing height and test if there is a new height
                        m.erase(iter);
                        if (m.begin() == m.end()) {
                            // nothing else in the map, height is 0 now
                            updateLast(rs, p.first, 0);
                        } else if (m.begin()->first < curHeight) {
                            updateLast(rs, p.first, m.begin()->first);
                        }
                    }
                } else {
                    // adding rectangle count
                    iter->second++;
                }
            }
        }
        return rs;
    }
    
    void updateLast(vector<pair<int, int>>& rs, int index, int value) {
        if (rs.back().first == index) rs.back().second = value;
        else rs.emplace_back(index, value);
    }
    
//    vector<int> divideAndMerge(vector<vector<int>>& bd, int begin, int end, vector<pair<int, int>>& rs) {
//        if (begin == end) return bd[begin];
//        int mid = (begin + end) / 2;
//        vector<int> left = divideAndMerge(bd, begin, mid, rs), right = divideAndMerge(bd, mid + 1, end, rs);
//        return merge(left, right, rs);
//    }
//
//    // left and right structure:
//    // [x1, h1, x2, h2, index]
//    vector<int> merge(vector<int>& left, vector<int>& right, vector<pair<int, int>>& rs) {
//        if (left[2] < right[0]) {
//            if (left[4] == INT_MAX) {
//                rs.push_back(pair<int, int>(left[2], 0));
//            }
//        }
//
//        return vector<int>();
//    }
};

int main(int argc, const char * argv[]) {
    Solution s;
    vector<vector<int>> b;
    b.emplace_back(vector<int>{0, 2, 3});
    b.emplace_back(vector<int>{2, 5, 3});
//    b.emplace_back(vector<int>{5, 12, 12});
//    b.emplace_back(vector<int>{15, 20, 10});
//    b.emplace_back(vector<int>{19, 24, 8});
    s.getSkyline2(b);
    return 0;
}
