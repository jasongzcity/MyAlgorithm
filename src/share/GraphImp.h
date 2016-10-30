/**
* GraphImp.h
* Implementations of Graph's searching algorithms.
* Last modified on: 2016/10/19
* Author:lwz
**/

#ifndef GRAPHIMP_H
#define GRAPHIMP_H

#include "./MyQueue.h" 
#include "stdlib.h"             //for rand()
#include "time.h"               //for time
#include "./MyAlgorithm.h"

ALGORITHM_BEGIN

/**
* The algorithms below only consider unidirectional circumstance as the most common situation.
**/

/** 
* The breadth-first search for the whole graph.
* start searching at vertex start.
* Time complexity: O(n+e)
**/
template<typename Tv,typename Te> template<typename VST> void Graph<Tv,Te>::bfs(int start,VST& visit)
{
    reset();
    int clock = 0;
    int v = start;
    do
    {
        if(status(v) == UNDISCOVERED)
            BFS(v,clock,visit);//use clock to mark touch time
        v++;
        v %= n;
    }while(start != v)//search all the vertex.
}

/**
* connected domain BFS.
**/
template<typename Tv,typename Te> template<typename VST> 
void Graph<Tv,Te>::BFS(int startV,int& clock,VST& visit)
{
    MyQueue<int> Q;//use a queue to mark discovered vertexes, so that the first discover vertex can be visited first.
    Q.Enqueue(startV);
    visit(startV);
    status(startV) = DISCOVERED;
    while(!Q.IsEmpty())
    {
        int ver = Q.Dequeue();      
        dTime(ver) = ++clock;//Notice the difference between VISITED and DISCOVERED
        for(int u=firstNbr(ver) ; u > -1 ; u=nextNbr(ver,u) )  //-1 if no neighbor left.
        {
            if(status(u) == UNDISCOVERED)
            {
                visit(u);
                status(u) = DISCOVERED;
                Q.Enqueue(u);
                type(ver,u) = TREE;
                parent(u) = ver;
            }
            else
                type(ver,u) = CROSS;
        }
        status(ver) = VISITED;//the vertex is VISITED after all its neighbor have been DISCOVERED
    }
}
   
/** 
* The depth-first search for the whole graph.
* start searching at vertex start
* Time complexity: O(n+e)
**/
template<typename Tv,typename Te> template<typename VST> void Graph<Tv,Te>::dfs(int start,VST& visit)
{
    reset();
    int v = start;
    int clock = 0;
    do
    {
        if(status(v) == UNDISCOVERED)
            DFS(v,clock,visit); 
        v++;
        v %= n;
    }while(start != v)
}

/**
* choose one of the DFS for the whole graph.
**/
template<typename Tv,typename Te> template<typename VST> void Graph<Tv,Te>::DFS(int start,int& clock,VST& visit)
{
    srand( (unsigned int)time(NULL) );
    switch(rand()%2)
    {
    case 1:
        DFS_Iter(start,clock,visit);
        break;
    case 2:
        DFS_Recur(start,clock,visit);
        break;
    default:
        break;
    }
}

/** 
* Recursive version of DFS
**/
template<typename Tv,typename Te> template<typename VST> void Graph<Tv,Te>::DFS_Recur(int start,int& clock,VST& visit)
{
    dTime(start) = ++clock;
    status(start) = DISCOVERED;
    visit(start);
    for(int u=firstNbr(start);u>-1;u=nextNbr(start,u))
    {
        if(status(u) == UNDISCOVERED)
        {
            type(start,u) = TREE;
            parent(u) = start;
            DFS_Recur(u,clock);//recursive call
        }
        else if(status(u) == DISCOVERED)//DISCOVERED not VISITED.
            type(start,u) = BACKWARD;        //so its parent vertex.
        else                                                      //VISITED
            type(start,u) = (dTime(start) < dTime(u) )? FORWARD:CROSS;//if FORWARD, start is u's parent. If CROSS , not in the same branch
    }
    fTime(start) = ++clock;             //active time: fTime - dTime
    status(start) = VISITED;
}

/**
* The iteration version of DFS
**/
#define nhb(x) fTime(x)             //use fTime to record the current neighbor.
template<typename Tv,typename Te> template<typename VST> void Graph<Tv,Te>::DFS_Iter(int start,int& clock,VST& visit)
{
    //In iteration version, a problem is to remember the current VISITED vertex.
    MyStack<int> S; //the LIFO structure better suits DFS
    S.Push(start);
    while(!S.IsEmpty())
    {
top: int v = S.Top();
        if(status(v) == UNDISCOVERED)
        {
            dTime(v) = ++clock;
            status(v) == DISCOVERED;
            visit(v);
            nhb(v) = n;
        }
        while(-1< (nhb(v)=nextNbr(v,nhb(v))) )      //search for neighbors
        {
            if(status(nhb(v)) == UNDISCOVERED)
            {
                S.Push(nhb(v));
                goto top;
            }
            else if(status(nhb(v)) == DISCOVERED)
                type(v,nhb(v)) = BACKWARD;
            else                                    //VISITED
                (dTime(v)<dTime(nhb(v)))? FORWARD:CROSS;
        }
        status(v) = VISITED;
        S.Pop();
    }
}
#undef nhb

/**
* Topo sort.
* Return the stack fill with the sequence of vertex.
* Return an empty stack if this graph contains ring of vertexes.
**/
template<typename Tv,typename Te> MyStack<Tv&>* Graph<Tv,Te>::tSort(int start)
{
    MyStack<Tv&>* S = new MyStack<Tv&>();//use a stack to record the sorted sequence
    reset();
    int clock = 0;
    int v = start;
    do
    {
        if(status(v) == UNDISCOVERED)
        {
            if(!TSort(v,clock,S)) //if contains ring, clear stack and return false
            {
                while(!S->IsEmpty())
                    S->Pop();
                break;
            }
        }
        v++;
        v %= n;
    }while(v != start)
    return S;         
}

/**
* Topo sort in connected domain based on DFS(Recursive version).
* The correnctness of this algorithm based on the fact that 
* all vertexes' descendants have been pushed into the stack before 
* the vertex itself.
* So, DFS fits the topo sort most.
**/
template<typename Tv,typename Te>
bool Graph<Tv,Te>::TSort(int start,int& clock,MyStack<Tv&>* S)
{
    status(start) = DISCOVERED;
    dTime(start) = ++clock;
    for(int u=firstNbr(start) ; u > -1 ; u=nextNbr(start,u) )//u for the index of vertex.
    {
        switch(status(u))
        {
        case UNDISCOVERED:
            type(start,u) = TREE;
            parent(u) = start;
            TSort(u,clock,S);
            break;
        case DISCOVERED:                    //this vertex is still active, then must be a ring!
            type(start,u) = BACKWARD;
            return false;
        default:
            type(start,u) = (dTime(start)<dTime(u))?FORWARD:CROSS;
            break;
        }
    }
    S->Push(vertex(start));
}

/**
* bi-connected component finding algorithm.
* The characteristics of BCC : if disconnected, its child vertexes can't connect  its ancestor.
**/
template<typename Tv,typename Te> template<typename VST> 
void Graph<Tv,Te>::bcc(int start)
{
    reset();
    int clock = 0;
    int v = start;
    MyStack<int> S;
    do
    {
        if(status(v) == UNDISCOVERED)
        {
            BBC(v,clock,S);
            S.Pop();//pop the redundant vertex.
        }
        v++;
        v %= n;
    }while(v != start)
}

/**
* Find bi-connected component in a connected domain.
**/
#define hca(x) fTime(x)         //use fTime to record highest connected ancestor.
template<typename Tv,typename Te> template<typename VST> 
void Graph<Tv,Te>::BCC(int v,int& clock,MyStack<int>& S)
{
    S.Push(v);
    hca(v) = dTime(v) = ++clock;
    status(v) = DISCOVERED;

    for(int u=firstNbr(v);u>-1;u=nextNbr(v,u))
    {
        switch(status(u))
        {
        case UNDISCOVERED:
            type(v,u) = TREE;
            parent(u) = v;
            BCC(u,clock,S);  
            if(hca(u)<dTime(v))
                hca(v) = min(hca(v),hca(u));//update v.
            else //this means the child u has connection to v's ancestor. so v is the bi-connected component.
            {
                while(v != S.Pop());// the poped out vertexes are not bcc.
                S.Push(v);
            }
            break;

        case DISCOVERED:
            type(v,u) = BACKWARD;
            if(u != parent(v))
                hca(v) = min(hca(v),dTime(u));
        default:        //VISITED
            type(v,u) = (dTime(v) < dTime(u)) ? FORWARD : CROSS;
            break;
        }
    }
    status(v) = VISITED;
}
#undef hca


/**
* The entrance of priority first search.
* start searching at index start.
**/
template<typename Tv,typename Te> template<typename VST> template <typename PU>
void Graph<Tv,Te>::pfs(int start,PU& prioOpt, VST& visit)
{
    reset();
    int v = start;
    do
    {
        if(status(v) == UNDISCOVERED)
            PFS(v,prioOpt,visit);
        v++;
        v %= n;
    }while(start != v)
}

/**
* Priority first search in connected domain.
* PU for priority updator.
* Time complexity: O(n^2) -- must look up all other vertexes in each vertex's active time.
* Notice:  0 stands for the largest priority, INT_MAX stands for the smallest priority.
**/
template<typename Tv,typename Te> template<typename VST> template <typename PU>
void Graph<Tv,Te>::PFS(int start,PU& prioOpt,VST& visit)
{
    priority(start) = 0;
    status(start) = VISITED;
    visit(start);
    parent(start) = -1;
    while(1)
    {
        for(int u=firstNbr(start);u>-1;u=nextNbr(start,u))//update neighbor's priority.
            prioOpt(this,start,u);

        for(int shortest=INT_MAX,u=0;u<n;u++) //find the largest priority
        {
            if(status(u) == UNDISCOVERED && priority(u)< shortest)
            {
                shortest = priority(u);
                start = u;//the start becomes the priority..
            }
        }
        if(status(start) == VISITED)//jump outta loop if all VISITED
            break;
        status(start) = VISITED;
        type(parent(start),start) = TREE;
    }
}

//updater for dijkstra algorithm.
template<typename Tv,typename Te>
class  DijkstraPO
{
public:
    virtual void operator()(Graph<Tv,Te>* G,int v,int u)//v for current vertex, u for vvertex to be updated.
    {
        if(G->status(u) == VISITED)
            return;
        if(G->weight(v,u) + G->priority(v) < G->priority(u))//if u has larger priority, update it.
        {
            G->priority(u) = G->weight(v,u) + G->priority(v);
            G->parent(u) = v;
        }
    }
};

/**
* build up a SPT using start vertex as root.
* Shortest path tree: all vertex has the shortest path to the root in this tree.
**/
template<typename Tv,typename Te> template<typename VST>
void Graph<Tv,Te>::dijkstra(int start, VST& visit)
{
    DijkstraPO<Tv,Te> dijkstra;
    pfs(start,dijkstra,visit);
}

//Priority updator used to find minimum spanning tree.(Prim algorithm)
template<typename Tv,typename Te>
class primPO 
{
    virtual void operator()(Graph<Tv,Te>* G,int v,int u)
    {
        if(G->status(u) == VISITED) 
            return;
        if(G->weight(v,u) < G->priority(u))
        {
            G->priority(u) = G->weight(v,u);//the only difference between this updater and SPT updater:
            G->parent(u) = v;                          //this updater focus on the the current weight of edges, not the summary weight of the whole path.
        }                                                         //in some way just like greedy algorithm
    }
};

/**
* Build up minimum spanning tree using MST as root.
**/
template<typename Tv,typename Te> template<typename VST>
void Graph<Tv,Te>::prim(int start,VST& visit)
{
    primPO<Tv,Te> prim;
    pfs(start,prim,visit);
}

ALGORITHM_END

#endif //GRAPHIMP_H