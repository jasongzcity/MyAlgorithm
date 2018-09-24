//
//  ListNode.hpp
//  leetcode
//
//  Created by Wenzhe Lu on 2018/2/28.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

#ifndef ListNode_h
#define ListNode_h

class ListNode {
public:
    int val;
    ListNode *next;
    
    ListNode(int x = 0) : val(x), next(nullptr) {}
};


#endif /* ListNode_h */
