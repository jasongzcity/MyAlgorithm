/**
* TestHashTable.cpp
* Created on 2016-10-14 
* Author: lwz
**/

#include "stdafx.h"
#include <iostream>

/**
* Simple hash function for test.
**/
class HashInt
{
public:
    virtual int operator()(const int& e)
    {
        return e;
    }
};

/**
* visit object function.
* should make sure K & V can be accepted by output stream's operator<<
**/
template<typename K,typename V>
class VisitEntry
{
public:
    virtual void operator()(MyAlgorithm::Entry<K,V>& entry)
    {
        std::cout<<"the key: "<<entry.key<<",  the value: "<<entry.value<<",  the hashcode: "<<entry.hashcode<<std::endl;
    }
};


void TestHashTable()
{
    using namespace std;
    using namespace MyAlgorithm;
    HashTable<int,char,HashInt> int_char;
    cout<<"========tests for HashTable begin======="<<endl;
    SkipLines(3);
    cout<<"use a key-int, value-char HashTable"<<endl;
    cout<<"put 1-'a', 3-'c', 5-'e', 6-'f', 26-'z', 24-'x', 23-'w',14-'n', 13-'m' "<<endl;
    int_char.Put(1,'a');
    int_char.Put(3,'c');
    int_char.Put(5,'e');
    int_char.Put(6,'f');
    int_char.Put(26,'z');
    int_char.Put(24,'x');
    int_char.Put(23,'w');
    int_char.Put(14,'n');
    int_char.Put(13,'m');
    cout<<"traverse:"<<endl;
    VisitEntry<int,char> visit;
    int_char.Traverse(visit);
    SkipLines(1);
    cout<<"then remove 6, then check the size and get"<<endl;
    int_char.Remove(6);
    int_char.Traverse(visit);
    SkipLines(1);
    cout<<"size: "<<int_char.Size()<<", get 24: "<<*(int_char.Get(24))<<endl;
    SkipLines(3);
    cout<<"========tests for HashTable end======="<<endl;
}