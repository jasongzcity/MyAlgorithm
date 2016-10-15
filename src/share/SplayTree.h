/**
* SplayTree.h
* Last modified on: 2016/10/12
* Author:lwz
**/

#ifndef SPLAYTREE_H
#define SPLAYTREE_H

#include "./BinarySearchTree.h"
#include "./MyAlgorithm.h"

ALGORITHM_BEGIN

/**
* Because of data locality, splay tree move the last visited node to the root, 
* and keep an average time complexity of O(logn) of each move basic action(insert/remove/search).
**/
template<typename T>
class SplayTree : public BinarySearchTree<T>
{
protected:
    /**
    * splay v to the root.
    * "Splay" means rotate twice.
    **/
    BinNodePosi(T) splay(BinNodePosi(T) v)
    {
        if(!v)
            return v;
        BinNodePosi(T) p;
        BinNodePosi(T) g;
        while((p= v->Parent)&&(g=p->Parent))//get parent & grandparent
        {
            BinNodePosi(T) gg = g->Parent;
            if(IsLChild(*p))
            {
                if(IsLChild(*v))
                {
                    //left-left zig-zig
                    //zig
                    g->AttachAsLChild(p->RChild);
                    p->AttachAsLChild(v->RChild);
                    //zig
                    p->AttachAsRChild(g);
                    v->AttachAsRChild(p);
                }
                else    
                {
                    //left-right zag-zig  
                    //zag
                    p->AttachAsRChild(v->LChild);
                    v->AttachAsLChild(p);
                    //zig
                    g->AttachAsLChild(v->RChild);
                    v->AttachAsRChild(g);
                }
            }
            else
            {
                if(IsRChild(*v))
                {
                    //right-right zag-zag
                    //zag
                    g->AttachAsRChild(p->LChild);
                    p->AttachAsLChild(g);
                    //zag
                    p->AttachAsRChild(v->LChild);
                    v->AttachAsLChild(p);
                }
                else
                {
                    //right-left zig-zag
                    //zig
                    p->AttachAsLChild(v->RChild);
                    v->AttachAsRChild(p);
                    //zag
                    g->AttachAsRChild(v->LChild);
                    v->AttachAsLChild(g);
                }
            }
            if(!gg)
                v->Parent = NULL;
            else
            {
                (g==gg->LChild)?(gg->AttachAsLChild(v)):(gg->AttachAsRChild(v));
                v->Parent = gg;
            }
            UpdateHeight(g);
            UpdateHeight(p);
            UpdateHeight(v);
        }//while((p=v->Parent) && (g=p->Parent))

        if(p = v->Parent)//when only a parent left(root)
        {
            if(IsLChild(*v))
            {
                p->AttachAsLChild(v->RChild);
                v->AttachAsRChild(p);
            }
            else
            {
                p->AttachAsRChild(v->LChild);
                v->AttachAsLChild(p);
            }
            UpdateHeight(p);
            UpdateHeight(v);
        }
        v->Parent = NULL;//v the root now
        return v;
    }

public:
    /**
    * Search for element e.
    * Return the reference of  the pointer of the target node.
    * Return NULL if not found and the last touched node will become root.
    **/
    virtual BinNodePosi(T)& Search(const T& e)
    {
        BinNodePosi(T) p = SearchIn(_root,e,_hot = NULL);
        _root = splay( p?p:_hot );
        return _root;
    }

    /**
    * Insert element e.
    * And the node contains e will become root.
    * Return the node contains e.
    **/
    virtual BinNodePosi(T) Insert(const T& e)
    {
        if(!_root)
        {
            _size++;
            return _root = new BinNode<T>(e);
        }
        if(e == Search(e)->Data)
            return _root;
        _size++;
        BinNodePosi(T) p = _root;
        if(p->Data > e)
        {
            p->Parent = _root = new BinNode<T>(e,NULL,p->LChild,p);
            if(p->LChild)
            {
                p->LChild->Parent = _root;
                p->LChild = NULL;
            }
        }
        else
        {
            p->Parent = _root = new BinNode<T>(e,NULL,p,p->RChild);
            if(p->RChild)
            {
                p->RChild->Parent = _root;
                p->RChild = NULL;
            }
        }
        UpdateHeightAbove(p);
        return _root;
    }

    /** 
    * Remove element e.
    * Return false if fail to delete.
    **/
    virtual bool Remove(const T& e) 
    {
        BinNodePosi(T) p = Search(e);
        if(p->Data != e)
            return false;
        if(!HasLChild(*p))
        {
            _root = p->RChild;
            if(_root)
                _root->Parent = NULL;
        }
        else if(!HasRChild(*p))
        {
            _root = p->LChild;
            if(_root)
                _root->Parent = NULL;
        }
        else
        {   
            BinNodePosi(T) lefttree = p->LChild;
            p->LChild = NULL;
            lefttree->Parent = NULL;//get the left tree

            _root = p->RChild;     //get the right tree.
            _root->Parent = NULL;
            Search(p->Data);       //get replace node in the right tree to the root.
            _root->LChild = lefttree;
            lefttree->Parent = _root;
            delete p;
            _size--;
        }
        if(_root)
            UpdateHeight(_root);
        return true;
    }
};

ALGORITHM_END

#endif //SPLAYTREE_H