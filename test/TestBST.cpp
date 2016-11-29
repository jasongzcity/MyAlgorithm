/**
* TestBST.cpp
* Last modified on: 2016/10/12
* Author:lwz
**/

#include "stdafx.h"

void TestBST()
{
    using namespace std;
    using namespace MyAlgorithm;
    ShowAll<int> show_int;
    cout<<"========tests for binary search tree begin======="<<endl;
    SkipLines(3);
    BinarySearchTree<int> bst;
    cout<<"test: insert some integer and use inorder traversal to see if sorted."<<endl;
    cout<<"insert: 5 8 2 99 88 199 66 56 57 58 55 4 2 3"<<endl;
    
    bst.Insert(5);
    bst.Insert(8);
    bst.Insert(2);
    bst.Insert(99);
    bst.Insert(88);
    bst.Insert(199);
    bst.Insert(66);
    bst.Insert(56);
    bst.Insert(57);
    bst.Insert(58);
    bst.Insert(55);
    bst.Insert(4);
    bst.Insert(2);
    bst.Insert(3);

    SkipLines(1);
    cout<<"Traverse: "<<endl;
    bst.Traverse(show_int);
    
    SkipLines(1);
    cout<<"Remove 99 56"<<endl;
    bst.Remove(99);
    bst.Remove(56);
    cout<<"Traverse: "<<endl;
    bst.Traverse(show_int);

    bst.Size();
    bst.Root();
    //bst.TestSum_Iter();

    SkipLines(3);
    cout<<"========tests for binary search tree end======="<<endl;
}