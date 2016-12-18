/**
* TestLinkedList.cpp
* Test function for linked list.
* Created on 2016/10/08 Happy National Day ;-)))
* Author: lwz
**/

#include "Test_Algorithm.h"

void TestLinkedList()
{
    using namespace std;
    using namespace MyAlgorithm;
    PrepareRandom();
    ShowAll<int> show_int;
    cout<<"===tests for linked list start==="<<endl;
    LinkedList<int> l_origin;
    for(int i=0;i<16;i++)
        l_origin.InsertAsFirst(rand());
    int repeat = rand();
    l_origin.InsertAsFirst(repeat);
    l_origin.InsertAsLast(repeat);
    SkipLines(1);
    cout<<"the origin linked list:"<<endl;
    ListNodePosi(int) pointer = l_origin.First()->pred;//get header sentinel
    for(ListNodePosi(int) p = l_origin.Last();p!=pointer;p=p->pred)
        cout<<p->data<<"  ";
    SkipLines(2);
    cout<<"the size of the linked list: "<<l_origin.Size()<<endl;
    LinkedList<int> l_insert(l_origin);
    LinkedList<int> l_select(l_origin);
    LinkedList<int> back2(l_origin);
    SkipLines(1);
    back2.Deduplicate();
    cout<<"test deduplicate:"<<endl;
    pointer = back2.First()->pred;//get header sentinel
    for(ListNodePosi(int) p = back2.Last();p!=pointer;p=p->pred)
        cout<<p->data<<"  ";
    SkipLines(1);
    cout<<"==sort tests for linked list=="<<endl;
    SkipLines(2);

    l_origin.MergeSort(l_origin.First(),l_origin.Size());
    cout<<"after mergesort:"<<endl;
    l_origin.Traverse(show_int);
    SkipLines(2);

    l_insert.InsertionSort(l_insert.First(),l_insert.Size());
    cout<<"after insertion_sort:"<<endl;
    l_insert.Traverse(show_int);
    SkipLines(2);

    l_select.SelectionSort(l_select.First(),l_select.Size());
    cout<<"after selection_sort:"<<endl;
    l_select.Traverse(show_int);
    SkipLines(2);

    cout<<"====tests for linked list end===="<<endl;
}