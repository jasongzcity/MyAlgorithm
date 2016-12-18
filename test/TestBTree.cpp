/**
* TestBTree.cpp
* Created on 2016-10-29 
* Author: lwz
**/

#include "Test_Algorithm.h"

class TravBTree
{
public:
    int count;
    bool sorted;
    int temp;
    TravBTree()
    {
        count = 0;
        sorted = true;
        temp = 0;
    }

    void operator()(const int& e)
    {
        count++;
        if(temp > e)
            sorted = false;
        temp = e;
    }
};

void TestBTree()
{
    using namespace std;
    using namespace MyAlgorithm;
    ShowAll<int> show_int;
    TravBTree t;
    PrepareRandom();
    cout<<"=====test for B-Tree begins====="<<endl;
    SkipLines(3);
    BTree<int> btree(4);
    int i = 0;
    int temp = 0;
    int del = 0;
    while(i<240)
    {
        temp = rand();
        if(i%6==0)
            del = temp;
        btree.Insert(temp);
        if(i%6==4)
            btree.Remove(del);
        ++i;
    }
    cout<<"inorder traversal: "<<endl;
    btree.InorderTraverse(show_int);
    SkipLines(2);
    cout<<"the size of this btree: "<<btree.size()<<endl;
    btree.InorderTraverse(t);
    cout<<"the real size of this btree: "<<t.count<<endl;

    if(t.sorted)
        cout<<"sorted!"<<endl;
    else
        cout<<"unsorted!"<<endl;

    SkipLines(3);
    cout<<"=====test for B-Tree ends====="<<endl;    
}
