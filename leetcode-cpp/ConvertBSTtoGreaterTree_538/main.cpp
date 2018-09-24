//
//  main.cpp
//  ConvertBSTtoGreaterTree_538
//
//  Created by Wenzhe Lu on 2018/3/12.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given a Binary Search Tree (BST), convert it to a Greater Tree such that
//every key of the original BST is changed to the original key plus sum of all keys
//greater than the original key in BST.
//
//Example:
//
//Input: The root of a Binary Search Tree like this:
//     5
//   /   \
//  2     13
//Output: The root of a Greater Tree like this:
//    18
//   /   \
// 20     13

#include <iostream>
#include "../include/TreeNode.hpp"

class Solution {
public:
    TreeNode* convertBST(TreeNode* root) {
        int sum = 0;
        helper(root, sum);
        return root;
    }
    
    void helper(TreeNode* root, int& sum) {
        if (root == nullptr) return;
        helper(root->right, sum);
        int tm = root->val;
        root->val += sum;
        sum += tm;
        helper(root->left, sum);
    }
};

int main(int argc, const char * argv[]) {
    
    return 0;
}
