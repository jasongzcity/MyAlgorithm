/**
* TestRBTree.cpp
* test for red black tree
* Created on 2016-12-15 
* Author: lwz
**/

#include "stdafx.h"

void TestRBTree()
{
    using namespace std;
    using namespace MyAlgorithm;
    ShowAll<int> show_int;

    cout<<"=====TEST for Red Black Tree====="<<endl;
    SkipLines(3);
    cout<<"insert 100 random number and later delete 50 of them.Then use inorder traverse to see if it maintains ordered"<<endl;
    PrepareRandom();
    RBTree<int> rbt;
    MyVector<int> vec;
    for(int i=0;i<100;i++)
    {
        int r = rand();
        rbt.Insert(r);

        vec.Insert(r);
    }
    cout<<"after insertion:"<<endl;
    rbt.InTravWithStack(show_int);
    cout<<endl;
    //rbt.LevelTrav_Iter(show_int);
    cout<<"the size of  rbt:"<<rbt.Size()<<endl;
    for(int i=0;i<100;i++)
    {
        if(i%2==0)
            rbt.Remove(vec[i]);
    }
    SkipLines(2);
    cout<<"after deletion, the size of the tree: "<<rbt.Size()<<endl;
    cout<<"show number:"<<endl;
    rbt.InTravWithNoStack(show_int);
    SkipLines(2);
    cout<<"test for red black tree ends"<<endl;
}
