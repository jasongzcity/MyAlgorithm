/**
* AdjacencyMatrix.h 
* Use a Graph to define a AdjacencyMatrix.
* Last modified on: 2016/10/21
* Author:lwz
**/

#ifndef ADJACENCYMATRIX_H
#define ADJACENCYMATRIX_H

#include "./Graph.h"
#include "./GraphImp.h"
#include <climits>
#include "./MyVector.h"
#include "./MyAlgorithm.h"

ALGORITHM_BEGIN

/**
* Use two structs to store vertex and edge information.
**/
template<typename Tv>
class Vertex
{
public:
    Tv Data;
    int InDegree,OutDegree;
    int DTime,FTime;
    int Parent;
    int Priority;
    VStatus Status;

    Vertex(Tv const& d  = (Tv)0)
        :Data(d),InDegree(0),OutDegree(0),DTime(-1),FTime(-1),
        Parent(-1),Priority(INT_MAX),Status(UNDISCOVERED){}

    Vertex<Tv>& operator=(const Vertex<Tv>& v)
    {
        Data = v.Data;
        InDegree = v.InDegree;
        OutDegree = v.OutDegree;
        DTime = v.DTime;
        FTime = v.FTime;
        Parent = v.Parent;
        Priority = v.Priority;
        Status = v.Status;
        return (*this);
    }
};

template <typename Te>
class Edge
{
public:
    Te Data;
    int Weight;
    EType Type;

    Edge(Te const& e,int w)
        :Data(e),Weight(w),Type(UNDETERMINED){}

    Edge<Te>& operator=(const Edge<Te>& e)
    {
        Te = e.Data;
        Weight = e.Weight;
        EType = e.Type;
        return (*this);
    }
};

/**
* Adjacency Matrix.
* Tv for datatype in vertex.
* Te for datatype in edge.
**/
template<typename Tv,typename Te>
class GraphMatrix: public Graph<Tv,Te> 
{   
private:
    MyVector< Vertex< Tv > > V;                          //Vertex set
    MyVector< MyVector< Edge< Te > * > > E;   //Edge set. Notice it stores pointers
    int EdgeSize;
    int VertexSize;

public:
    GraphMatrix()
    {
        EdgeSize = VertexSize = 0;
    }

    virtual ~GraphMatrix()
    {
        for(int i=0;i<VertexSize;i++)
        {
            for(int j=0;j<VertexSize;j++)
                delete E[i][j];//free edges
        }
    }

    virtual Tv& Vertex(int i)
    {
        return V[i].Data;
    }
    
    virtual int InDegree(int i)
    {
        return V[i].InDegree;
    }

    virtual int OutDegree(int i)
    {
        return V[i].OutDegree;
    }

    virtual int FirstNbhd(int i)
    {
        return NextNbhd(i,VertexSize);
    }

    virtual int NextNbhd(int i,int j)
    {
        while((-1<j) && (!Exist(i,--j)));//check neighbor.
        return j;
    }

    virtual VStatus& Status(int i)
    {
        return V[i].Status;
    }

    virtual int& DTime(int i)
    {
        return V[i].DTime;
    }

    virtual int& FTime(int i)
    {
        return V[i].FTime;
    }

    virtual int& Parent(int i)
    {
        return V[i].Parent;
    }

    virtual int& Priority(int i)
    {
        return V[i].Priority;
    }

    //=======Vertex operations=======//

    /**
    * Insert a vertex.
    * Return the rank of the vertex in the vector.
    **/
    virtual Rank Insert(Tv const& data)
    {
        for(int i=0;i<VertexSize;i++)
            E[i].Insert(NULL,VertexSize);//+1 position in other vector
        VertexSize++;
        E.Insert(MyVector<Edge<Te>*>(VertexSize,VertexSize,(Edge<Te>*)NULL));//+1 vector in the matrix
        return V.Insert(Vertex<Tv>(data));
    }

    /**
    * Remove vertex i.
    * Return the data of the vertex.
    **/
    virtual Tv Remove(int i)
    {
        for(int j=0;j<VertexSize;j++)//delete edges.
        {
            if(Exist(i,j))
            {
                delete E[i][j];
                V[j].InDegree--;
            }
        }
        E.Remove(i);
        VertexSize--;
        Tv VBack = Vertex(i);
        V.Remove(i);
        for(int j=0;j<VertexSize;j++)//delete all edges points to i
        {
            if(Edge<Te>* p = E[j].Remove(i))
            {
                delete p;
                V[j].OutDegree--;
            }
        }
        return VBack;
    }

    //=======Edge operations=======//
    /*
    * All parameters means the edge from i points to j
    */

    /**
    * Judge if edge from i to j exists.
    **/
    virtual bool Exist(int i,int j)
    {
        return ((i>=0) && (i<VertexSize) && (j>=0) && (j<VertexSize) && (E[i][j]!=NULL));
    }

    virtual EType& Type(int i,int j)
    {
        return E[i][j]->Type;
    }

    virtual Te& Edge(int i,int j)
    {
        return E[i][j]->Data;
    }

    virtual int& Weight(int i,int j)
    {
        return E[i][j]->Weight;
    }

    /**
    * Insert edge from i to j, with data and weight of w
    **/
    virtual void Insert(Te const& data,int i,int j,int w)
    {
        if(Exist(i,j))
            return;
        E[i][j] = new Edge<Te>(data,w);
        V[i].OutDegree++;
        V[j].InDegree++;
        EdgeSize++;
    }

    /**
    * Remove the edge from i to j.
    **/
    virtual Te Remove(int i,int j)
    {
        Te EBack = Edge(i,j);
        delete E[i][j];
        //E[i].Remove[j];               //save the position!!!!
        E[i][j] = NULL;
        V[i].OutDegree--;
        V[j].OutDegree--;
        EdgeSize--;
        return EBack;
    }
};

ALGORITHM_END

#endif //ADJACENCYMATRIX_H  