/**
* Graph.h
* Abstract class for graph
* Last modified on: 2016/10/19
* Author:lwz
**/

#ifndef GRAPH_H
#define GRAPH_H

#include "./MyStack.h"
#include "./MyAlgorithm.h"

ALGORITHM_BEGIN

typedef enum { UNDISCOVERED, DISCOVERED, VISITED } VStatus;  // the vertex status
typedef enum { UNDETERMINED, TREE, CROSS, FORWARD, BACKWARD } EType; //the edge type 

/**
* The graph template.
* Tv the type of vertex, Te type of edge.
* Make it an abstract class.
* User should define the visit methods for vertexes and edges.
**/
template <typename Tv, typename Te>
class Graph
{                               
private:
    //reset all vertex & edge status.
   void reset()  
   {
      for ( int i = 0; i < n; i++ ) 
      {
         status(i) = UNDISCOVERED;
         dTime(i) = fTime (i) = -1; 
         parent(i) = -1; 
         priority(i) = INT_MAX; 
         for(int j = 0; j < n; j++)
         {
            if(exists(i, j))
                type(i, j) = UNDETERMINED; 
         }
      }
   }
   //====connected domain methods====//
   //VST is the object function for traverse.
   //breadth first search
   template<typename VST> void BFS(int,int&,VST&);
   //depth first search
   template<typename VST> void DFS(int,int&,VST&);
   //bi-connected component 
   void BCC(int,int&,MyStack<int>&);
   //Topo sort based on dfs
   bool TSort( int,int&,MyStack<Tv&>*);
   //priority first search, PU for priority updater.
   template<typename VST> template <typename PU> void PFS(int,PU,VST&);
   //DFS recusive version.
   template<typename VST> void DFS_Recur(int,int&,VST&);
   //DFS iteration version.
   template<typename VST> void DFS_Iter(int,int&,VST&);

public:
    //operation on vertexes

   int n;//the number of vertexes
   virtual int insert ( Tv const& ) = 0;//insert the vertex and return the number of the vertex
   virtual Tv remove ( int ) = 0;          //remove the vertex
   virtual Tv& vertex ( int ) = 0;         //return the reference of vertex 
   virtual int inDegree ( int ) = 0;       // the in & out degree of the vertex.
   virtual int outDegree ( int ) = 0;  
   virtual int firstNbr ( int ) = 0;        //return the first neighbor of vertex
   virtual int nextNbr ( int, int ) = 0;  //return the next neighbor of the vertex
   virtual VStatus& status ( int ) = 0; //return the vertex's status.
   virtual int& dTime ( int ) = 0;         //the reference of dTime tag
   virtual int& fTime ( int ) = 0;         //the reference of fTime tag
   virtual int& parent ( int ) = 0;         //the reference of the vertex parent
   virtual int& priority ( int ) = 0;      //the renference of the vertex's priority

   //operations on edges
   int e;                                           //number of edges
   virtual bool exists ( int, int ) = 0;            //judge if edge exists.
   virtual void insert ( Te const&, int, int, int ) = 0;  
   virtual Te remove ( int, int ) = 0;           //remove edge between 2 vertexes.
   virtual EType & type ( int, int ) = 0;      //return the type of edge between 2 vertexes.
   virtual Te& edge ( int, int ) = 0;             //return the edge between 2 vertexes.
   virtual int& weight ( int, int ) = 0;         //return the weight of edge between 2 vertexes.

   //the search algorithms for graph
   template<typename VST> void bfs ( int ,VST&);
   template<typename VST> void dfs ( int ,VST&);
   void bcc ( int );
   MyStack<Tv&>* tSort ( int );
   template<typename VST> void prim ( int ,VST&); //build up minimum spanning tree
   template<typename VST> void dijkstra ( int ,VST&);
   template<typename VST> template <typename PU> void pfs ( int, PU& ,VST& );
};

ALGORITHM_END

#endif //GRAPH_H