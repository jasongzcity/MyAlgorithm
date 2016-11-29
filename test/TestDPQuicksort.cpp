/**
* TestDPQuicksort.cpp
* Created on 2016-10-16
* Author: lwz
**/

#include "stdafx.h"


void TestDPQuicksort()
{
    using namespace std;
    using namespace MyAlgorithm;
    ShowAll<int> show_int;
    cout<<"========tests for Dual Pivot Quicksort begin======="<<endl;
    SkipLines(2);
    PrepareRandom();
    
    MyVector<int> vec;
    for(int i=0;i<40;i++)
        vec.Insert(rand());

    vec[0] = -1;
    MyVector<int> vec2(vec);
    MyVector<int> standard(vec);
    standard.QuickSort(0,standard.size()-1,0);
    
    cout<<"test tradition insertion sort in Dual Pivot Quicksort: ";
    vec.DPQuicksort(0,vec.size()-1,true);
    if(!vec.Equals(standard))
        cout<<"Fail!!!"<<endl;
    else
        cout<<"pass"<<endl;
    SkipLines(2);
    cout<<"test pair insertion sort in Dual Pivot Quicksort: ";
    vec2.DPQuicksort(1,vec2.size()-1,false);
    if(!vec2.Equals(standard))
        cout<<"Fail!!!"<<endl;
    else
        cout<<"pass"<<endl;

    MyVector<int> dpvec;
    for(int i=0;i<250;i++)
        dpvec.Insert(rand());
    dpvec[0] = -1;
    MyVector<int> stdvec(dpvec);

    stdvec.QuickSort(0,stdvec.size()-1,3);
    dpvec.DPQuicksort(1,dpvec.size()-1,false);
    SkipLines(2);
    cout<<"test dual pivot quicksort: ";

    if(dpvec.Equals(stdvec))
        cout<<"pass"<<endl;
    else
        cout<<"Fail!!!"<<endl;

    SkipLines(3);

    cout<<"========tests for Dual Pivot Quicksort end======="<<endl;
}