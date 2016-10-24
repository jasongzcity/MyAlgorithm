/**
* MyStack.h 
* Defines a stack container.
* Last modified on: 2016/10/08
* Author:lwz
**/
#ifndef MYSTACK_H
#define MYSTACK_H

#include "./MyAlgorithm.h"
#include "./MyVector.h"

ALGORITHM_BEGIN

/**
* The underlying structure of a stack is just like vector.
* So it can simply inherit vector and add some unique methods.
**/
template<typename T>
class MyStack : protected MyVector<T>
{
public:
    //==constructors==//
    MyStack(int c = VEC_DEFAULT_CAPACITY,int s = 0,T e = 0)
        :MyVector(c,s,e)
    {}

    MyStack(MyStack<T> const& S,Rank lo,Rank hi)
    {
        CopyFrom(S.DataBuff,lo,hi);
    }

    MyStack(T const* A,Rank lo,Rank hi)
    {
        CopyFrom(A,lo,hi);
    }

    virtual ~MyStack(){}

    bool IsEmpty()
    {
        return Size==0;
    }

    /**
    * Push element e to the stack
    * Return the current size of the stack
    **/
    int Push(T const& e)
    {
        Insert(e,Size);
        return Size;
    }

    /**
    * Return the element at stack's top.
    * Return NULL if empty stack.
    **/
    T Top()
    {
        if(Size==0)
            return NULL;
        return DataBuff[Size-1];
    }

    /**
    * Pop out the element at stack's top.
    * Return NULL if empty stack.
    **/
    T Pop()
    {
        if(Size==0)
            return NULL;
        return Remove(Size-1);
    }
};

ALGORITHM_END

#endif //MYSTACK_H