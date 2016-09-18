/* Created on 2016-1-4 16:42:56  Author:LWZ */

#include "SGraph.h"
#ifndef _INC_LIMITS
#include "limits.h"
#endif

//#ifndef ADT_VECTOR_H
//#include "ADT_Vector.h"
//#endif
#include "vector.h"

template<typename Tv>				//这里要求调用者提前想好边类型和顶点类型,来生成具体的类
struct Vertex
{
//	friend class Graph<Tv,Te>;		//图类有所有访问权
	Tv Data;
	int InDegree,OutDegree;
	int DTime,FTime;
	int Parent;
	int Priority;
	VStatus Status;

//public:
	Vertex(Tv const& d  = (Tv)0)	//若无参数默认Tv为0
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
};//class Vertex

template <typename Te>
struct Edge
{
//	friend class Graph<Tv,Te>;		//开放访问权给图类
//public:
	//数据区
	Te Data;
	int Weight;
	EType Type;

//public:
	Edge(Te const& e,int w)
		:Data(e),Weight(w),Type(UNDETERMINED){}

	Edge<Te>& operator=(const Edge<Te>& e)
	{
		Te = e.Data;
		Weight = e.Weight;
		EType = e.Type;
		return (*this);
	}
};//class Edge


/******************  
 *   邻接矩阵
*******************/
template<typename Tv,typename Te>
class GraphMatrix: public Graph<Tv,Te>		//图矩阵,由两个向量构成的边集和一个向量承载的顶点集构成
{	
private:
	//SVector< Vertex< Tv > > V;				//顶点集（向量）
	//SVector< SVector< Edge< Te > * > > E;		//边集（邻接矩阵）
	//Vector< Vertex< Tv > > V; //顶点集（向量）
	//Vector< Vector< Edge< Te > * > > E; //边集（邻接矩阵）

public:
	GraphMatrix()
	{
		ENum = VNum = 0;
	}
	virtual ~GraphMatrix()
	{
		for(int i=0;i<VNum;i++)
		{
			for(int j=0;j<VNum;j++)
			{
				delete E[i][j];			//逐个删除边
			}
		}
	}

	//顶点基本操作:查询

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
		return NextNbhd(i,VNum);
	}

	virtual int NextNbhd(int i,int j)
	{
		while((-1<j) && (!Exist(i,--j)));		//从最大的VNum开始向下减当第一个存在的边则返回.若该顶点无邻居则返回-1
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
		return V[i].Parent;			//遍历树中的父亲
	}

	virtual int& Priority(int i)
	{
		return V[i].Priority;
	}

	//顶点的动态操作
	virtual int Insert(Tv const& data)		//插入操作,需要在邻接矩阵中加
	{
		for(int i=0;i<VNum;i++)
		{
			E[i].Insert(NULL,VNum);			//可能需要重载运算符=
		}
		VNum++;
		E.Insert(SVector<Edge<Te>*>(VNum,VNum,(Edge<Te>*)NULL));	//......
		return V.Insert(Vertex<Tv>(data));			//返回该顶点在向量中的秩
	}

	virtual Tv Remove(int i)				//释放矩阵中空间,以及矩阵存储的指针指向的堆空间
	{
		for(int j=0;j<VNum;j++)
		{
			if(Exist(i,j))
			{
				delete E[i][j];				//删除出边
				V[j].InDegree--;
			}
		}
		E.Remove(i);
		VNum--;
		Tv VBack = Vertex(i);				//复制顶点i信息,Tv类型需重载操作符=
		V.Remove(i);
		for(int j=0;j<VNum;j++)
		{
			if(Edge<Te>* p = E[j].Remove(i))			//若p不为Null(因为一开始全部设置为NULL)
			{
				delete p;
				V[j].OutDegree--;
			}
		}
		return VBack;
	}

	//边线性操作
	virtual bool Exist(int i,int j)
	{
		return ((i>=0) && (i<VNum) && (j>=0) && (j<VNum) && (E[i][j]!=NULL));
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

	//边的动态操作
	virtual void Insert(Te const& data,int i,int j,int w)	//用运算符new在堆中创建结构体,在放入向量矩阵中
	{
		if(Exist(i,j))		//确保不存在
		{
			return;
		}
		E[i][j] = new Edge<Te>(data,w);			//创建结构体,循秩放入向量中
		V[i].OutDegree++;
		V[j].InDegree++;
		ENum++;
	}

	virtual Te Remove(int i,int j)					//删除边(i,j)
	{
		Te EBack = Edge(i,j);			//备份边中数据
		delete E[i][j];
		//E[i].Remove[j];				//留意,顶点还在!
		E[i][j] = NULL;
		V[i].OutDegree--;
		V[j].OutDegree--;
		ENum--;
		return EBack;
	}
};//class GraphMatrix