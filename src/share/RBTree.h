/**
* RBTree.h
* Red Black Tree.
* Created on 2016-12-9 
* Author: lwz
**/

#ifndef RBTREE_H
#define RBTREE_H

#include "./MyAlgorithm.h"
#include "./BinarySearchTree.h"

///////////////////////FAST ROUTE////////////////////////////
#define IsBlack(x) ((!x) || ((x)->Color == RB_BLACK))
#define IsRed(x)   !(IsBlack(x))
////////////////////////////////////////////////////////

ALGORITHM_BEGIN

/**
* Inherited search method from binary search tree.
* Special characteristics that must be maintained in a Red black tree:
* 1. root is always black.
* 2. leaf is always black ( or say, can be regarded as black )
* 3. red nodes can only have black children.
* 4. There must be same amount of black nodes from root to any leaves in one red black tree.
* Advantages compared to AVL Tree: the red nodes between black nodes reduce the chances of rotation, 
* and still maintain O(logn) time complexity on insertion and deletion. 
**/
template<typename T>
class RBTree : public BinarySearchTree<T>
{
protected:

    /**
    * The situation "Double Red" is just like the "Overflow" situation 
    * in (2,4) BTree. Overflow a red node to the parent.
    * In most cases, it cost constant time.
    * In worst cases, it cost O(logn), and it's much stable than AVL Tree. 
    **/
    void solveDoubleRed(BinNodePosi(T) n)
    {
        if(IsRoot(*n))
        {
            n->Color = RB_BLACK;//make the root black directly.
            UpdateHeight(n);
            return;
        }
        BinNodePosi(T) p = n->Parent;//parent
        if(p->Color == RB_BLACK)//double red solved.
            return;

        BinNodePosi(T) g = p->Parent; //get grandparent. p must not be root so it must not be NULL.
        BinNodePosi(T) u = Uncle(*p); //must not be NULL
        if(u->Color == RB_BLACK)//if uncle's black, simply reconnect n, parent, grandparent using connect 34 method
        {
            //"dyeing"
            g->Color = RB_RED; 
            if(IsLChild(*p) ^ IsLChild(*n)) // node and the parent node are not on the same side
                n->Color = RB_BLACK;
            else
                p->Color = RB_BLACK;
            
            BinNodePosi(T) r = rotateAt(n);//solved,height has been updated during rotation.
            if(r->Parent!=NULL)                  //reconnect
                FromParentTo(*r) = r;
        }
        else//uncle's red
        {
            u->Color = RB_BLACK;
            UpdateHeight(u);
            p->Color = RB_BLACK;
            UpdateHeight(p);
            g->Color = RB_RED;
            UpdateHeight(g);
            solveDoubleRed(g);
        }
    }

    void solveDoubleBlack(BinNodePosi(T) n)
    {
        //TODO:understanding all circumstances.
    }

    virtual int UpdateHeight(BinNodePosi(T) p)
    {
        p->Height = max(Stature(p->LChild),Stature(p->RChild));
        return IsBlack(p)?p->Height++:p->Height; 
    }

public:
    /**
    * Insert a node with element e
    * Time complexity: O(logn)
    * Return the pointer to the node containing e
    **/
    virtual BinNodePosi(T) Insert(T const& e)
    {
        BinNodePosi(T) &p = Search(e); //notice _hot is pointing target node's parent
        if(p)
            return p;
        p = new BinNode<T>(e,_hot,NULL,NULL,-1);//record height = black height -1
        _size++;
        solveDoubleRed(p);
        return p;
    }

    /**
    * Remove node containing element e
    * return true if deletion success.
    * return false if there is no nodes containing element e
    * Time complexity: O(logn)
    **/
    virtual bool Remove(T const& e)
    {
        BinNodePosi(T) & n = Search(e);
        if(!n)
            return false;
        BinNodePosi(T) succ = RemoveAt(n,_hot);
        --_size;
        if(!_size)//empty tree
            return true;
        if(_hot==NULL)//root has been deleted
        {
            succ->Color = RB_BLACK;//make the new root black.
            UpdateHeight(succ);
            return true;
        }
        if(BlackHeightUpdated(_hot)) //the deleted node is red. no need to repair
            return true;
        if(IsRed(succ)) //the deleted node is black
        {
            succ->Color = RB_BLACK;
            succ->Height++;
            return true;
        }
        solveDoubleBlack(succ);//both the deleted node and the successor are black, need to repair.
        return true;
    }

    /**
    * Check if the node p's height has been updated.
    **/
    bool BlackHeightUpdated(BinNodePosi(T) p)
    {
        if(Stature(p->LChild) == Stature(p->RChild))
        {
            if(IsBlack(p))
                return (p->Height == Stature(p->LChild)+1);
            else
                return (p->Height == Stature(p->LChild));
        }
        return false;
    }
};

ALGORITHM_END

#endif //RBTREE_H