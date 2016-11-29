#include "StdAfx.h"

void TestString()
{
    using namespace MyAlgorithm;
    using std::cout;
    using std::endl;
    cout<<"========string tests begin========="<<endl;
    MyString mystr;
    mystr.StrAssign("ababbabccababbababab");
    MyString sub;
    mystr.SubString(sub,3,4);
    cout<<"the substring: ";
    sub.DispString();
    cout<<endl;
    sub.DispKmpArray();
    cout<<"the main string : ";
    mystr.DispString();
    cout<<"sub's position 2: "<<sub.CharAt(2)<<endl;
    cout<<"match tests: "<<endl;
    cout<<"brute force: "<<endl;
    cout<<"begin-0: "<<mystr.PositionOf(sub,0)<<"\nbegin 5: "<<mystr.PositionOf(sub,5)<<endl;
    cout<<endl;
    cout<<"kmp:"<<endl;
    cout<<"begin-0: "<<mystr.PositionOf_Kmp(sub,0)<<"\nbegin 5: "<<mystr.PositionOf_Kmp(sub,5)<<endl;
    cout<<"the substring kmp: "<<endl;
    sub.DispKmpArray();
    cout<<endl;
    cout<<"======change methods======"<<endl;
    mystr+=mystr;
    cout<<"the main string : ";
    mystr.DispString();
    sub.StrAssign("aba");
    cout<<"the substring: ";
    sub.DispString();
    cout<<"IsEmpty: "<<sub.IsEmpty()<<" main string length: "<<mystr.Length()<<endl;
    cout<<endl;
    cout<<"============string tests end============"<<endl;
}