/**
* BinarySearchTree.h
* Inherit BinTree.Defines BinarySearchTree.
* Last modified on: 2016/10/11
* Author:lwz
**/

#ifndef BINARYSEARCHTREE_H
#define BINARYSEARCHTREE_H
    
#include "./BinTree.h"
#include "./MyAlgorithm.h"

ALGORITHM_BEGIN

/**
* A BinarySearchTree is a binary tree with nodes in specific order:
* the left child node is always smaller and the right child node is always larger than current node.
* Thus, the inorder traversal of BinarySearchTree should be in sorted sequence, from the smallest to the largest.
* So Time complexity of searching, inserting methods becomes O(logn) in average.
* However, if not "balanced" the Time complexity will become O(n) in worst circumstances.
* So, the various extensions of BST are focusing on the different way of keeping "balance", to achieve better performance.  
**/
template<typename T>
class BinarySearchTree : protected BinTree<T>
{
protected:
    BinNodePosi(T) _hot;//use to store the target node's parent, frequently used in further externsion

    //connect 3 nodes and 4 trees
    //b is the parent node and a the left child c the right child.
    //T0 and T2 the left subtree for a and c, T1 T3 the right subtree for and c.
    BinNodePosi(T) connect34(BinNodePosi(T) a,BinNodePosi(T) b,BinNodePosi(T) c,BinNodePosi(T) T0,BinNodePosi(T) T1,
        BinNodePosi(T) T2,BinNodePosi(T) T3)        
    {
        a->LChild = T0;
        if(T0)
            T0->Parent = a;
        a->RChild = T1;
        if(T1)
            T1->Parent = a;
        UpdateHeight(a);
        c->LChild = T2;
        if(T2)
            T2->Parent = c;
        c->RChild = T3;
        if(T3)
            T3->Parent = c;
        UpdateHeight(c);
        a->Parent = c->Parent = b;
        b->LChild = a;
        b->RChild = c;
        UpdateHeight(b);   //remember to update the height above b 
        return b;
    }

    /**
    * use connect34 method above to rotate(and maintain balance)
    * Notice: Remember to reconnect the great grandparent to the grandparent
    * after the rotation
    **/
    BinNodePosi(T) rotateAt(BinNodePosi(T) v)   
    {                                                                           //zig - clockwise. zag - counterclockwise
        BinNodePosi(T) p = v->Parent;                    //time complexity: constant-time
        BinNodePosi(T) g = p->Parent;                    //there are 4 circumstances
        if(IsLChild(*p))
        {
            if(IsLChild(*v))
            {
                p->Parent = g->Parent;
                return connect34(v,p,g,v->LChild,v->RChild,p->RChild,g->RChild);
            }
            else
            {
                v->Parent = g->Parent;
                return connect34(p,v,g,p->LChild,v->LChild,v->RChild,g->RChild);
            }
        }
        else
        {
            if(IsLChild(*v))
            {
                v->Parent = g->Parent;
                return connect34(g,v,p,g->LChild,v->LChild,v->RChild,p->RChild);
            }
            else
            {
                p->Parent = g->Parent;
                return connect34(g,p,v,g->LChild,p->LChild,v->LChild,v->RChild);
            }
        }
    }

    /**
    * recursive method to search in the tree for elemnet e.
    * return the reference of target element's node's pointer.
    **/
    BinNodePosi(T)& SearchIn(BinNodePosi(T)& x,T const& e,BinNodePosi(T)& hot)
    {                                                                           //hot to record the parent of the target
        if(!x || (x->Data == e))        //Base. Return NULL if not found.                                
            return x;                           //Also notice: it returns the reference of suitable insert position
        hot = x;    
        BinNodePosi(T)& v = (x->Data > e)? x->LChild:x->RChild;//go left or right   
        return SearchIn(v,e,hot);
    }

    /**
    * remove element at x, use hot to record the parent of the removal node, 
    * x also the reference of the parent pointer.
    **/
    BinNodePosi(T) RemoveAt(BinNodePosi(T)& x,BinNodePosi(T)& hot)
    {
        BinNodePosi(T) w = x;      
        BinNodePosi(T) succ = NULL; 
        if(!HasLChild(*x))
        {
            succ = x->RChild;   
            if(IsRoot(*x))
                x = _root = succ;//move the root
            else
            {
                if(IsRChild(*x))//connect x's parent with its successor
                    x = x->Parent->RChild = succ;
                else
                    x = x->Parent->LChild = succ;
            }
        }
        else if(!HasRChild(*x))//the same steps as above
        {
            succ = x = x->LChild;
            if(IsRoot(*x))
                x = _root = succ;
            else
            {
                if(IsRChild(*x))
                    x = x->Parent->RChild = succ;
                else
                    x = x->Parent->LChild = succ;
            }
        }
        else // if x has both children, find its successor to replace it.
        {
            w = w->Succ();
            swap(w->Data,x->Data); // so w has only left child or right child.
            hot = w->Parent;
            succ = w->RChild;//w must not have left child
            if(hot == x)//w is the right child of x
                hot->RChild = succ;//so its safe to skip
            else               //then w must be the left child of  hot
                hot->LChild = succ;
            //x = succ;
        }
        if(succ)
            succ->Parent = hot;
        delete w;
        return succ;
    }

public:
    //all virtual methods.
    //For overload.

    /**
    * Search for element e in the BST
    * Return the pointer's reference of target element,
    * or NULL if target element not found.
    * And the BST's _hot is the target node's parent.
    **/
    virtual BinNodePosi(T)& Search(T const& e)
    {
        return SearchIn(_root,e,_hot = NULL);
    }

    /**
    * Search for element e in the BST
    * Return the pointer's reference of target element,
    * or NULL if target element not found.
    **/
    virtual bool Remove(T const& e)
    {
        BinNodePosi(T)& x = Search(e);
        if(!x)
            return false;
        RemoveAt(x,_hot);
        _size--;
        UpdateHeightAbove(_hot);
        return true;
    }
    
    /**
    * Insert element e.
    * Return the pointer of the inserted node.
    **/
    virtual BinNodePosi(T) Insert(T const& e)
    {
        BinNodePosi(T)& x = Search(e);      //Find suitable position
        if(x)                                                      
            return x;

        if(_hot == NULL)//_hot now the parent of the insert position.
            return InsertAsRoot(e);

        if(e<_hot->Data)
            this->InsertAsLC(_hot,e);//use BinTree's method
        else
            this->InsertAsRC(_hot,e);
        return x;
    }

    /** 
    * Return the taller child's pointer of x.
    * If both child has the same height, return the child on the same side with x. 
    **/
    BinNodePosi(T) TallerChild(BinNodePosi(T) x)
    {
        if(Stature(x->LChild) > Stature(x->RChild))
            return x->LChild;
        else
        {
            if(Stature(x->RChild) > Stature(x->LChild))
                return x->RChild;
            else                                        //the same height
            {
                if(IsLChild(*x))
                    return x->LChild;
                else
                    return x->RChild;
            }
        }
    }

    //use inorder traversal to traverse.
    template <typename VST> void Traverse(VST& visit)
    {
        BinTree::InTravWithNoStack(visit);
    }

    //===properties===//
    int Size()
    {
        return _size;
    }

    bool IsEmpty()
    {
        return _size==0;
    }

    BinNodePosi(T) Root()
    {
        return _root;
    }
};

ALGORITHM_END

#endif //BINARYSEARCHTREE_H