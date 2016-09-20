/* Created on 2016-1-5 15:25:33 by LWZ. The impletation of class Graph */

#ifndef DERIVEDQUEUE_H
#include "DerivedQueue.h" 
#endif
#include "stdlib.h"				//for rand()
#include "time.h"				//for srand()

template<typename Tv,typename Te>
void Graph<Tv,Te>::bfs(int start)					//全图 广度优先算法(公有方法)
{
	reset();			//重置所有参数
	int clock = 0;
	int v = start;		//从start号顶点开始搜索
	do
	{
		if(status(v) == UNDISCOVERED)
		{
			BFS(v,clock);		//单连通域内广度优先搜索
		}						//这样每个顶点都有自己的访问时间
		v++;
		v %= n;
	}while(start != v)
}

template<typename Tv,typename Te>
void Graph<Tv,Te>::BFS(int startV,int& clock)		//单连通域 广度优先算法(私有方法)
{
	SQueue<int> Q;
	Q.Enqueue(startV);
	status(startV) = DISCOVERED;	//将首顶点设为已发现
	while(!Q.IsEmpty())				//非空时
	{
		int ver = Q.Dequeue();		
		dTime(ver) = ++clock;		//记录访问时间 - 留意区分"访问"和"发现"的区别:发现 仅仅是放入队列中
		for(int u=firstNbr(ver);u>-1;u=nextNbr(ver,u))	//根据邻居搜索方法,返回-1时无邻居,从大到小搜索邻居
		{												//第二个参数表示从第几个开始搜索
			if(status(u) == UNDISCOVERED)				//尚未被发现
			{
				status(u) = DISCOVERED;
				Q.Enqueue(u);
				type(ver,u) = TREE;						//留意参数的先后是不一样的,尤其在有向图中.此类首次到访的边属树边
				parent(u) = ver;
			}
			else										//已经到访过了
			{
				type(ver,u) = CROSS;					//那么他们的边属跨边
			}
		}												//搜索所有邻居完毕
		status(ver) = VISITED;							//且已弹出队列,操作完毕,算"已访问"
	}//while
}
	
template<typename Tv,typename Te>
void Graph<Tv,Te>::dfs(int start)						//从顶点start开始进行全图深度优先搜索(公有接口)
{
	reset();				//重置
	int v = start;
	int clock = 0;
	do
	{
		if(status(v) == UNDISCOVERED)
		{
			DFS(v,clock);		//单连通域内广度优先搜索
		}						//这样每个顶点都有自己的访问时间
		v++;
		v %= n;
	}while(start != v)
}

template<typename Tv,typename Te>
void Graph<Tv,Te>::DFS(int start,int& clock)
{
	srand( (unsigned int)time(NULL) );		//以时间做种子
	switch(rand()%2)						//二选一选取一种版本
	{
	case 1:
		DFS_Iter(start,clock);
		break;
	case 2:
		DFS_Recur(start,clock);
		break;
	default:
		break;
	}
}

template<typename Tv,typename Te>
void Graph<Tv,Te>::DFS_Recur(int start,int& clock)			//连通域内深度优先搜索(递归版)
{
	dTime(start) = ++clock;									//留意:clock>0
	status(start) = DISCOVERED;
	for(int u=firstNbr(start);u>-1;u=nextNbr(start,u))
	{
		if(status(u) == UNDISCOVERED)						//发现未曾发现的顶点
		{
			type(start,u) = TREE;		//树边
			parent(u) = start;			//记录父顶点
			DFS_Recur(u,clock);			//记录完边关系后进行更深一层递归
		}
		else if(status(u) == DISCOVERED)					//已发现但未访问的节点
		{
			type(start,u) = BACKWARD;	//该节点还未处于还未访问结束状态,故必为祖先的节点
		}
		else							//顶点状态是已访问结束VISITED
		{
			type(start,u) = (dTime(start) < dTime(u) )? FORWARD:CROSS;	//前向边或跨边
		}
	}
	fTime(start) = ++clock;				//记录访问结束时间,每个顶点活跃时间是fTime - dTime
	status(start) = VISITED;
}

#define nhb(x) fTime(x)				//借用fTime来存储当前访问到第几个邻居
template<typename Tv,typename Te>
void Graph<Tv,Te>::DFS_Iter(int start,int& clock)			//单连通域深度优先搜索算法(迭代版)
{
	//迭代版是作业,借助栈来实现								//迭代版较麻烦在于记录已经访问到第几个节点
	SStack<int> S;
	S.Push(start);
	while(!S.IsEmpty())
	{
top:	int v = S.Top();
		
		if(status(v) == UNDISCOVERED)
		{
			dTime(v) = ++clock;
			status(v) == DISCOVERED;
			nhb(v) = n;
		}
		while(-1< (nhb(v)=nextNbr(v,nhb(v))) )		//搜索邻居		此处可能写错!
		{
			if(status(nhb(v)) == UNDISCOVERED)		//将该未发现邻居入栈
			{
				S.Push(nhb(v));
				goto top;
			}
			else if(status(nhb(v)) == DISCOVERED)
			{
				type(v,nhb(v)) = BACKWARD;
			}	
			else									//VISITED
			{
				(dTime(v)<dTime(nhb(v)))? FORWARD:CROSS;	//根据访问时间判断
			}
		}//while(搜索邻居)
		status(v) = VISITED;
		S.Pop();					//访问过所有的邻居,弹出
	}//while(!S.IsEmpty())
}
#undef nhb		//解除该宏定义

template<typename Tv,typename Te>
SStack<Tv>* Graph<Tv,Te>::tSort(int start)			//全图拓扑排序(公有接口)
{
	SStack<Tv>* S = new SStack<Tv>;					//默认构造方法
	reset();
	int clock = 0;
	int v = start;
	do
	{
		if(status(v) == UNDISCOVERED)
		{
			if(!TSort(v,clock,S))		//若排序不成功(不是有向无环图)
			{
				while(!S->IsEmpty())
				{
					S->Pop();			//清空栈
				}
				break;
			}
		}
		v++;
		v %= n;
	}while(v != start)
	return S;			//若排序不成功返回空栈
}

template<typename Tv,typename Te>
bool Graph<Tv,Te>::TSort(int start,int& clock,SStack<Tv>* S)			//连通域内基于DFS的拓扑排序算法(递归)
{																		//该拓扑排序算法正确性建立在:只有所有的后代都入栈之后再入栈,即可保证不会
	status(start) = DISCOVERED;											//有后代排在前面
	dTime(start) = ++clock;
	for(int u=firstNbr(start);u>-1;u=nextNbr(start,u))
	{
		switch(status(u))
		{
		case UNDISCOVERED:
			//处理边关系
			type(start,u) = TREE;
			parent(u) = start;
			TSort(u,clock,S);
			break;
		case DISCOVERED:
			//形成环
			type(start,u) = BACKWARD;
			return false;			//拓扑排序必须是有向无环图
		default:
			type(start,u) = (dTime(start)<dTime(u))?FORWARD:CROSS;
			break;
		}
	}
	S->Push(vertex(start));			//注意这是由顶点内数据类型元素构成的向量而不是顶点序号
}

template<typename Tv,typename Te>
void Graph<Tv,Te>::bcc(int start)				//全图范围的双连通域分解,以递归DFS为主要框架(双向图的情况)
{
	reset();
	int clock = 0;
	int v = start;
	SStack<int> S;					//辅助栈
	do
	{
		if(status(v) == UNDISCOVERED)
		{
			BBC(v,clock,S);
			S.Pop();				//因其中有Push会将该连通域的起点重新入栈
		}							//在此连通域结束时需将起点弹出
		v++;
		v %= n;
	}while(v != start)
}

#define hca(x) fTime(x)			//用fTime代为记录最高触及祖先
template<typename Tv,typename Te>
void Graph<Tv,Te>::BCC(int v,int& clock,SStack<int>& S)		//(连通域)基于DFS的双连通分量分解算法(内部接口)
{
	S.Push(v);						//入栈
	hca(v) = dTime(v) = ++clock;	//记录到访时间和当前最高祖先(自己)
	status(v) = DISCOVERED;
	//顶点操作结束,下一步寻找邻居

	for(int u=firstNbr(v);u>-1;u=nextNbr(v,u))
	{
		switch(status(u))
		{
		case UNDISCOVERED:
			type(v,u) = TREE;
			parent(u) = v;
			BCC(u,clock,S);
			//递归更新完高度后检查!
			//if(hca(v)>hca(u))		//这个判断是错误的!是要比有没有指向比v更高的祖先而不是比谁指的高!	
			if(hca(u)<dTime(v))		//这个判断才说明了指向了v的祖先
			{						//故v也要更新高度
				hca(v) = min(hca(v),hca(u));	//考虑其中一种情况就是v指向了一个非常高的祖先,即hca(v)非常小
			}									//那么此时它的高度是不需要随u变化的,但此时判断if(hca(v)>hca(u))就是错误的
			else								//此时hca(u)>=dTime(v)(是有可能等于的)从u的子孙绕一圈回来,但这不妨碍v成为关节点
			{									//故它需要分割.v此时就是关节点!
				while(v != S.Pop());//此处可将栈内转至所需容器中
				S.Push(v);			//最后一个弹出的v同样在其他子连通域中,压回
			}
			break;

		case DISCOVERED:
			type(v,u) = BACKWARD;
			if(u != parent(v))		//双向图中必须排除邻居是父亲的情况下,有指向更高的祖先
			{
				hca(v) = min(hca(v),dTime(u));		//更新高度
			}
		default:		//VISITED不可能出现在无向图中
			break;
		}//switch
	}//for
	status(v) = VISITED;
}
#undef hca

template<typename Tv,typename Te> template <typename PU>	//这里编译器会根据调用情况按照模板隐式生成对应函数实例
void Graph<Tv,Te>::pfs(int start,PU prioOpt)				//全图优先级搜索对外框架,prioOpt是更新顶点优先级的操作器
{
	reset();				//重置
	int v = start;
	//int clock = 0;		//可以不用clock,所有访问顺序已经根据优先级来决定了
	do
	{
		if(status(v) == UNDISCOVERED)
		{
			PFS(v,prioOpt);		//单连通域内广度优先搜索
		}						//这样每个顶点都有自己的访问时间
		v++;
		v %= n;
	}while(start != v)
}

template<typename Tv,typename Te> template <typename PU>	//单个连通域内的优先级搜索,复杂度是O(n^2).前述方法都是O(n+e)边和顶点的和 
void Graph<Tv,Te>::PFS(int start,PU prioOpt)				//由于后面方法要和边的权挂钩,规定:优先级值越小 优先级越高
{
	priority(start) = 0;		//起始点start已经放入树中,他优先级最高
	status(start) = VISITED;
	parent(start) = -1;			//表示无祖先
	while(1)
	{
		//更新当前顶点的邻居的优先级
		for(int u=firstNbr(start);u>-1;u=nextNbr(start,u))
		{
			prioOpt(this,start,u);			//将start及附近的邻居逐个更新.this是为了指向当前的图(操作器不属于图类)
		}
		for(int shortest=INT_MAX,u=0;u<n;u++)		//注意,这里是全图扫描最高优先级且未访问过的顶点 - 改进:只搜索已经更新过的邻居
		{											//而不是在start的邻居中扫描.变量shortest为了记录当前最高优先级
			if(status(u) == UNDISCOVERED && priority(u)< shortest)	//u未发现且优先级最高
			{
				shortest = priority(u);
				start = u;					//更新下一个准备访问的点
			}
		}
		if(status(start) == VISITED)	//全连通域访问过一遍了
		{
			break;						//跳出while(1)循环
		}
		status(start) = VISITED;
		type(parent(start),start) = TREE;	//是为树边 在操作器中更新了父顶点
	}//while(1)
}

///////////////////////////prim算法构造最小支撑树/////////////////////////////////
//prim算法的优先级操作器 prim算法用于查找minimum spanning tree(MST)最小支撑树
//根据已访问及未访问顶点间的"桥梁"更新优先级,其目的是找到权重和最小的树
template<typename Tv,typename Te>
class primPO
{
public:
	virtual void operator()(Graph<Tv,Te>* G,int v,int u)		//v是访问完毕节点,u是搜索到的邻居
	{
		if(G->status(u) == VISITED)		//该算法中不存在 DISCOVERED的情况,不予考虑
		{
			return;			//访问过的则无需更新u的优先级,反正也用不上
		}
		if(G->weight(v,u) + G->priority(v) < G->priority(u))	//若路径和小于原来u的优先级	
		{														//则u的优先级需要更新
			G->priority(u) = G->weight(v,u) + G->priority(v);
			G->parent(u) = v;			//同时更新父顶点
		}
	}
};//class primPO

template<typename Tv,typename Te> 
void Graph<Tv,Te>::prim(int start)						//用prim算法构造MST的公有接口
{
	pfs(start,primPO);
}
//////////////////////////////////////////////////////////////////////////////////////

///////////////////////////Dijkstra算法构造最短路径树/////////////////////////////////
template<typename Tv,typename Te>
class DijkstraPO										//唯一不用的是此时priority内记录的是该顶点到初始顶点间的最短距离
{														//而不是与已访问区域间最短距离
	virtual void operator()(Graph<Tv,Te>* G,int v,int u)		//v是访问完毕节点,u是搜索到的邻居
	{
		if(G->status(u) == VISITED)		//该算法中不存在 DISCOVERED的情况,不予考虑
		{
			return;			//访问过的则无需更新u的优先级,反正也用不上
		}
		if(G->weight(v,u) < G->priority(u))				//若权重小于原来u的优先级	
		{												//则u的优先级需要更新
			G->priority(u) = G->weight(v,u);
			G->parent(u) = v;			//同时更新父顶点
		}
	}
};//class DijkstraPO

template<typename Tv,typename Te> 
void Graph<Tv,Te>::dijkstra(int start)					//只需写明起点.构造最小路径树的公有接口
{
	pfs(start,DijkstraPO);
}
/////////////////////////////////////////////////////////////////////////

//作业