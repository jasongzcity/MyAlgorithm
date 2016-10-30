/**
* BTree.h
* Created on 2016-10-28 
* Author:lwz
**/

#ifndef BTREE_H
#define BTREE_H

#include "./MyStack.h"
#include "./MyVector.h"
#include "./MyAlgorithm.h"

ALGORITHM_BEGIN

#define BTNodePosi(T) BTreeNode<T>* 

/**
* BTree node.
* T for the type of key
**/
template<typename T> class BTreeNode
{
public:
    BTNodePosi(T) parent;
    MyVector<T> key;    
    MyVector< BTNodePosi(T) > child;

    BTreeNode()
    {
        parent = NULL;
        child.Insert(NULL,0);
    }

    BTreeNode(T e,BTNodePosi(T) lc = NULL,BTNodePosi(T) rc = NULL)
    {
        parent = NULL;  
        key.Insert(e,0);
        child.Insert(lc,0);
        child.Insert(rc,1);
        if(lc)                  
            lc->parent = this;
        if(rc)
            rc->parent = this;
    }

    virtual ~BTreeNode(){}

    /**
    * Destroy subtree with postorder traverse.
    * Use with caution! Remember to cooperate with BTree 
    * or it may cause null pointer when calling BTree methods.
    **/
    void DestroySubtree()
    {
        int i = 0;
        while(child.size() && child[0])
        {
            child[0]->DestroySubtree();
            delete child[0];
            child.Remove(0);
        }
    }
};

/**
* BTree template.
* T for the type of key.
* BTree expands the size of keys in a node to reduce the level of trees.
**/
template<typename T> class BTree
{
private:
    int _order; //order of this tree.
protected:
    int _size;              //the number of keys
    BTNodePosi(T) _root;
    BTNodePosi(T) _hot;//temporary storage of target's parent

    /**
    * called after insertion.
    * check if the number of the keys in current node larger than the order.
    * If do, "overflow" the key.
    * Time complexity: O(logn)
    **/
    void OverFlow(BTNodePosi(T) v)
    {
        if(v->key.size()<_order)//recursion base.
            return;

        Rank r = _order/2;

        BTNodePosi(T) right = new BTreeNode<T>(); //If more nodes, create a new node.
        for(int i=0;i<_order-r-1;i++)//and move the right half of the keys to the child node.
        {
            right->key.Insert(v->key.Remove(r+1),i);
            right->child.Insert(v->child.Remove(r+1),i);
        }
        right->child[_order-r-1] = v->child.Remove(r+1);//move the last child.
        if(right->child[0])//having children
        {
            for(int i=0;i<_order-r;i++)
                right->child[i]->parent = right;
        }

        BTNodePosi(T) p = v->parent;

        if(!p)//v is the root.
        {
            p = _root = new BTreeNode<T>(); //need to overflow, create new root.
            v->parent = p;
            p->child[0] = v;
        }

        Rank ins = 1 + p->key.BinSearch(v->key[0]);//search the position for v

        p->key.Insert(v->key.Remove(r),ins);//so now the position ins is for v and postion+1 is for right
        p->child.Insert(right,ins+1);//insert right node
        right->parent = p;
        OverFlow(p);//recursive call to check parent
    }

    void UnderFlow(BTNodePosi(T) v)
    {
        if( v->child.size() >= (_order+1)/2 )//recursion base.
            return;
        BTNodePosi(T) p = v->parent;
        if(!p)//v is root
        {
            if((v->key.IsEmpty()) && v->child[0])//root point to child
            {
                _root = v->child[0];
                _root->parent = NULL;
                delete v;
            }
            return;
        }

        Rank r = 0;
        while(p->child[r] != v)//locate r as the rank of v
            r++;

        if(r>0 && (p->child[r-1]->key.size() > (_order+1)/2))//borrow from left brother.
        {
            BTNodePosi(T) lb = p->child[r-1];

            v->key.Insert(lb->key.Remove(lb->key.size()-1),0);//borrow and swap
            swap(p->key[r-1],v->key[0]);//Notice: it does not violate the basic rules of search tree.

            v->child.Insert(lb->child.Remove(lb->child.size()-1),0);
            if(v->child[0])
                v->child[0]->parent = v;
            //no more recursion calls.
            return;
        }
        else if( (r<p->child.size()-1) && (p->child[r+1]->key.size()>(_order+1)/2) )//borrow from the right brother.
        {
            BTNodePosi(T) rb = p->child[r+1];
            v->key.Insert(rb->key.Remove(0),v->key.size());
            swap(p->key[r],v->key[v->key.size()-1]); 
            v->child.Insert(rb->child.Remove(0),v->child.size()); 
            if(v->child[v->child.size()-1])
                v->child[v->child.size()-1]->parent = v;
            //no more recursion calls.
            return;
        }
        else
        {
            if(r>0)     //left brother exists. merge with left brother
            {
                BTNodePosi(T) lb = p->child[r-1];
                lb->key.Insert(p->key.Remove(r-1));//get node from parent and put it in left brother
                p->child.Remove(r);
                while(v->key.size())//merge!
                {
                    lb->key.Insert(v->key.Remove(0));
                    lb->child.Insert(v->child.Remove(0));
                }
                lb->child.Insert(v->child.Remove(0));
                if(lb->child[0])//if child not null
                {
                    Rank i=0;
                    while(i<lb->child.size())
                        lb->child[i++]->parent = lb;//points to left brother as parent.
                }
                delete v;
            }
            else        //merge with right brother
            {
                BTNodePosi(T) rb = p->child[r+1];
                v->key.Insert(p->key.Remove(r));
                p->child.Remove(r+1);
                while(rb->key.size())
                {
                    v->key.Insert(rb->key.Remove(0));
                    v->child.Insert(rb->child.Remove(0));
                }
                v->child.Insert(rb->child.Remove(0));
                if(v->child[0])
                {
                    Rank i=0;
                    while(i<v->child.size())
                        v->child[i++]->parent = v;
                }
                delete rb;
            }
            UnderFlow(p);//recursion call to check its parent
        }
    }

public:

    /**
    * Must specify the order of this tree while construting.
    * Default 3
    **/
    BTree( int order = 3 )
    {
        _root = new BTreeNode<T>();
        _order = order;
        _size = 0;
    }

    virtual ~BTree()
    {
        _root->DestroySubtree();
    }
    
    //===query properties===//
    int Order() const
    {
        return _order;
    }
    
    int size() const
    {
        return _size;
    }
    
    /**
    * Return the reference of the root pointer.
    **/
    BTNodePosi(T)& Root()
    {
        return _root;
    }

    bool IsEmpty() const
    {
        return !_root;
    }

    /**
    * Search for key e.
    * Return the pointer of the node contains e.
    * Time complexity: O(logn)
    **/
    BTNodePosi(T) Search(T const& e)
    {
        BTNodePosi(T) v = _root;
        _hot = NULL;
        while(v)
        {
            int r = v->key.BinSearch(e);//return the largest rank of elements those smaller than e
            if((r>-1) && (e==v->key[r]))
                return v;

            //else
            _hot = v;
            v = v->child[r+1];//e must between key[r,r+1]
        }
        return NULL; //and _hot record the position for insertion.
    }

    /** 
    * Insert element e.
    * Time complexity: O(logn)
    **/
    bool Insert(T const& e)
    {
        BTNodePosi(T) v = Search(e);
        if(v)
            return false;
        //else
        int r = _hot->key.BinSearch(e);
        _hot->key.Insert(e,r+1);
        _hot->child.Insert(NULL,r+2);//prepare position for child node.
        _size++;
        OverFlow(_hot);//overflow check.
        return true;
    }

    bool Remove(T const& e)
    {
        BTNodePosi(T) p = Search(e);
        if(!p)
            return false;
        Rank r = p->key.BinSearch(e);
        if(p->child[0])//if p not leaf
        {
            BTNodePosi(T) next = p->child[r+1];
            while(next->child[0])//find the smallest in right child
                next = next->child[0];
            p->key[r] = next->key[0];//replace
            p = next;
            r = 0;
        }
        //now delete the rank r in node p, and p must be leaf
        p->key.Remove(r);
        p->child.Remove(r+1);
        _size--;
        UnderFlow(p); // get keys from parent if number of keys less than half of the order
    }

    template<typename VST> void traverse_internal(VST& visit, BTNodePosi(T) p)
    {
        if(!p)//base
            return;
        int i = 0;
        while(i<p->key.size())
        {
            traverse_internal(visit, p->child[i]);
            visit(p->key[i++]);
        }
        traverse_internal(visit, p->child[i]);//visit the last child.
    }

    template<typename VST> void InorderTraverse(VST& visit)
    {
        traverse_internal(visit,_root);
    }
};

ALGORITHM_END

#endif //BTREE_H