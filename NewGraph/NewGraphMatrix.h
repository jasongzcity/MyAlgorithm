/* Created on 2016-1-4 21:46:31 Author:LWZ   */
#include "graph.h"
#ifndef ADT_VECTOR_H
#include "ADT_Vector.h"
#endif


////顶点和边的结构体模板////////////
//注意:dTime一般表示发现时间,fTime表示"离开"时间
template <typename Tv> struct Vertex { //顶点对象（为简化起见，并未严格封装）
   Tv data; int inDegree, outDegree; VStatus status; //数据、出入度数、状态
   int dTime, fTime; //时间标签
   int parent; int priority; //在遍历树中的父节点、优先级数
   Vertex ( Tv const& d = ( Tv ) 0 ) : //构造新顶点
      data ( d ), inDegree ( 0 ), outDegree ( 0 ), status ( UNDISCOVERED ),
      dTime ( -1 ), fTime ( -1 ), parent ( -1 ), priority ( INT_MAX ) {} //暂不考虑权重溢出
};

template <typename Te> struct Edge { //边对象（为简化起见，并未严格封装）
   Te data; int weight; EType type; //数据、权重、类型
   Edge ( Te const& d, int w ) : data ( d ), weight ( w ), type ( UNDETERMINED ) {} //构造
};
//////////////////////////////////////////////////////////////////////////////


/////////////////////邻接矩阵模板////////////////////////////////
template<typename Tv,typename Te>
class GraphMatrix: public Graph<Tv,Te>			//图矩阵,由两个向量构成的边集和一个向量承载的顶点集构成
{	
private:

	SVector< Vertex< Tv > > V;					//顶点集（向量）
	SVector< SVector< Edge< Te > * > > E;		//边集（邻接矩阵）

public:
	GraphMatrix()
	{
		n = e = 0;
	}
	virtual ~GraphMatrix()
	{
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				delete E[i][j];			//逐个删除边
			}
		}
	}

	//顶点基本操作:查询

	virtual Tv& vertex(int i)
	{
		return V[i].data;
	}
	
	virtual int inDegree(int i)
	{
		return V[i].inDegree;
	}

	virtual int outDegree(int i)
	{
		return V[i].outDegree;
	}

	virtual int firstNbr(int i)
	{
		return nextNbr(i,n);
	}

	virtual int nextNbr(int i,int j)			//对顶点i来说,j是下一个邻接顶点
	{
		while((-1<j) && (!exists(i,--j)));		//从最大的VNum开始向下减当第一个存在的边则返回.若该顶点无邻居则返回-1
		return j;
	}

	virtual VStatus& status(int i)
	{
		return V[i].status;
	}

	virtual int& dTime(int i)
	{
		return V[i].dTime;
	}

	virtual int& fTime(int i)
	{
		return V[i].fTime;
	}

	virtual int& parent(int i)
	{
		return V[i].parent;			//遍历树中的父亲
	}

	virtual int& priority(int i)
	{
		return V[i].priority;
	}

	//顶点的动态操作
	virtual int insert(Tv const& data)		//插入操作,需要在邻接矩阵中加
	{
		for(int i=0;i<n;i++)
		{
			E[i].Insert(NULL,n);			//可能需要重载运算符=
		}
		n++;
		E.Insert(SVector<Edge<Te>*>(n,n,(Edge<Te>*)NULL));	//......
		return V.Insert(Vertex<Tv>(data));			//返回该顶点在向量中的秩
	}

	virtual Tv remove(int i)				//释放矩阵中空间,以及矩阵存储的指针指向的堆空间
	{
		for(int j=0;j<n;j++)
		{
			if(exists(i,j))
			{
				delete E[i][j];				//删除出边
				V[j].inDegree--;
			}
		}
		E.Remove(i);
		n--;
		Tv VBack = vertex(i);				//复制顶点i信息,Tv类型需重载操作符=
		V.Remove(i);
		for(int j=0;j<n;j++)
		{
			if(Edge<Te>* p = E[j].Remove(i))			//若p不为Null(因为一开始全部设置为NULL)
			{
				delete p;
				V[j].outDegree--;
			}
		}
		return VBack;
	}

	//边线性操作
	virtual bool exists(int i,int j)
	{
		return ((i>=0) && (i<n) && (j>=0) && (j<n) && (E[i][j]!=NULL));
	}

	virtual EType& type(int i,int j)
	{
		return E[i][j]->type;
	}

	virtual Te& edge(int i,int j)
	{
		return E[i][j]->data;
	}

	virtual int& weight(int i,int j)
	{
		return E[i][j]->weight;
	}

	//边的动态操作
	virtual void insert(Te const& data,int i,int j,int w)	//用运算符new在堆中创建结构体,在放入向量矩阵中
	{
		if(exists(i,j))		//确保不存在
		{
			return;
		}
		E[i][j] = new Edge<Te>(data,w);			//创建结构体,循秩放入向量中
		V[i].outDegree++;
		V[j].inDegree++;
		e++;
	}

	virtual Te remove(int i,int j)					//删除边(i,j)
	{
		Te EBack = edge(i,j);			//备份边中数据
		delete E[i][j];
		//E[i].Remove[j];				//留意,顶点还在!
		E[i][j] = NULL;
		V[i].outDegree--;
		V[j].outDegree--;
		e--;
		return EBack;
	}	
};//class GraphMatrix

#include "GraphImp.h"					//图类中搜索算法需使用图矩阵中实现的查询操作