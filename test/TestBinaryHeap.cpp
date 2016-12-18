/**
* TestBinaryHeap.cpp
* Created on 2016-10-24
* Author: lwz
**/

#include "Test_Algorithm.h"

void TestBinaryHeap()
{
    /**
    * simply use binary heap to sort an array could prove its correctness.
    **/
    using namespace std;
    using namespace MyAlgorithm;

    cout<<"======test for binary heap begins======"<<endl;
    SkipLines(1);
    PrepareRandom();
    int int_array[100];
    for(int i=0;i<100;i++)
        int_array[i] = rand();
    BinaryHeap<int> heap(int_array, 100);
    int result[100];
    for(int i=99;i>-1;--i)
        result[i] = heap.DeleteMax();//so the result should be from the largest to the smallest.
    MyVector<int> vector(result,100);//use to check ordered.
    if(vector.Disordered())
        cout<<"incorrect"<<endl;
    else
        cout<<"correct"<<endl;
    SkipLines(1);
    cout<<"======test for binary heap ends======"<<endl;

}
