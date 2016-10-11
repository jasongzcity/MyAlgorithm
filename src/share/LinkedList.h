/* Created on 2015-11-22 16:21:08    
     Author:LWZ 
     this header file defines a LinkedList container.
*/
#ifndef  LINKEDLIST_H
#define LINKEDLIST_H

#include <stdlib.h>  //usage of rand()
#include "./MyAlgorithm.h"

#define ListNodePosi(T) ListNode<T>*

ALGORITHM_BEGIN

//the node for the linked list
template <class T> class ListNode
{
public:
    T data;                                 //data
    ListNodePosi(T) pred;       //predecessor
    ListNodePosi(T) succ;       //successor
    
    ListNode(){}
    
    ListNode(T e,ListNodePosi(T) p = NULL, ListNodePosi(T) s = NULL)        
        :data(e),pred(p),succ(s){}

    ListNodePosi(T) insertAsPred(T const& e)
    {
        ListNodePosi(T) pTemp = new ListNode(e,pred,this);
        pred->succ = pTemp;
        pred = pTemp;
        return pTemp;
    }

    ListNodePosi(T) insertAsSucc(T const& e)
    {
        ListNodePosi(T) pTemp = new ListNode(e,this,succ);
        succ->pred = pTemp;
        succ = pTemp;
        return pTemp;
    }
};

/*
* Linked list. 
* Compared with array-supported list, it's faster at removing and inserting, but slower at iterating and random access.
* Notice: The elements put into the LinkedList should allow the operate '=' '<' and '>' 
* The default sorted sequence is from smallest to largest
*/
template <class T> class LinkedList
{
protected:
    int size;                                   //number of nodes
    ListNodePosi(T) header;       //using sentinel node so no needs to consider degenerate circumstances that the pred or 
    ListNodePosi(T) trailer;        //succ is null

    void init()
    {
        header = new ListNode<T>; 
        trailer = new ListNode<T>;

        header->succ = trailer;
        header->pred = NULL;

        trailer->pred = header;
        trailer->succ = NULL;

        size = 0;
    }

    //copy n nodes from p
    //O(n)
    void copyNodes(ListNodePosi(T) p,int n)
    {
        while(n--)
        {
            InsertAsLast(p->data);
            p = p->succ;
        }
    }

public:
    LinkedList()
    {
        init();     //default method
    }

    LinkedList(LinkedList<T> const& L)
    {
        init();
        copyNodes(L.First(),L.Size());
    }

    LinkedList(LinkedList<T> const& L,Rank r,int n)
    {
        init();
        ListNodePosi(T) pData = L.First();
        while(r--)
            pData = pData->succ;
        copyNodes(pData,n);
        size = n;
    }

    //Destructor
    virtual ~LinkedList()
    {
        Clear();
        delete header;
        delete trailer;
    }

    //========properties=======//
    
    ListNodePosi(T) First() const
    {
        return header->succ;
    }

    ListNodePosi(T) Last() const
    {
        return trailer->pred;
    }

    T& operator[](Rank r) const
    {
        if(r+1>size)
            return null;
        ListNodePosi(T) pTemp = First();
        
        while(r--)
            pTemp = pTemp->succ;
        return pTemp->data;
    }

    int Size() const
    {
        return size;
    }

    bool IsEmpty() const 
    {
        return (header->succ == trailer);
    }

    //check if p points to a valid position
    bool IsValid(ListNodePosi(T) p) const
    {
        return p && (p != header) && (p != trailer);
    }

    //=====find/search methods =====//
    /**
    *  Find the element e before p(not include),in the range of n, in an unsorted list.
    *  Return the position of the element. If not contain return NULL
    *  Time complexity: O(n)
    **/
    ListNodePosi(T) Find(T const& e,ListNodePosi(T) p,int n) const
    {
        while(n--)
        {
            p = p->pred;
            if(e == p->data)
                return p;
        }
        return NULL;
    }

    /**
    * Find element e in an unsorted list 
    **/
    ListNodePosi(T) Find(T const& e) const
    {
        return Find(e,trailer,size);
    }

    /**
    * Find element(or the first element smaller than the e) in a sorted list before position p, in the range of n
    * return the position of the element.
    * Time complexity: O(n)
    **/
    ListNodePosi(T) Search(T const& e,ListNodePosi(T) p,int n) const    
    {                                                                   
        while(n>=0)
        {
            n--;
            p = p->pred;
            if(p->data <= e) //need overload the operator
                break;
        }
        return p;//maybe header
    }

    /** 
    * Search element(or the first element smaller than e) e in a sorted list.
    **/
    ListNodePosi(T) Search(T const& e)  const
    {
        return Search(e,trailer,size);
    }

    /**
    * Check if the list is sorted from the smallest to the largest
    * return true if it is sorted.
    * Time complexity: O(n)
    **/
    bool Disordered() const
    {
        ListNodePosi(T) p = First();
        T e = p->data;
        while(p != Last())
        {
            if(e > (p->succ->data))
                return false;
            else
            {
                p = p->succ;
                e = p->data;
            }
        }
        return true;
    }

    /**
    * Find the biggest element in n positions begin with position p.
    * return the postion of the biggest element.
    * Time complexity: O(n)
    **/
    ListNodePosi(T) SelectMax(ListNodePosi(T) p,int n)  const
    {
        ListNodePosi(T) max = p; 
        while(n>1) 
        {
            n--;
            p = p->succ;
            if(max->data < p->data)
                max = p;
        }
        return max;         
    }

    /**
    * Find the biggest element in the whole list.
    * return the postion of the biggest element.
    * Time complexity: O(n)
    **/
    ListNodePosi(T) SelectMax() const
    {
        return SelectMax(First(),size);
    }


    //========write methods=========//

    ListNodePosi(T) InsertAsFirst(T const& e) 
    {
        ListNodePosi(T) pTemp = header->insertAsSucc(e);
        size++;
        return pTemp;
    }

    ListNodePosi(T) InsertAsLast(T const& e) 
    {
        ListNodePosi(T) pTemp = trailer->insertAsPred(e);
        size++;
        return pTemp;
    }

    ListNodePosi(T) InsertAsSelSucc(ListNodePosi(T) p,T const& e)
    {
        ListNodePosi(T) pTemp = p->insertAsSucc(e);
        size++;
        return pTemp;
    }

    ListNodePosi(T) InsertAsSelPred(ListNodePosi(T) p,T const& e)
    {
        ListNodePosi(T) pTemp = p->insertAsPred(e);
        size++;
        return pTemp;
    }

    T Remove(ListNodePosi(T) p)
    {
        T temp = p->data;//Notice: overload the operator '=' here if using complicated structure
        p->succ->pred = p->pred;
        p->pred->succ = p->succ;
        delete p;
        size--;
        return temp;
    }

    //return the size of nodes those have been deleted
    int Clear()
    {
        int nTemp = size;
        while(size)
            Remove(header->succ);
        return nTemp;
    }

    /**
    * Delete duplicate elements in the unsorted list.
    * Return the number of elements have been deleted.
    * Time complexity:O(n^2)
    **/
    int Deduplicate()
    {
        if(size<2)
            return 0;

        int count = 0;//count the number of deletion
        ListNodePosi(T) p = First();
        int r = 0;//the number to search in the finding procedure
        while(p != trailer)
        {
                                        
            ListNodePosi(T) q = Find(p->data,p,r);  //find duplicate element

            if(q)                    //if found
            {
                Remove(q);
                count++;
                r--;                  //deduce the finding number or it will cause null pointer
            }
            else                    //if not found, find the duplicate for the next element
            {
                r++;
                p = p->succ;
            }
        }
        return count;
    }

    /**
    * Delete duplicate elements in the sorted list.
    * Return the number of elements have been deleted.
    * Time complexity:O(n)
    **/
    int Uniquify()
    {
        int count = 0;
        if(size<2)
            return 0;
        ListNodePosi(T) p = First();
        ListNodePosi(T) q = p->succ;
        while(q != trailer)
        {
            if(p->data == q->data)
            {
                Remove(q);
                count++;
            }
            else
                p = q;
            q = p->succ;
        }
        return count;
    }

    /**
    * Merge m nodes(started from q) from LinkedList L(target list) 
    * to this LinkedList(started from p,forward n-times limitation,source list)
    * Notice: both list should be sorted before merge.
    * The p return the beginning of the merged list.
    * Time complexity: O(n)
    **/
    void Merge(ListNodePosi(T) & p,int n,LinkedList<T>& L,ListNodePosi(T) q,int m)
    {
        ListNodePosi(T) head = p->pred;
        while(m)//search the target list for the node smaller than the current p position
        {
            if((n>0) && (p->data <= q->data))
            {
                p = p->succ;
                if(p == q)//degenerated situation: the p & q in the same list. return directly.
                    break;
                n--;
            }
            else                                          //insert 
            {
                q = q->succ;
                T e =L.Remove(q->pred);
                InsertAsSelPred(p,e);
                m--;
            }
        }//while
        p = head->succ;
    }           

    /**
    * Sort method. Sort n nodes begin with p(include p)
    * Notice: all nodes should be valid or may cause null pointer
    **/
    void Sort(ListNodePosi(T) p,int n)
    {
        switch(rand()%3)//select one the method randomly
        {
            case 1:
                InsertionSort(p,n);
                break;
            case 2:
                SelectionSort(p,n);
                break;
            default:
                MergeSort(p,n);
                break;
        }
    }

    /**
    * MergeSort.Sort n nodes begin with position p
    * Time complexity: O(nlogn)
    **/
    void MergeSort(ListNodePosi(T) p,int n)
    {  
        if(n<2)//recursion base,occur when only one node in the sub-list
            return;
        int m = n>>1;
        ListNodePosi(T) q = p;
        for(int i=0;i<m;i++)//find the begin node of the second-half list
            q = q->succ;
        ListNodePosi(T) ppred = p->pred;//backup
                
        MergeSort(p,m);                             //"slicing" the list into two half recursively

        ListNodePosi(T) qpred = q->pred;//backup

        MergeSort(q,n-m);

        p = ppred->succ;    
        q = qpred->succ;                           

        Merge(p,n,*this,q,n-m);//merge two lists together
    }
    
    /**
    * SelectionSort.  Repeatedly select the largest node in the front list
    * and move it to the behind list, until the the front list is empty.
    * And the behind list is sorted
    * Time complexity: O(n^2)
    **/
    void SelectionSort(ListNodePosi(T) p,int n)
    {
        int temp = n;
        ListNodePosi(T) tail = p;
        ListNodePosi(T) head = p->pred;
        while(temp--)//find the tail
            tail = tail->succ;

        while(n>1)//find largest node repeatedly
        {
            ListNodePosi(T) copy = SelectMax(head->succ,n);
            InsertAsSelPred(tail,copy->data);       //insert into behind list
            Remove(copy);                                    //move forward the boundary
            tail = tail->pred;
            n--;
        }
    }

    /**
    * InsertionSort. The front list is sorted and the behind list is not.
    * Search in the sorted list for right position for unsorted element, and then insert.
    * p for the beginning position, n for the number of elements to be sorted.
    * Time complexity: O(n^2)
    **/
    void InsertionSort(ListNodePosi(T) p,int n)
    {
        int count = 0;//the number of nodes in the front sorted list
        while(n--)
        {
            ListNodePosi(T) q = Search(p->data,p,count);//find the first smaller node
            InsertAsSelSucc(q,p->data);//insert as successor
            p = p->succ;
            Remove(p->pred);//remove the node
            count++;
        }
    }
    
    //iteration methods
    //use a function pointer to iterate
    void Traverse(void (*visit)(T&))
    {
        ListNodePosi(T) p = First();
        while(p != trailer)
        {
            (*visit)(p->data);
            p = p->succ;
        }
    }
    
    //use a function object
    template <typename VST> void Traverse(VST& visit)
    {
        ListNodePosi(T) p = First();
        while(p != trailer)
        {
            visit(p->data);
            p = p->succ;
        }
    }
};//LinkedList

ALGORITHM_END

#endif //LINKEDLIST_H