/**
* TestSplayTree.cpp
* Created on: 2016/10/12
* Author:lwz
**/

#include "Test_Algorithm.h"

void TestSplayTree()
{
    using namespace std;
    using namespace MyAlgorithm;
    cout<<"=======Tests for splay tree begin======="<<endl;
    SkipLines(3);
    cout<<"Insert 1 5 9 8  98 56 and remove 98, see the inorder traversal"<<endl;
    SplayTree<int> splay;
    splay.Insert(1);
    splay.Insert(5);
    splay.Insert(9);
    splay.Insert(8);
    splay.Insert(98);
    splay.Insert(56);
    splay.Remove(98);
    cout<<"traverse:"<<endl;
    ShowAll<int> show_int;
    splay.Traverse(show_int);
    SkipLines(3);
    cout<<"=======Tests for splay tree end=========="<<endl;
}