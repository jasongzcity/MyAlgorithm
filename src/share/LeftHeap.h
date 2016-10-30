/**
* LeftHeap.h
* Last modified on: 2016/10/25
* Author:lwz
**/

#ifndef LEFTHEAP_H
#define LEFTHEAP_H

#include "./PQInterface.h"
#include "./BinTree.h"
#include "./MyAlgorithm.h"
#include "./MyStack.h"

ALGORITHM_BEGIN

/**
* Left heap use null path length(Npl) to maintain balance
* Npl(NULL)  = 0;
* Npl(x) = 1+ min(Npl(LChild(x),Npl(RChild(x))
* Left heap should maintain :npl(x) = 1+npl(RChild(x)) and npl(LChild(x)>=npl(RChild(x))
* Compare with binary heap, left heap is better at the situation that two heaps need to merge,
* in binary heap, merging means you need to give up the the sorted sequence in one of two heaps.
**/
template <typename T>
class LeftHeap:public PQ<T>,protected BinTree<T>
{
protected:
    /**
    * Merge left heap a with b.
    * Return top of the heap.
    **/
    BinNodePosi(T) merge(BinNodePosi(T) a,BinNodePosi(T) b)
    {
        if(!a)
            return b;
        if(!b)
            return a;
        if(a->Data<b->Data)//make sure a points to the larger node
            swap(a,b);
        a->RChild = merge(a->RChild,b);//recursive call
        a->RChild->Parent = a;
        if(!a->LChild || a->LChild->Npl<a->RChild->Npl) //make sure after merge the left have larger Npl
            swap(a->RChild,a->LChild);
        a->Npl = a->RChild?a->RChild->Npl+1:1;//since right child must has smaller npl, current node = rchild+1 
        return a;
    }

    /**
    * Merge left heap a and b.
    * Iteration version.
    * Return top of the heap
    **/
    BinNodePosi(T) merge_Iter(BinNodePosi(T) a,BinNodePosi(T) b)
    {
        MyStack<BinNodePosi(T)> stack;
        while(a && b)//use stack to record the the merge sequence
        {                                      
            if(a->Data<b->Data)                 
                swap(a,b);
            stack.Push(a);
            a = a->RChild;
        }
                   
        while(!stack.IsEmpty())
        {
            a = stack.Top();
            a->RChild = b;
            b->Parent = a;
            if(!a->LChild || a->LChild->Npl<a->RChild->Npl) 
                swap(a->RChild,a->LChild);
            a->Npl = a->RChild?a->RChild->Npl+1:1;
            b = stack.Pop();//now a==b
        }
        return a?a:b;//return the top
    }

public:
    LeftHeap(){}

    /**
    * Time complexity: O(nlogn)
    **/
    LeftHeap(T* A,int size)
    {
        for(int i=0;i<size;i++)
            Insert(A[i]);
    }

    /**
    * Time complexity: O(nlogn)
    * Simply merge one node into this heap.
    **/
    virtual void Insert(const T& e)
    {
        BinNode<T>* node = new BinNode<T>(e);
        _root = merge(_root,node);
        _root->Parent = NULL;
        _size++;
    }

    void Insert_Iter(const T& e)
    {
        BinNode<T>* node = new BinNode<T>(e);
        _root = merge_Iter(_root,node);
        _root->Parent = NULL;
        _size++;
    }

    virtual int Size() const
    {
        return _size;
    }

    /**
    * Time complexity: O(logn)
    * merge the top node's right & left child.
    **/
    virtual T DeleteMax()
    {
        BinNodePosi(T) lc = _root->LChild;
        BinNodePosi(T) rc = _root->RChild;
        T data = _root->Data;
        delete _root;
        _size--;
        _root = merge(lc,rc);
        if(_root)
            _root->Parent = NULL;
        return data;
    }

    virtual T GetMax()
    {
        return _root->Data;
    }
};

ALGORITHM_END

#endif //LEFTHEAP_H