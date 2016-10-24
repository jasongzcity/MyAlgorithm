/**
* BinaryHeap.h 
* Last modified on: 2016/10/24
* Author:lwz
**/

#ifndef BINARYHEAP_H
#define BINARYHEAP_H

#include "./PQInterface.h"
#include "./MyStack.h"

ALGORITHM_BEGIN

/////////////////////////Shortcut////////////////////////
//rank functions
#define InHeap(i,n)     (((i)>(-1)) && ((i)<n)) //Judge if rank in the heap size of n.
#define Parent(i)           (((i)-1)>>1)                 //return the parent rank
#define LChild(i)           (((i)<<1)+1)               // left child's rank
#define RChild(i)           (LChild(i)+1)             //right child's rank
#define RChildValid(i,n)    InHeap(RChild(i),n)  //judge if i's right child valid
#define LChildValid(i,n)    InHeap(LChild(i),n)   //judge if i's left child valid
#define ParentValid(i)      ((i)>0)                      //judge if parent valid ( is i the top of the heap)
#define LastInternal(n)     (Parent(n-1))            //last internal node with heap's size n
//need to overload operator < for the target element type T, return the rank with the larger priority
#define Prior(buff,i,j)     ((buff[i])<(buff[j])?j:i) 
////////////////////////ShortcutEnd//////////////////////

/**
* Binary Heap. 
* Maintain the largest(logical) element at the heap's top.
* Binary heap provides a structure whose insertion & deletion time complexity of O(logn)
* and initialization time complexity of O(n) (According to Introduction To Algorithm).
**/
template <typename T>
class BinaryHeap : public PQ<T>  //implement the primary queue abstract class
{
private:
    int _size;
    MyVector<T> Buff;//use vector for underlying structure, no need to worry about expand and shrink
protected:

    /**
    * Find the largest element in i and i's children
    **/
    Rank ProperParent(Rank i)
    {
        Rank temp = i;
        if(LChildValid(i,_size)) 
        {
            temp = (Prior(Buff,i,LChild(i)) == i)?i:LChild(i);
            if(RChildValid(i,_size))
                temp = (Prior(Buff,temp,RChild(i)) == temp)?temp:RChild(i);
        }
        return temp;
    }
    
    /**
    * Percolate element up.
    * Return the final rank.
    **/
    Rank PercolateUp(Rank i)
    {
        T temp = Buff[i];                    
        while(i)//compare with parent
        {
            if(temp>Buff[Parent(i)])
            {
                Buff[i] = Buff[Parent(i)];//put parent down
                i = Parent(i);
            }
            else
                break;
    }
        Buff[i] = temp;//put the element in position
        return i;
    }

    /**
    * Percolate rank i's element down.
    * Return the final rank.
    **/
    Rank PercolateDown(Rank i)
    {
        T temp = Buff[i];  
        //check largest element repeatedly
        while( ( RChildValid(i,_size) && temp<Buff[RChild(i)] ) || ( LChildValid(i,_size) && temp<Buff[LChild(i)] ) )
        {
            if(RChildValid(i,_size) && (Prior(Buff,LChild(i),RChild(i)) == RChild(i)))
            {
                Buff[i] = Buff[RChild(i)];
                i = RChild(i);
            }
            else
            {
                Buff[i] = Buff[LChild(i)];
                i = LChild(i);
            }
        }
        Buff[i] = temp;
        return i;
    }

    /** 
    * Build heap slow vertion.
    * Call PercolateDown repeatedly from the the lastInternal.
    * Time complexity: O(n)
    **/
    void BuildHeap()
    {
        for(Rank i=LastInternal(_size);i>-1;i--)
            PercolateDown(i);
    }


public:
    BinaryHeap(){}

    BinaryHeap(const T* Array,int size)
    {
        _size = size;
        Buff = MyVector<T>(Array,size);
        BuildHeap();
    }

    BinaryHeap(const MyVector<T>& vec)
    {
        _size = vec.Num();
        Buff = MyVector<T>(vec);
        BuildHeap();
    }
    virtual ~BinaryHeap(){}

    /**
    * Insert element e.
    * Time complexity: O(logn)
    **/
    virtual void Insert(const T& e)
    {
        Buff.Insert(e);
        _size++;
        PercolateUp(_size-1);
    }

    virtual int Size() const 
    {
        return _size;
    }

    /**
    * Delete the max element.
    * Swap to the end and then percolate down.
    * Time complexity: O(logn)
    **/
    virtual T DeleteMax()
    {
        T t = Buff[0];
        Buff[0] = Buff[--_size];
        Buff.Remove(_size);
        PercolateDown(0);
        return t;
    }

    virtual T GetMax()
    {
        return Buff[0];
    }
};

ALGORITHM_END

#endif //BINARYHEAP_H