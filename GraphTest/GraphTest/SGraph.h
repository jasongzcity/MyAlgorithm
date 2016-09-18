/* Created on 2015-12-17 09:56:04 Author: LWZ 
   This file contains templates for graph and graph matrix.
*/

#define SGRAPH_H
//#include "ASonStack.h"

typedef enum
{
	UNDISCOVERED,
	DISCOVERED,
	VISITED
}VStatus;//顶点状态

typedef enum
{
	UNDETERMINED,
	TREE,
	CROSS,
	FORWARD,
	BACKWARD
}EType;//边在遍历树中的类型

template<typename Tv,typename Te>	//边类型,顶点类型		抽象模板类类似于接口协议,有一些接口必须实现,才能够具体化和实例化
class Graph							//这是抽象模板类,用邻接表或邻接矩阵来具体实现
{
public:
	int ENum;		//边数
	int VNum;		//顶点个数
private:
	void reset()
	{
		for(int i=0;i<VNum;i++)
		{
			Status(i) = UNDISCOVERED;
			FTime(i) = DTime(i) = -1;
			Parent(i) = -1;
			Priority(i) = INT_MAX;
			for(int j=0;j<VNum;j++)
			{
				if(Exist(i,j))		//若边(i,j)存在
				{
					Type(i,j) = UNDETERMINED;
				}
			}//for
		}//for
	}

	//void bfs(int,int&);					//联通域的内部的广度优先遍历算法(若多个连通域则会构成森林)
	//void dfs(int,int&);					//连通域的内部的深度优先遍历算法
	//void bcc(int,int&,SStack<int>&);	//连通域内部的基于DFS的双连通分量分解算法
	//bool tsort(int,int&,SStack<Tv>*);	//连通域内部基于DFS的拓扑排序算法

	//template<typename PU>void pfs(int,PU);	//连通域内优先级搜索框架

public:
	//顶点部分方法
	virtual int Insert(Tv const&) = 0;		//插入顶点,返回编号
	virtual Tv Remove(int) = 0;				//删除顶点及其关联边,返回顶点信息
	virtual Tv& Vertex(int) = 0;			//返回顶点的引用(可直接修改)
	virtual int InDegree(int) = 0;			//返回该顶点的入度
	virtual int OutDegree(int) = 0;			//返回顶点的出度
	virtual int FirstNbhd(int) = 0;			//返回顶点的首个邻接顶点的编号(而非引用)
	virtual int NextNbhd(int i,int j) = 0;	//顶点i相对于顶点j的下一邻接顶点
	virtual VStatus& Status(int) = 0;		//返回该顶点状态的引用(可直接修改)
	virtual int& DTime(int) = 0;			//返回顶点的时间标签的引用
	virtual int& FTime(int) = 0;			//返回顶点的时间标签的引用
	virtual int& Parent(int) = 0;			//返回顶点在遍历树中的父亲
	virtual int& Priority(int) = 0;			//返回顶点在遍历树中的优先级

	//边部分方法,约定是有向图
	virtual bool Exist(int,int) = 0;		//判断边i->j是否存在
	virtual void Insert(Te const&,int,int,int) = 0;	//将边e插入到顶点i,j之间并赋予权重w
	virtual Te Remove(int,int) = 0;			//删除顶点间的边
	virtual EType& Type(int,int) = 0;		//返回边的类型的引用
	virtual Te& Edge(int,int) = 0;			//返回边的引用(可直接修改内部数据)
	virtual int& Weight(int,int) = 0;		//返回边的权重的引用

	//对外算法接口
	//void BFS(int);			//广度优先算法
	//void DFS(int);			//深度优先算法
	//void BCC(int);			//基于DFS的双连通分量分解算法!
	//SStack<Tv>* TSort(int);	//基于DFS的拓扑排序算法
	//void Prim(int);			//最小支撑树Prim算法
	//void Dijkstra(int);		//最短路径Dijkstra算法

	//template<typename PU>
	//void PFS(int,PU);		//优先级搜索框架
};//class Graph



	

