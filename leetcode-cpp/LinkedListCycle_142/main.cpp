//
//  main.cpp
//  LinkedListCycle_142
//
//  Created by Wenzhe Lu on 2018/2/28.
//  Copyright © 2018年 Wenzhe Lu. All rights reserved.
//

//Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
//
//Note: Do not modify the linked list.
//
//Follow up:
//Can you solve it without using extra space?

#include "../include/ListNode.hpp"

#include <iostream>

class Solution {
public:
    ListNode *detectCycle(ListNode *head) {
        if (head == nullptr) return nullptr;
        ListNode *slow = head, *fast = head;
        do {
            slow = slow->next;
            fast = fast->next;
            if (fast != nullptr) fast = fast->next;
            if (fast == nullptr) return nullptr;
        } while (slow != fast);
        // now we know there is cycle in the list
        slow = head;
        while (slow != fast) {
            slow = slow->next;
            fast = fast->next;
        }
        return slow;
    }
};

int main(int argc, const char * argv[]) {
    ListNode l(12);
    using namespace std;
    cout << (l.next == NULL) << endl;
    cout << (l.next == nullptr) << endl;
    return 0;
}
