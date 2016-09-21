/*
 * VectorTest.cpp
 *
 *  Created on: 2016年9月20日
 *      Author: jason
 */

#include "includes/MyVector.h"
#include <iostream>

int main() {
    using namespace std;
    SVector<int> vector;
    vector.Insert(3);
    vector.Insert(4);
    vector.Insert(5);
    vector.Insert(6);
    vector.Insert(7);
    vector.Insert(1);
    vector.Insert(2);
    vector.Insert(1);
    vector.Insert(0);

    cout<<vector.Num()<<endl;
    cout<<endl;
    vector.MergeSort(0,vector.Num());
    cout<<vector.BinSearch(1)<<endl;
    int i=0;
    while(i<vector.Num()) {
        cout<<vector[i]<<endl;
        i++;
    }
}
