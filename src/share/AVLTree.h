/**
* AVLTree.h
* Last modified on: 2016/10/12
* Author:lwz
**/

#ifndef AVLTREE_H
#define AVLTREE_H

#include "./BinarySearchTree.h"
#include "./MyAlgorithm.h" 

#include <math.h>//for abs()

ALGORITHM_BEGIN

/******************************************************
  BinTree
    BST
      kd-tree/RED_BLACK TREE/AVL TREE - BBST(Balanced Binary Search Tree)
      they use some way to maintain balance.
******************************************************/

///////////////////////////////FAST ROUTE/////////////////////////////////////

#define Balanced(x) ( Stature( (x).LChild ) == Stature( (x).RChild ) )      //check if balanced
#define BalFactor(x) (abs( Stature( (x).LChild ) - Stature( (x).RChild ) )) //balance factor
#define AVLBalanced(x) (BalFactor(x)<2)

///////////////////////////////////////////////////////////////////////////////////////

/**
* The balanced condition for AVL Tree is the difference between height of right child
* and left child is not larger than 1.
* When balanced, the average time complexity of insertion and removal is O(logn)
**/
template <typename T>
class AVLTree : public BinarySearchTree<T>
{
    //only need to overload remove & insert
public:
    virtual bool Remove(T const& e)
    {
        BinNodePosi(T)& del = Search(e);
        if(!del)
            return false;
        RemoveAt(del,_hot);
        _size--;
        for(BinNodePosi(T) g=_hot;g;g=g->Parent) //check if balanced from deleted node to root
        {
            if(!AVLBalanced(*g))
            {
                if(IsRoot(*g))
                    _root = rotateAt(TallerChild(TallerChild(g)));
                else
                    g = FromParentTo(*g) = rotateAt(TallerChild(TallerChild(g)));                                       
            }
            UpdateHeight(g);//the unbalance cause by deletion will have effect to the higher tree.
        }
        return true;
    }

    virtual BinNodePosi(T) Insert(T const& e)
    {
        BinNodePosi(T)& x = Search(e);
        if(x)
            return x;                 
        x = new BinNode<T>(e,_hot);
        _size++;
        for(BinNodePosi(T) g=_hot;g;g=g->Parent)
        {
            if(!AVLBalanced(*g))
            {
                if(IsRoot(*g))
                    _root = rotateAt(TallerChild(TallerChild(g)));
                else
                {
                    BinNodePosi(T)& tp = FromParentTo(*g);
                    tp = rotateAt(TallerChild(TallerChild(g)));
                }
                                
                break;//if the unbalanced subtree has adjusted and rotated, it will not affact the higher tree.
            }
            else
                UpdateHeight(g);
        }
        return x;
    }
};

ALGORITHM_END

#endif