//
//  TreeNode.hpp
//  leetcode
//
//  Created by Wenzhe Lu on 2018/3/12.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

#ifndef TreeNode_h
#define TreeNode_h

class TreeNode {
public:
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

#endif /* TreeNode_h */
