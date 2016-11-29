/**
* TestQuickSort.cpp
* Created on 2016-10-15 
* Author: lwz
**/

#include "stdafx.h"
#include <stdlib.h>

void TestQuickSort()
{
    using namespace MyAlgorithm;
    using namespace std;
    PrepareRandom();
    MyVector<int> vector1;
    MyVector<int> vector2;
    MyVector<int> vector3;
    MyVector<int> vector4;
    for(int i=0;i<100;i++)
    {
        vector1.Insert(rand());
        vector2.Insert(rand());
        vector3.Insert(rand());
        vector4.Insert(rand());
    }
    cout<<"=======test for quicksort begins======="<<endl;
    SkipLines(3);
    cout<<"insert 100 random integers in the vector, and use 4 kinds of partitions of quicksort, then check if the vector is ordered"<<endl;
    vector1.QuickSort(0,vector1.size(),0);
    vector2.QuickSort(0,vector2.size(),1);
    vector3.QuickSort(0,vector3.size(),2);
    vector4.QuickSort(0,vector4.size(),3);
    SkipLines(3);
    cout<<"Partition1: "<<vector1.Disordered()<<" Partition2: "<<vector2.Disordered()<<" Partition3: "<<\
        vector3.Disordered()<<" Partition4: "<<vector4.Disordered()<<endl;
    SkipLines(3);

    cout<<"use 4 kinds of partitions of quicksort to deal with degenerate situation: 100 equal elements in the array."<<endl;
    SkipLines(2);
    vector1.Remove(0,vector1.size()-1);
    vector2.Remove(0,vector2.size()-1);
    vector3.Remove(0,vector3.size()-1);
    vector4.Remove(0,vector4.size()-1);
    int temp1 = rand();
    int temp2 = rand();
    int temp3 = rand();
    int temp4 = rand();
    for(int i=0;i<100;i++)
    {
        vector1.Insert(temp1);
        vector2.Insert(temp2);
        vector3.Insert(temp3);
        vector4.Insert(temp4);
    }
    vector1.QuickSort(0,vector1.size(),0);
    vector2.QuickSort(0,vector2.size(),1);
    vector3.QuickSort(0,vector3.size(),2);
    vector4.QuickSort(0,vector4.size(),3);
    cout<<"Partition1: "<<vector1.Disordered()<<" Partition2: "<<vector2.Disordered()<<" Partition3: "<<\
        vector3.Disordered()<<" Partition4: "<<vector4.Disordered()<<endl;
    SkipLines(3);
    cout<<"=======test for quicksort ends======="<<endl;
}
