/**
* Test_Algorithm.h
* include header files of data structrues for tests
* Created on 2016/10/01
* Author: lwz
**/

#ifndef  TEST_ALGORITHM_H
#define TEST_ALGORITHM_H

#include "../src/share/MyString.h"
#include "../src/share/MyVector.h"
#include "../src/share/LinkedList.h"
#include "../src/share/MyStack.h"
#include "../src/share/MyQueue.h"
#include "../src/share/BinTree.h"
#include "../src/share/BinarySearchTree.h"
#include "../src/share/AVLTree.h"
#include "../src/share/SplayTree.h"
#include "../src/share/Bitmap.h"
#include "../src/share/HashTable.h"
#include "../src/share/DualPivotQuicksort.h"
#include "../src/share/BinaryHeap.h"
#include "../src/share/LeftHeap.h"
#include "../src/share/BTree.h"

void TestString();
void TestVector();
void TestLinkedList();
void TestStack();
void TestQueue();
void TestBinaryTree();
void TestBST();
void TestAVLTree();
void TestSplayTree();
void TestBitmap();
void TestHashTable();
void TestQuickSort();
void TestDPQuicksort();
void TestBinaryHeap();
void TestLeftHeap();
void TestBTree();

//skip n lines for good looking on the console ;-)
inline void SkipLines(int n)
{
    while(n--)
        std::cout<<std::endl;
}

#endif //TEST_ALGORITHM_H