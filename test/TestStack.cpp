/**
* TestStack.cpp
* Test function for MyStack.h
* Created on 2016/10/08
* Author: lwz
**/
#include "stdafx.h"

void TestStack()
{
    using namespace std;
    using namespace MyAlgorithm;
    cout<<"==========tests for stack begin=========="<<endl;
    MyStack<int> stack;
    stack.Push(0);
    stack.Push(1);
    stack.Push(2);
    SkipLines(2);
    cout<<"Pushed 0 1 2 to the stack, pop the stack 3 times"<<endl;
    int n = 3;
    while(n--)
        cout<<stack.Pop()<<endl;
    cout<<"Check if empty:"<<endl;
    if(stack.IsEmpty())
        cout<<"Empty"<<endl;
    else
        cout<<"Not Empty"<<endl;
    SkipLines(3);
    cout<<"==========tests for stack end=========="<<endl;
}
