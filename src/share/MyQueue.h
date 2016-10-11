/**
* MyQueue.h 
* Defines a simple FIFO queue.
* Last modified on: 2016/10/09
* Author:lwz
**/

#ifndef MYQUEUE_H
#define MYQUEUE_H

#include "./LinkedList.h"

ALGORITHM_BEGIN

/**
* A FIFO Queue can simply use a LinkedList as underlying structure 
* for faster and simpler removal and insertion.
**/
template <typename T>
class MyQueue : protected LinkedList<T>
{
public:
    MyQueue()
    {
        init();//init the sentinel nodes for the linked list
    }

    MyQueue(MyQueue<T> & q)
    {
        init();
        CopyNodes(q.First(),q.Size());
    }

    virtual ~MyQueue(){}

    int Enqueue(T const& e)
    {
        InsertAsLast(e);
        return Size();
    }

    T Dequeue()
    {
        return Remove(First());
    }

    T& Front()
    {
        return First()->data;
    }

    int Size()
    {
        return size;
    }

    bool IsEmpty()
    {
        return size==0;
    }
};

ALGORITHM_END

#endif //MYQUEUE_H