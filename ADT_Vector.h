/* Created at 2015-12-9 22:33:21 by LWZ */
#ifndef ADT_VECTOR_H
#define ADT_VECTOR_H

#define DEFAULT_CAPACITY 5

typedef int Rank;

//一个向量规模是Size，秩就是[0,Size-1]

template <typename T> 
class SVector
{
protected:
	T* DataBuff;			//数据区
	Rank Size;				//数据规模
	int Capacity;			//容量
	
	//内部接口

	void CopyFrom(T const* A,Rank lo,Rank hi)	//统一复制方法，从数据区A复制秩为lo到hi的数据,这是初始化方法！
	{											//这里复制的是[lo,hi)不包括hi
		Capacity = 2*(hi-lo);					//容量是两倍规模
		DataBuff = new T[Capacity];
		Size = 0;
		while(lo<hi)
		{
			DataBuff[Size] = A[lo];
			Size++;
			lo++;						//Size = hi-lo
		}
	}
	
	void Expand()						//扩容，内含容量判断
	{
		if(Size<Capacity)				//未超容量
		{
			return;
		}
		if(Capacity<DEFAULT_CAPACITY)
		{
			Capacity = DEFAULT_CAPACITY;
		}

		T* oldbuff = DataBuff;
		Capacity *= 2;
		DataBuff = new T[Capacity];
		for(int i=0;i<Size;i++)
		{
			DataBuff[i] = oldbuff[i];
		}
		delete [] oldbuff;
	}

	void Shrink()						//缩容
	{
		if(Capacity < DEFAULT_CAPACITY<<1)		//若容量小于默认容量的两倍不用缩容（考虑到缩容也需时间成本）
		{
			return;
		}
		if(Size > Capacity>>2)					//规模大于容量的四分之一，不用缩容
		{
			return;
		}

		T* oldbuff = DataBuff;
		Capacity >>= 1;							//容量减半
		DataBuff = new T[Capacity];
		for(int i=0;i<Size;i++)
		{
			DataBuff[i] = oldbuff[i];
		}
		delete [] oldbuff;
	}


public:
	
	//构造函数
	SVector(int c = DEFAULT_CAPACITY,int s = 0,T e = 0)		//默认构造函数，规模是s,容量是c
	{
		Capacity = c;
		DataBuff = new T[c];		//拓展起始空间
		for(Size=0;Size<s;Size++)	
		{
			DataBuff[Size] = e;		//设置数据默认值	留意:这里默认了类型T重载了运算符[]
		}
		//cout<<"vector"<<endl;
	}

	SVector(T const* A,Rank n)		//从A处复制n个数据(数组形式)
	{
		CopyFrom(A,0,n);
	}

	SVector(T const* A,Rank lo,Rank hi)
	{
		CopyFrom(A,lo,hi);
	}

	SVector(SVector<T> const& V)		//整体复制
	{
		CopyFrom(V.DataBuff,0,V.Size);
	}

	SVector(SVector<T> const& V,Rank lo,Rank hi)	//区间复制
	{
		CopyFrom(V.DataBuff,lo,hi);
	}
	SVector<T>& operator=(SVector<T> const& V)
	{
		CopyFrom(V.DataBuff,0,V.Size);
		return (*this);
	}
	//SVector<T>& operator=(T const* A,Rank n)
	//{
	//	CopyFrom(A,0,n);
	//	return (*this);
	//}


	//析构函数
	virtual ~SVector()
	{
		delete [] DataBuff;
		//cout<<"Clean Vector!"<<endl;
	}
	//只读接口
	int capacity() const
	{
		return Capacity;
	}

	Rank Num() const
	{
		return Size;
	}

	bool IsEmpty() const		//空返回1，非空返回0
	{
		return (Size == 0);
	}

	bool Disordered() const		//未排序返回1,排好序返回0
	{							//O(n)				
		Rank i = 0;
		while(i<Size-1)
		{
			if(DataBuff[i] > DataBuff[i+1])
			{
				return true;		//若有i大于i+1的情况，说明未排序
			}
			i++;
		}
		return false;				//循环完毕说明已排序
	}

	Rank Find(T const& e,Rank lo,Rank hi)	//区间[lo,hi)内查找e，若命中则返回秩，若未命中则返回hi
	{
		while(lo<hi)
		{
			if(DataBuff[lo] == e)
			{
				break;
			}
			lo++;
		}
		return lo;
	}

	Rank Find(T const& e)			//全区间查找
	{
		return Find(e,0,Size);
		
	}

	Rank BinSearch(T const& e,Rank lo,Rank hi) const	//二分查找,查找区间[lo,hi),返回小于等于e的最后一个秩
	{													//O(logn)
		while(lo<hi)
		{	
			Rank mi = (lo+hi)>>1;
			if(e<DataBuff[mi])
			{
				hi = mi;
			}
			else
			{
				lo = mi+1;
			}
		}
		return --lo;				//lo减一就是小于等于e的最后一个值
	}

	Rank BinSearch(T const& e) const					//全向量搜索，返回最后一个小于等于e的秩
	{													//O(logn)
		return BinSearch(e,0,Size);
	}


	//可写接口

	T& operator[](Rank r) const
	{
		return DataBuff[r];
	}

	Rank Insert(T const& e,Rank r)		//将元素 e插入到 秩为r的位置
	{
		Expand();						//如有需要需扩容
		for(int i = Size;i>r;i--)
		{
			DataBuff[i] = DataBuff[i-1];
		}
		DataBuff[r] = e;
		Size++;
		return r;	
	}

	Rank Insert(T const& e)				//末位元素插入
	{
		return Insert(e,Size);
	}

	int Remove(Rank lo,Rank hi)			//区间删除[lo,hi)
	{
		if(lo == hi)					//考虑退化情况
		{
			return 0;
		}
		while(hi<Size)
		{
			DataBuff[lo] = DataBuff[hi];
			hi++;
			lo++;
		}
		Size = lo;
		Shrink();
		return (hi-lo);
	}

	T Remove(Rank r)					//删除秩为r的元素
	{
		T e = DataBuff[r];
		Remove(r,r+1);
		return e;
	}

	int Deduplicate()					//无序向量唯一化//检验正确性! 返回删除的个数
	{									//O(n^2)
		int oldsize = Size;
		Rank i = Size-1;
		Rank temp = 0;
		while(i)
		{

			temp = Find(DataBuff[i],temp,i);	//在[0,i)区间找重复值
			if(temp != i)
			{
				Remove(temp);
			}
			else								//若temp =i即该搜索并删除完毕,将temp归0从头搜索
			{
				temp = 0;
			}	
			i--;
		}
		Shrink();						//检验是否需要缩容
		return (oldsize-Size);
	}

	int Uniquify()						//有序向量唯一化
	{
		Rank i = 0,j = 1;
		int oldsize = Size;
		while(j<Size)
		{
			//j++;			//注意这里的错误,错误的判断了BuffSize[Size]而非BuffSize[Size-1]
			if(DataBuff[i] != DataBuff[j])
			{
				i++;
				DataBuff[i] = DataBuff[j];
			}
			j++;
		}
		Size = i+1;
		Shrink();
		return (oldsize-Size);			//返回删除个数
	}
	
	
		
	void Merge(Rank lo,Rank mi,Rank hi)		//两序列归并，要求两段不能重叠，但可以在同一向量内
	{										//序列[lo,mi),[mi,hi) 有待测试					
		int lb = mi-lo,lc = hi-mi;			//计算两个序列的长度
		T* B = new T[lb];					//开辟空间做备份
		T* A = DataBuff+lo;
		T* C = DataBuff+mi;
		for(Rank i=0;i<lb;i++)
		{
			B[i] = A[i];					//备份数据
		}
		for(Rank n=0,i=0,j=0;(i<lb)||(j<lc);)			//i或j没到顶，就继续归并
		{
			if((i<lb) && (B[i]<=C[j] || j>=lc))			//当B序列还没排完，且B序列小于等于C序列当前元素
			{											//或C序列已经扫描到头（此时直接将B序列剩余元素逐个输入即可）
				A[n] = B[i];
				n++;
				i++;
			}
			if((j<lc) && (B[i]>C[j] || i>=lb))
			{
				A[n] = C[j];
				j++;
				n++;
			}
		}
		delete [] B;						//删除临时空间
	
		//以下是原来的思路，行不通
//		while(lo2<hi2)
//		{
//			temp = BinSearch(V[lo2],lo1,hi1);						
			//Insert(V[lo2],temp);			//不能用Insert，假如在原向量内归并，会影响后半序列的元素

	}


	void MergeSort(Rank lo,Rank hi)			//归并排序区间[lo,hi)
	{
		if(hi-lo < 2)		//区间内只有一个元素
		{
			return;
		}
		Rank mi = (lo+hi)>>1;				//取中点

		MergeSort(lo,mi);
		MergeSort(mi,hi);

		Merge(lo,mi,hi);
	}


	//遍历接口

	void Traverse(void (*visit)(T const&))		//函数指针接口
	{
		for(int i=0;i<Size;i++)
		{
			(*visit)(DataBuff[i]);
		}
	}
	
	template<typename VST>					//操作性接口
	void Traverse(VST& visit)
	{
		for(int i=0;i<Size;i++)
		{
			visit(DataBuff[i]);
		}
	}

};// class Vector

#endif //ADT_VECTOR_H