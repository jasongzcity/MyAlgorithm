/**
* MyVector.h 
* Defines a vector container
* Last modified on: 2016/10/08
* Author:lwz
**/
#ifndef MYVECTOR_H
#define MYVECTOR_H

#include "./MyAlgorithm.h"
#include "./QuickSort.h"
#include "./DualPivotQuicksort.h"

#define VEC_DEFAULT_CAPACITY 5

ALGORITHM_BEGIN

/*
 * A simple vector.
 * A vector is like an encapsulated array. It can expand and shrink "automatically"
 * Also, it support random access.
 * Notice: the default sorted sequence is from smallest to the largest.
 */
template <typename T> class MyVector
{
protected:
    T* DataBuff;//pointer to the data array.
    int Size;
    int Capacity;

    /**
    * copy [lo,hi) from array A into this vector.
    * Only used in constructors.
    **/
    void CopyFrom(T const* A,Rank lo,Rank hi)
    {
        Capacity = 2*(hi-lo);
        DataBuff = new T[Capacity];
        Size = 0;
        while(lo<hi)
        {
            DataBuff[Size] = A[lo];
            Size++;
            lo++;
        }
    }

    /**
    * Expand the vector 2 times when the capacity is not enough.
    **/
    void Expand()
    {
        if(Size<Capacity)
            return;
        if(Capacity<VEC_DEFAULT_CAPACITY)
            Capacity = VEC_DEFAULT_CAPACITY;

        T* oldbuff = DataBuff;
        Capacity *= 2;
        DataBuff = new T[Capacity];
        for(int i=0;i<Size;i++)
            DataBuff[i] = oldbuff[i];
        delete [] oldbuff;
    }

    /**
    * Shrink the capacity to a half when the size is a half less than the capacity.
    **/
    void Shrink()
    {
        if(Capacity < VEC_DEFAULT_CAPACITY<<1)
            return;
        if(Size > Capacity>>2)
            return;

        T* oldbuff = DataBuff;
        Capacity >>= 1;
        DataBuff = new T[Capacity];
        for(int i=0;i<Size;i++)
            DataBuff[i] = oldbuff[i];
        delete [] oldbuff;
    }

public:
    MyVector(int c = VEC_DEFAULT_CAPACITY,int s = 0,T e = 0)
    {
        Capacity = c;
        DataBuff = new T[c];
        for(Size=0;Size<s;Size++)
            DataBuff[Size] = e;
    }

    MyVector(T const* A,Rank n)
    {
        CopyFrom(A,0,n);
    }

    MyVector(T const* A,Rank lo,Rank hi)
    {
        CopyFrom(A,lo,hi);
    }

    MyVector(MyVector<T> const& V)
    {
        CopyFrom(V.DataBuff,0,V.Size);
    }

    MyVector(MyVector<T> const& V,Rank lo,Rank hi)
    {
        CopyFrom(V.DataBuff,lo,hi);
    }
    MyVector<T>& operator=(MyVector<T> const& V)
    {
        CopyFrom(V.DataBuff,0,V.Size);
        return (*this);
    }

    virtual ~MyVector()
    {
        delete [] DataBuff;
    }

    //======simple getter=====//
    int capacity() const
    {
        return Capacity;
    }

    int size() const
    {
        return Size;
    }

    bool IsEmpty() const
    {
        return (Size == 0);
    }

    //check if the vector is disordered.
    bool Disordered() const
    {
        Rank i = 0;
        while(i<Size-1)
        {
            if(DataBuff[i] > DataBuff[i+1])
                return true;
            i++;
        }
        return false;
    }

    //===Find & Search methods===//
    T& FindMax(Rank lo,Rank hi)
    {
        Rank r = lo;
        while(lo<hi)
        {
            if(DataBuff[r]<DataBuff[lo])
                r = lo;
            lo++;
        }
        return DataBuff[r];
    }

    T& FindMin(Rank lo,Rank hi)
    {
        Rank r = lo;
        while(lo<hi)
        {
            if(DataBuff[r]>DataBuff[lo])
                r = lo;
            lo++;
        }
        return DataBuff[r];
    }

    /**
    * Find element e in the unsorted vector, range [lo,hi).
    * if not found, return hi.
    * Time complexity: O(n)
    */
    Rank Find(T const& e,Rank lo,Rank hi)
    {
        while(lo<hi)
        {
            if(DataBuff[lo] == e)
                break;
            lo++;
        }
        return lo;
    }

    /**
    * Find element e in the whole vector.
    * Time complexity: O(n)
    **/
    Rank Find(T const& e)
    {
        return Find(e,0,Size);
        
    }

    /**
    * Find element e or the suitable position for e 
    * in the range[lo,hi) using binary search.
    * This method is supposed to work with insertion sort,
    * it returns the Rank r is the largest position for the reason of fast insertion. 
    * Time complexity: O(logn)
    **/
    Rank BinSearch(T const& e,Rank lo,Rank hi) const
    {
        while(lo<hi)
        {
            Rank mi = (lo+hi)>>1;//find the middle position
            if(e<DataBuff[mi])
                hi = mi;
            else
                lo = mi+1;
        }
        return --lo;
    }

    /**
    * Find element e or the suitable position for e in the whole vector using binary search.
    * Time complexity: O(logn)
    **/
    Rank BinSearch(T const& e) const
    {                                                   
        return BinSearch(e,0,Size);
    }

    /**
    * Overload operator [] to support easy random access.
    **/
    T& operator[](Rank r) const
    {
        return DataBuff[r];
    }

    bool Equals(const MyVector& vec)
    {
        if(Size!=vec.size())
            return false;
        for(int i=0;i<Size;++i)
        {
            if(DataBuff[i]!=vec[i])
                return false;
        }
        return true;
    }

    bool operator==(const MyVector& vec)
    {
        return Equals(vec);
    }

    //==find & search ends==//

    //==modify methods(Insert remove deduplicate)==//

    /**
    * Insert element e into the position r.
    * The reason why its slower than linked list in insertion: it need move the sublist one position back.
    * Return the insert position 
    **/
    Rank Insert(T const& e,Rank r)  
    {
        Expand();
        for(int i = Size;i>r;i--)
            DataBuff[i] = DataBuff[i-1];
        DataBuff[r] = e;
        Size++;
        return r;   
    }

    /**
    * Insert the element into the last postion.
    **/
    Rank Insert(T const& e) 
    {
        return Insert(e,Size);
    }

    /**
    * Remove element in the range [lo,hi)
    * Return the number of elements that have been removed.
    **/
    int Remove(Rank lo,Rank hi)
    {
        if(lo == hi)
            return 0;
        while(hi<Size)//move the elements backward.
        {
            DataBuff[lo] = DataBuff[hi];
            hi++;
            lo++;
        }
        Size = lo;
        Shrink();//check if need shrink
        return (hi-lo);
    }

    /**
    * Remove element at the postion r
    * Return the element at the postion r.
    **/
    T Remove(Rank r)
    {
        T e = DataBuff[r];
        Remove(r,r+1);
        return e;
    }

    /**
    * Remove duplicate elements in an unsorted vector.
    * Repeatedly find duplicate element and delete.Brute force...
    * Return the number of elements that has been deleted.
    * Time complexity: O(n^2)
    **/
    int Deduplicate()
    {
        int oldsize = Size;
        Rank i = Size-1;
        Rank temp = 0;
        while(i)
        {
            temp = Find(DataBuff[i],temp,i);
            if(temp != i)
                Remove(temp);
            else
                temp = 0;//make it 0 to find duplicate element from the beginning of the vector.   
            i--;
        }
        Shrink();
        return (oldsize-Size);
    }

    /**
    * Remove duplicate elements in an sorted vector.
    * Return the number of elements that has been deleted.
    * Time complexity: O(n)
    **/
    int Uniquify()
    {
        Rank i = 0,j = 1;
        int oldsize = Size;
        while(j<Size)
        {
            if(DataBuff[i] != DataBuff[j])
            {
                i++;
                DataBuff[i] = DataBuff[j];
            }
            j++;
		}
        Size = i+1;
        Shrink();
        return (oldsize-Size);
    }

    //======sort methods======//

    /**
    * Merge 2 sorted sub array { [lo,mi), [mi,hi) }  into 1
    * Time complexity: O(n)
    **/
    void Merge(Rank lo,Rank mi,Rank hi)
    {            
        int lb = mi-lo,lc = hi-mi;//get the length of the sub array.
        T* B = new T[lb];
        T* A = DataBuff+lo;
        T* C = DataBuff+mi;
        for(Rank i=0;i<lb;i++)//back up the first sub array.
            B[i] = A[i];
        for(Rank n=0,i=0,j=0;(i<lb)||(j<lc);)//compare the first and the second sub array repeatedly
        {                                                          //put the smaller element into the storage. 
            if((i<lb) && (B[i]<=C[j] || j>=lc))
            {
                A[n] = B[i];
                n++;
                i++;
            }
            if((j<lc) && (B[i]>C[j] || i>=lb))
            {
                A[n] = C[j];
                j++;
                n++;
            }
        }
        delete [] B;//delete backup
    }

    /**
    * Sort from Rank lo to Rank hi using mergesort
    * Time complexity: O(nlogn) 
    * The "slicing" procedure cost O(logn) and the merge cost O(n)
    **/
    void MergeSort(Rank lo,Rank hi)
    {
        if(hi-lo < 2)
            return;
        Rank mi = (lo+hi)>>1;

        MergeSort(lo,mi);//"slicing" logn times
        MergeSort(mi,hi);

        Merge(lo,mi,hi);
    }

    /**
    * Sort from Rank lo to Rank hi using insertion sort
    * Time complexity: O(n^2) 
    * Find suitable place in sorted vector then insert.
    **/
    void InsertionSort(Rank lo,Rank hi)
    {
        Rank r = lo+1;//begin with the second rank
        while(r<hi)
        {
            Rank position = BinSearch(DataBuff[r],lo,r);//search in the sorted vector[lo,i)
            T element = Remove(r);
            Insert(element,position+1);
            r++;
        }
    }

    /**
    * Sort from Rank lo to Rank hi using selection sort
    * Time complexity: O(n^2) 
    * Find suitable place in sorted vector then insert.
    **/
    void SelectionSort(Rank lo,Rank hi)
    {
        Rank r = lo;
        while(r<hi)
        {
            T& min = FindMin(r,hi);
            swap(min,(*this)[r]);
            r++;
        }
    }

    /**
    * Use QuickSort to sort this vector in the range of [lo,hi]
    * and use partitionflag to choose partition methods.
    * check QuickSort.h to see the difference and the choices for 
    * partitionflag.
    **/
    void QuickSort(Rank lo,Rank hi,int partitionflag = 0)
    {
        MyAlgorithm::QuickSort(DataBuff,lo,hi,partitionflag);
    }

    /**
    * Interface for Dual Pivot Quicksort.
    * Sort range [lo, hi]  .
    * Use boolean leftmost to illustrate if the subarray is the leftmost
    * or not. If not use correctly this may cause array out of bound.
    * See DualPivotQuicksort.h for more details.
    **/
    void DPQuicksort(Rank lo, Rank hi, bool leftmost)
    {
        MyAlgorithm::DualPivotQuicksort(DataBuff, lo, hi, leftmost);
    }

    //=======iterate methods=======//
    /**
    * From this point, C++ is dealing with iteration more elegant than Java.
    * It can use function object to "pass" action on each element.
    * Java instead, you need to write looping procedure every time, though it may be easier to understand.
    * This problem is solved until Java have Stream Framework.
    **/
    void Traverse(void (*visit)(T const&))
    {
        for(int i=0;i<Size;i++)
            (*visit)(DataBuff[i]);
    }
    
    template<typename VST> void Traverse(VST& visit)
    {
        for(int i=0;i<Size;i++)
            visit(DataBuff[i]);
    }
};// class MyVector

ALGORITHM_END

#endif //MYVECTOR_H