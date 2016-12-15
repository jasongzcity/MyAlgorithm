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
#define IsBlack(x) (!(x) || ((x)->Color == RB_BLACK))
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
            n->Height++;
            return;
        }
        BinNodePosi(T) p = n->Parent;//parent
        if(IsBlack(p))//double red solved.
            return;

        BinNodePosi(T) g = p->Parent; //get grandparent. p must not be root so it must not be NULL.
        BinNodePosi(T) u = NULL;
        if(p == g->LChild)
            u = g->RChild;
        else
            u = g->LChild;

        if(IsBlack(u))//if uncle's black, simply reconnect n, parent, grandparent using connect 34 method
        {
            //"dyeing"
            g->Color = RB_RED; 
            if(IsLChild(*p) == IsLChild(*n)) // node and the parent node are not on the same side
                p->Color = RB_BLACK;
            else
                n->Color = RB_BLACK;

            BinNodePosi(T) gg = g->Parent;
            int tag = 0;
            if(gg && IsLChild(*g))
            {   
                tag = 1;
            }
            else if( gg && IsRChild(*g))
            {
                tag = 2;
            }
            BinNodePosi(T) r = rotateAt(n);
            r->Parent = gg;
            if(tag == 1)
            {
                gg->LChild = r;
            }
            else if(tag==2)
            {
                gg->RChild = r;
            }
            else//g is root
                _root = r;
        }
        else//uncle's red
        {
            u->Color = RB_BLACK;
            u->Height++;
            p->Color = RB_BLACK;
            p->Height++;
            if(!IsRoot(*g))
                g->Color = RB_RED; 
            solveDoubleRed(g);
        }
    }

    /**
    * The double black circumstance is just like the 
    * "Underflow" circumstance in BTree. 
    * parameter n stands for the successor of deleted node.
    * In most cases, it cost constant time.
    * In worst cases, it cost O(logn), and it's much stable than AVL Tree. 
    **/
    void solveDoubleBlack(BinNodePosi(T) n)
    {
        BinNodePosi(T) p = n ? n->Parent : _hot;//consider the circumstance the successor is NULL
        if(!p)
            return;
        BinNodePosi(T) b = (n==p->LChild) ? p->RChild : p->LChild;//get n's brother
        if(IsBlack(b))
        {
            BinNodePosi(T) nephew = NULL;//brother's son
            if(IsRed(b->RChild))
                nephew = b->RChild;
            if(IsRed(b->LChild))
                nephew = b->LChild;
            if(nephew != NULL)//one of the nephew is red
            {
                RBColor back = p->Color;
                BinNodePosi(T) r = FromParentTo(*p) = rotateAt(nephew);
                if(r->LChild)
                {
                    r->LChild->Color = RB_BLACK;
                    UpdateHeight(r->LChild);
                }
                if(r->RChild)
                {
                    r->RChild->Color = RB_BLACK;
                    UpdateHeight(r->RChild);
                }
                r->Color = back;
                UpdateHeight(r);
            }
            else if(IsRed(p)) //if the parent's red, brother is black and both nephews aren't red 
            {
                p->Color = RB_BLACK;
                b->Color = RB_RED;//safe to turn brother red.
                b->Height--;
            }
            else//both parent and brother are black, and nephews aren't red.
            {
                b->Color = RB_RED;
                b->Height--;
                p->Height--;
                solveDoubleBlack(p);//the parent is now "unbalance"
            }
        }
        else //the parent is black and brother is red, needs a rotation
        {
            //need to rotate at node p
            _hot = p;
            b->Color = RB_BLACK;
            b->Height++;
            p->Color = RB_RED;
            p->Height--;
            if(IsRChild(*b))//rotate left
                rotateLeft(p);
            else
                rotateRight(p);
            solveDoubleBlack(n);//solve once more for its parent is now red.
        }
    }

    virtual int UpdateHeight(BinNodePosi(T) p)
    {
        p->Height = std::max(Stature(p->LChild),Stature(p->RChild));
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
        return (p ? p : _hot->Parent);
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
        if(IsRed(succ)) //the deleted node is black and the successor is red
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