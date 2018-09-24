//
//  main.cpp
//  TwoSum_1
//
//  Created by Wenzhe Lu on 2018/1/28.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

#include <vector>
#include <unordered_map>
#include <iterator>

using namespace std;

// this solution is created to get a basic idea of the use of map
class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
        
        unordered_map<int, int> map;
        for (int i = 0; ; i++) {
            unordered_map<int, int>::const_iterator it = map.find(target - nums[i]);
            if (it != map.end()) {
                return vector<int> {it -> second, i};
            } else {
                map.insert({nums[i], i});
            }
        }
    }
};

int main(int argc, const char * argv[]) {
    return 0;
}
