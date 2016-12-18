/**
* TestLeftHeap.cpp
* Created on 2016-10-25 
* Author: lwz
**/

#include "Test_Algorithm.h"

void TestLeftHeap()
{
    using namespace std;
    using namespace MyAlgorithm;
    PrepareRandom();
    cout<<"======test for left heap begins===="<<endl;
    SkipLines(3);
    LeftHeap<int> heap1;
    LeftHeap<int> heap2;

    int result[100];
    int result2[100];
    for(int i=0;i<100;i++)
    {
        heap1.Insert(rand());
        heap2.Insert_Iter(rand());
    }
    for(int i=99;i>-1;i--)
    {
        result[i] = heap1.DeleteMax();
        result2[i] = heap2.DeleteMax();
    }
    
    MyVector<int> vec(result,100);
    MyVector<int> vec2(result2,100);
    cout<<"correctness for merge recursion version: ";
    if(vec.Disordered() || vec2.Disordered())
        cout<<"incorrect"<<endl;
    else
        cout<<"correct"<<endl;
    
    SkipLines(3);
    cout<<"======test for left heap ends===="<<endl;
}