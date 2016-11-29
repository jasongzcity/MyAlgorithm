/**
* TestQueue.cpp
* Test function for MyQueue.h
* Created on 2016/10/09
* Author: lwz
**/
#include "stdafx.h"

void TestQueue()
{
    using namespace std;
    using namespace MyAlgorithm;
    cout<<"==========tests for queue begin=========="<<endl;
    SkipLines(2);
    cout<<"enqueue 3 elements 0 1 2"<<endl;
    MyQueue<int> q;
    q.Enqueue(0);
    q.Enqueue(1);
    q.Enqueue(2);
    cout<<"the size now: "<<q.Size()<<endl;
    SkipLines(1);
    cout<<"And dequeue until empty"<<endl;
    while(!q.IsEmpty())
        cout<<q.Dequeue()<<"  ";
    SkipLines(3);
    cout<<"==========tests for queue end=========="<<endl;
}