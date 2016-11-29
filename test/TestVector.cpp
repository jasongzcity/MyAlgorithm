/*
 * TestVector.cpp
 *
 * Created on: 2016/10/07
 * Author: lwz
 */

#include "stdafx.h"

void TestVector()
{
    using namespace std;
    using namespace MyAlgorithm;
    PrepareRandom();
    cout<<"====tests for vector begins===="<<endl;
    SkipLines(3);
    ShowAll<int> show_int;
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
    cout<<"vector begins with: "<<endl;
    vector.Traverse(show_int);
    MyVector<int> vector_insert_sort(vector);
    MyVector<int> vector_select_sort(vector);
    cout<<"the size of the vector: "<<vector.size()<<'\r'<<endl;
    vector.MergeSort(0,vector.size());
    cout<<"result of mergesort: "<<endl;
    vector.Traverse(show_int);
    SkipLines(1);
    cout<<"Binary Search 1: "<<vector.BinSearch(1)<<'\r'<<endl;
    cout<<"Binary Search 3: "<<vector.BinSearch(3)<<'\r'<<endl;
    vector.Uniquify();
    cout<<"after uniquify: "<<endl;
    vector.Traverse(show_int);
    SkipLines(1);

    vector_insert_sort.InsertionSort(0,vector_insert_sort.size());
    cout<<"result of insertion sort: "<<endl;
    vector_insert_sort.Traverse(show_int);
    SkipLines(1);
    vector_select_sort.SelectionSort(0,vector_select_sort.size());
    cout<<"result of selection sort: "<<endl;
    vector_select_sort.Traverse(show_int);

    SkipLines(3);
    
    cout<<"=====expand and shrink test====="<<endl;
    MyVector<int> vec;
    int count = 100;
    while(count--)
        vec.Insert(rand());
    cout<<"After inserting 100 integers: "<<endl;
    cout<<"the size of the vector: "<<vec.size()<<endl;
    cout<<"the capacity of the vector: "<<vec.capacity()<<endl;
    while(count<100)
    {
        count++;
        vec.Remove(0);
    }
    cout<<"After removing 100 integers: "<<endl;
    cout<<"the size of the vector: "<<vec.size()<<endl;
    cout<<"the capacity of the vector: "<<vec.capacity()<<endl;
    SkipLines(3);
    
    cout<<"====tests for vector ends===="<<endl;
}