/*
 * VectorTest.cpp
 *
 * Created on: 2016/9/20
 * Author: lwz
 */
#include "share/MyVector.h"
#include <iostream>

int main() {
    using namespace std;
    using namespace MyAlgorithm;
    MyVector<int> vector;
    vector.Insert(3);
    vector.Insert(4);
    vector.Insert(89);
    vector.Insert(18481);
    vector.Insert(789);
    vector.Insert(22);
    vector.Insert(2);
    vector.Insert(3);
    vector.Insert(0);

    cout<<"the size of the vector: "<<vector.Num()<<'\r'<<endl;
    vector.MergeSort(0,vector.Num());
    cout<<"Binary Search 1: "<<vector.BinSearch(1)<<'\r'<<endl;
    cout<<"Binary Search 3: "<<vector.BinSearch(3)<<'\r'<<endl;
    vector.Uniquify();
    int i=0;
    while(i<vector.Num()) {
        cout<<vector[i]<<endl;
        i++;
    }
}
