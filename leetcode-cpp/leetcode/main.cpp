//
//  main.cpp
//  leetcode
//
//  Created by Wenzhe Lu on 2018/1/25.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

#include <iostream>
#include <vector>

// playground
// try rvalue
using namespace std;

class Solution {
public:
    int *arr;
    
public:
    Solution() {
        arr = new int[10];
        for (int j = 0; j < 10; j++) {
            arr[j] = j;
        }
        cout << "default" << endl;
    }
    
    Solution(Solution&& rvalue) {
        arr = rvalue.arr;
        cout << "calling rvalue" << endl;
        rvalue.arr = NULL;
    }
    
    Solution(Solution& left) {
        arr = new int[10];
        int j = 0;
        for (; j < 10; j++) {
            arr[j] = left.arr[j];
        }
        cout << "calling lvalue" << endl;
    }
    
    Solution(int& lv) {
        cout << "calling lvalue" << endl;
        arr = new int[10];
        for (int j = 0; j < 10; j++) {
            arr[j] = j;
        }
    }
    
    Solution(int&& rv) {
        cout << "calling rvalue" << endl;
        arr = new int[10];
        for (int j = 0; j < 10; j++) {
            arr[j] = j;
        }
    }
    
    ~Solution() {
        delete arr;
    }
};

// move function does this:
//template <class T>
//typename remove_reference<T>::type&&
//move(T&& a)
//{
//    return a;
//}

// conclusion:
// something about perfect forwarding and type deduction in C++11
// https://eli.thegreenplace.net/2014/perfect-forwarding-and-universal-references-in-c/

int main(int argc, const char * argv[]) {
//    Solution s(10);// this is calling rvalue
//    Solution ss(move(s)); // this call rvalue
//    Solution sss(*(new Solution)); // this is still calling left value
    // to get a rvalue you have to explicitly call move
    // or get from a function return
//    vector<int> a;
//    vector<int> v(vector<int>);
//    for (int i = 0; i < 10; i++) {
//
//    }
//    Solution sss(s);
//    v.insert(5);
//    for (int i = 0; i < 10; i++) {
//        cout << ss.arr[i] << " ";
//    }
    
    // a common scene: adding a new Solution to the vector, see
    // how many constructors are called and how they are called
    cout << endl;
    vector<Solution> v;
    v.push_back(Solution()); // so it's calling a default and then using rvalue call!
    // that's exactly we want! Suppose the arr in the Solution is some expensive resource(thus allocating is slow)
    // this will greatly improve the performance
    // even more, using "emplace" will be even faster(only one instance created!)
    return 0;
}
