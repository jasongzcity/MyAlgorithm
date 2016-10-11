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
		 x y ��
*/
	
public:
	int x,y;
	bool Valid;
	//Ĭ�Ϲ��캯��
	Queen(int xx = 0,int yy = 0)
	{
		x = xx;
		y = yy;
		Valid = true; 
	}

	void operator()(Queen const &q)		//��������
	{
		
		if( (x == q.x) || (y == q.y) || ( x+y == q.x+q.y) || (x-y == q.x - q.y))	//����������,���κ��������Valid = false
		{																			//����λ�ò�����
			Valid &= false;
		}
		else 
		{
			Valid &= true;					//ֻ�е�ÿ��Valid ����trueʱ,��λ�òſ���
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
		PlaceQueen(8);				//�Ļʺ�����
	}

	cin.get();

	return 0;
}

//N�ʺ�����
void PlaceQueen(int N)				//N���ʺ����
{									//ƪ������ֻչʾ4�ֽ�
	int SoluCount = 0;
	SStack<Queen> Solu;				//��Ž�
	Queen q(0,0);
	
	while(q.x<N || q.y>0)			//��ѭ���������������ڵ�һ������������N,��ʱ��ζ���Ѿ��������
	{
begin:	q.Valid = true;
		Solu.Traverse(q);
		while(!q.Valid && q.x<N)	//������������������
		{
			q.x++;
			q.Valid = true;
			Solu.Traverse(q);		//����
		}
		if(q.x<N)					//���������x��С��4,���λ�úϷ�
		{
			Solu.Push(q);			//�п�����Ҫ����Queen��=�����,Push���õ��Ǹ���
			if(Solu.Num() == N)		//zջ��
			{
				PrintQueen(Solu);	//��ӡ������
				Solu.Pop();			//��������ս�,��ô����һ����ֻ�����λ�úϷ�,��ֱ��Pop����
				q = Solu.Pop();		//�˻ص������ڶ���
				q.x++;				//����һ�п�ʼ��������
				SoluCount++;		//���㷽������
				goto begin;
			}
			q.y++;					//ת����һ��
			q.x = 0;				//����һ�еĵ�һ�п�ʼ����
		}
		else						//����������ʧ��
		{
			q = Solu.Pop();			//���Ѿ���ջ��Ԫ�س�ջ,�������ڳ�ջ���ڽ���������
			q.x++;
		}
		
		if(SoluCount>=4)			//ֻ���4�����
		{
			return;					//��������
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
