//
//  main.cpp
//  CountOfSimilarNumberAfterSelf_315
//
//  Created by Wenzhe Lu on 2018/3/15.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//You are given an integer array nums and you have to return a new counts array.
//The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].
//
//Example:
//
//Given nums = [5, 2, 6, 1]
//
//To the right of 5 there are 2 smaller elements (2 and 1).
//To the right of 2 there is only 1 smaller element (1).
//To the right of 6 there is 1 smaller element (1).
//To the right of 1 there is 0 smaller element.
//Return the array [2, 1, 1, 0].

#include <iostream>
#include <vector>

using namespace std;

// This question is actually similar with
// 300 - longest increasing subsequence(binary search solution)
class Solution {
public:
    // intuitive O(n^2)
    // but it would be trivial
    // given the hint "merge sort"
    // combine with a structure to track
    // the number's index, we can solve this problem
    // accepted
    class numIndex {
    public:
        int number;
        int index;
        
        numIndex(int num, int ind) { number = num; index = ind; }
        // using default operator=
    };
    
    vector<int> countSmaller2(vector<int>& nums) {
        vector<int> counts(nums.size(), 0);
        vector<numIndex> numInd(nums.size(), numIndex(0, 0));
        for (int i = 0; i < nums.size(); i++) {
            numInd[i].index = i;
            numInd[i].number = nums[i];
        }
        msort(counts, numInd, 0, nums.size());
        return counts;
    }
    
    // end exclusive
    void msort(vector<int>& count, vector<numIndex>& numInd, int begin, int end) {
        if (end <= begin + 1) return;
        int mid = (begin + end) / 2;
        msort(count, numInd, begin, mid);
        msort(count, numInd, mid, end);
        merging(count, numInd, begin, mid, end);
    }
    
    // notice: should do merging in-place on numInd
    void merging(vector<int>& count, vector<numIndex>& numInd, int begin, int mid, int end) {
        vector<numIndex> copy(numInd.begin() + mid, numInd.begin() + end);
        int pa = mid - 1, pb = end - mid - 1, pc = end - 1;
        while (pb >= 0) {
            if (pa < begin || copy[pb].number >= numInd[pa].number) {
                numInd[pc--] = copy[pb--];
            } else {
                count[numInd[pa].index] += pb + 1;
                numInd[pc--] = numInd[pa--];
            }
        }
    }
    
    // given hint "BST", can think of a solution
    // first build a BST using the input, and each node contains element's index
    // so every time an element go to some node's left tree,
    // we add 1 to the index in the result.
    // Note that in this case you may have worst case O(n^2)
    // this is the most straight forward solution
    class Node {
    public:
        int index;
        int number;
        int sum;    // total number of nodes on the left hand side
        
        Node *left;
        Node *right;
        
        Node(int ind, int num, int sum) : index(ind), number(num), left(nullptr), right(nullptr), sum(sum) {}
        
        ~Node() {
            delete left;
            delete right;
        }
    };
    
public:
    vector<int> countSmaller3(vector<int>& nums) {
        if (nums.size() == 0) return vector<int>();
        Node root(nums.size() - 1, nums[nums.size() - 1], 1);
        Node *prev, *cur = &root;
        vector<int> rs(nums.size(), 0);
        for (int i = nums.size() - 2; i >= 0; i--) {
            int sum = 0;
            prev = cur = &root;
            while (cur != nullptr) {
                prev = cur;
                if (nums[i] <= cur->number) {
                    cur->sum++;
                    cur = cur->left;
                } else {
                    sum += cur->sum;
                    cur = cur->right;
                }
            }
            if (nums[i] > prev->number) prev->right = new Node(i, nums[i], 1);
            else prev->left = new Node(i, nums[i], 1);
            rs[i] = sum;
        }
        
        return rs;
    }
    
    // we could also do insertion sort on nums
    // but that would require O(n^2) time
    
    // binary indexed tree
    // this structure initially for use of
    // "range sum querying"
    // but in this situation, we only use tree to "set"
    // or say "represent" thoses bits
    // for example, in other cases, we use 8 to sum 1 to 8.
    // but now we use 8 to count the present times from 1 to 8
    // accepted!
    vector<int> countSmaller(vector<int>& nums) {
        if (nums.size() == 0) return vector<int>();
        int min = numeric_limits<int>::max(), max = numeric_limits<int>::min();
        for (int& i : nums) min = std::min(min, i);
        
        // note that in extreme test cases this could exceed INT_MAX, even UINT_MAX(INT_MAX - INT_MIN + 1)
        for (int& i : nums) {
            i += -min + 1;
            max = std::max(max, i);
        }
        
        int tree[max + 1];
        memset(tree, 0, sizeof(int) * (max + 1));
        
        vector<int> rs(nums.size(), 0);
        for (int i = nums.size() - 1; i > -1; i--) {
            rs[i] = get(nums[i], tree);
            update(nums[i], tree, max);
        }
        
        return rs;
    }
    
    // the crux of binary indexed tree :
    // use bits to represent charges
    // example: 11100(28)
    // the least bit(100) in charge from 11001(25) to 11100(28)
    // the second least bit (1000) in charge from 10001 (17) to 11000 (24)
    // the highest bit in charge from 1 to 16
    
    // Also, given one number, which index will be in charge of it?
    // first, of course, itself.
    // Also, all the zeroes in the number.
    // so that we can ensure it will be in charge in all directions --
    // 10001(17) - 10010 (18) - 10100(20) - 11000(24) - 100000 - ... we can go on if possible
    // this ensure that no matter you use either sum 21(10101) or sum 25(11001), 17 will be covered
    int get(int value, const int tree[]) {
        int sum = 0;
        while (value != 0) {
            sum += tree[value];
            value -= (value & -value);  // delete last set bit
        }
        
        return sum;
    }
    
    void update(int value, int tree[], int max) {
        while (value <= max) {
            tree[value]++;
            value += (value & -value);
        }
    }
};

int main(int argc, const char * argv[]) {
    Solution s;
    vector<int> v{5, 2, 6, 1};
    s.countSmaller(v);
//    cout << s.countSmaller(v) << endl;
    return 0;
}
