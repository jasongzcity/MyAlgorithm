/**
* TestBitmap.cpp
* Created on 2016-10-13 
* Author:lwz
**/

#include "Test_Algorithm.h"

void TestBitmap()
{
    using namespace std;
    using namespace MyAlgorithm;
    cout<<"=====tests for bitmap begins====="<<endl;
    SkipLines(3);
    cout<<"set 10 11 3 then clear 10"<<endl;
    Bitmap bitmap;
    
    bitmap.set(10);
    bitmap.set(11);
    bitmap.set(3);
    bitmap.clear(10);

    SkipLines(1);
    cout<<"test 11:"<<endl;
    if(bitmap.test(11))
        cout<<"true"<<endl;
    else
        cout<<"false"<<endl;
    SkipLines(1);
    cout<<"test 10:"<<endl;
    if(bitmap.test(10))
        cout<<"true"<<endl;
    else
        cout<<"false"<<endl;
    SkipLines(1);

    cout<<"tostring: "<<endl;
    bitmap.printbits(11);

    SkipLines(1);
    cout<<"clear all then test 3"<<endl;
    bitmap.clear();
    if(bitmap.test(3))
        cout<<"true"<<endl;
    else
        cout<<"false"<<endl;
    SkipLines(3);
    cout<<"=====tests for bitmap ends======"<<endl;
}