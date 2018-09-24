//
//  main.cpp
//  PoorPigs_458
//
//  Created by Wenzhe Lu on 2018/3/1.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//
//
//There are 1000 buckets, one and only one of them contains poison, the rest are filled with water.
//They all look the same. If a pig drinks that poison it will die within 15 minutes.
//What is the minimum amount of pigs you need to figure out which bucket contains the poison within one hour.
//
//Answer this question, and write an algorithm for the follow-up general case.
//
//Follow-up:
//
//If there are n buckets and a pig drinking poison will die within m minutes,
//how many pigs (x) you need to figure out the "poison" bucket within p minutes? There is exact one bucket with poison.

#include <cmath>
#include <string>
#include <iostream>

using namespace std;

class Solution {
public:
    // we can analyse this problem in two way
    // great idea really:
    // https://leetcode.com/problems/poor-pigs/discuss/94273/Solution-with-detailed-explanation
    // the first one is to solve this by bit counting
    // we need to use pigs to "locate" the poisonous bucket
    // so we may try to use bits to represents the buckets
    // if we can use bits to represents all the bucket, we must can
    // find the poisonous bucket using given number of pigs.
    // First, find the number of rounds = (minuteTest / minutesDie).floor
    // however, we can leave some buckets untouched and think of them as the last round
    // so rounds should be (minuteTest / minutesDie).floor + 1.
    // now that the number of rounds is the base, how many bits(pigs) do
    // you need to represents all the buckets? (log_rounds(buckets)).ceil
    // you can give an example and draw a picture to imagine this procedure
    int poorPigs2(int buckets, int minutesToDie, int minutesToTest) {
        return ceil(log(buckets) / log(minutesToTest / minutesToDie + 1));
    }
    
    // another way though the beginning part is good, but later harder to imagine:
    // https://leetcode.com/problems/poor-pigs/discuss/94266/Another-explanation-and-solution
    // two pigs 40 test mins 15 die mins, rounds = 3
    // using a matrix of 3x3 to solve this. you can have at most 9 buckets
    // now in a reverse way, given buckets and rounds, at least how many
    // pigs we need? IN the first example we can see that one pig
    // is one dimension, so given the matrix's length, how many dimension
    // we need? The same idea, use log to solve it. Or keep powing to find the enough dimensions.
    int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        int pig = 0;
        while (pow(minutesToTest / minutesToDie + 1, pig) < buckets) {
            pig++;
        }
        return pig;
    }
    
    
};

int main(int argc, const char * argv[]) {

    return 0;
}
