/* Created at 2015-12-9 22:33:21 by LWZ */


#include "ASonStack.h"
#include <iostream>

using namespace std;


class Queen
{
/*    
		y| 
		 |
		 |
		 .----------x
		 x y 轴
*/
	
public:
	int x,y;
	bool Valid;
	//默认构造函数
	Queen(int xx = 0,int yy = 0)
	{
		x = xx;
		y = yy;
		Valid = true; 
	}

	void operator()(Queen const &q)		//函数对象
	{
		
		if( (x == q.x) || (y == q.y) || ( x+y == q.x+q.y) || (x-y == q.x - q.y))	//遍历操作器,有任何这种情况Valid = false
		{																			//即该位置不可行
			Valid &= false;
		}
		else 
		{
			Valid &= true;					//只有当每次Valid 都是true时,该位置才可行
		}
	}

	Queen& operator=(Queen const&q)
	{
		x = q.x;
		y = q.y;
		return *this;
	}

};//class Queen

void PrintQueen(SStack<Queen> const& );
void PlaceQueen(int N);

template<typename T> void ShowAll(T const& e)
{
	cout<<e<<endl;
}

int main()
{
	if(0)
	{
		SStack<double> dbS(10,5,2.3);
		double a = dbS.Top();
		//cout<<a<<endl;
		cout<<endl;
		dbS.Traverse(ShowAll);
		cout<<endl;

		//SStack<double> dbS2(dbS);
		//dbS2.Traverse(ShowAll);
	}

	if(1)
	{
		PlaceQueen(8);				//四皇后问题
	}

	cin.get();

	return 0;
}

//N皇后问题
void PlaceQueen(int N)				//N即皇后个数
{									//篇幅问题只展示4种解
	int SoluCount = 0;
	SStack<Queen> Solu;				//存放解
	Queen q(0,0);
	
	while(q.x<N || q.y>0)			//该循环结束的条件是在第一行内列数超过N,此时意味着已经回溯完毕
	{
begin:	q.Valid = true;
		Solu.Traverse(q);
		while(!q.Valid && q.x<N)	//这里是行内逐列搜索
		{
			q.x++;
			q.Valid = true;
			Solu.Traverse(q);		//遍历
		}
		if(q.x<N)					//搜索完毕若x还小于4,则该位置合法
		{
			Solu.Push(q);			//有可能需要重载Queen的=运算符,Push运用的是复制
			if(Solu.Num() == N)		//z栈满
			{
				PrintQueen(Solu);	//打印该序列
				Solu.Pop();			//如果是最终解,那么在这一行中只有这个位置合法,故直接Pop两次
				q = Solu.Pop();		//退回到倒数第二行
				q.x++;				//从下一列开始继续搜索
				SoluCount++;		//计算方法个数
				goto begin;
			}
			q.y++;					//转向下一行
			q.x = 0;				//从下一行的第一列开始搜索
		}
		else						//该行内搜索失败
		{
			q = Solu.Pop();			//将已经入栈的元素出栈,并继续在出栈行内进行列搜索
			q.x++;
		}
		
		if(SoluCount>=4)			//只输出4个结果
		{
			return;					//结束程序
		}
	}//while
}

void PrintQueen(SStack<Queen> const& s)
{
	for(int i = 0;i<s.Num();i++)
	{
		cout<<"("<<s[i].x<<", "<<s[i].y<<") "<<endl;
	}
	cout<<"\n\n"<<endl;

}
