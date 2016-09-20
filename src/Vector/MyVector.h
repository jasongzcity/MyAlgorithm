/* Created at 2015-12-9 22:33:21 by LWZ */
#ifndef ADT_VECTOR_H
#define ADT_VECTOR_H

#define DEFAULT_CAPACITY 5

typedef int Rank;

template <typename T> 
class SVector
{
protected:
    T* DataBuff;
    Rank Size;
    int Capacity;

    void CopyFrom(T const* A,Rank lo,Rank hi)
    {
        Capacity = 2*(hi-lo);
        DataBuff = new T[Capacity];
        Size = 0;
        while(lo<hi)
        {
            DataBuff[Size] = A[lo];
            Size++;
            lo++;						//Size = hi-lo
        }
    }

    void Expand()
    {
        if(Size<Capacity)
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

	void Shrink()						//����
	{
		if(Capacity < DEFAULT_CAPACITY<<1)		//������С��Ĭ�������������������ݣ����ǵ�����Ҳ��ʱ��ɱ���
		{
			return;
		}
		if(Size > Capacity>>2)					//��ģ�����������ķ�֮һ����������
		{
			return;
		}

		T* oldbuff = DataBuff;
		Capacity >>= 1;							//��������
		DataBuff = new T[Capacity];
		for(int i=0;i<Size;i++)
		{
			DataBuff[i] = oldbuff[i];
		}
		delete [] oldbuff;
	}

public:
	//���캯��
	SVector(int c = DEFAULT_CAPACITY,int s = 0,T e = 0)		//Ĭ�Ϲ��캯������ģ��s,������c
	{
		Capacity = c;
		DataBuff = new T[c];		//��չ��ʼ�ռ�
		for(Size=0;Size<s;Size++)	
		{
			DataBuff[Size] = e;		//��������Ĭ��ֵ	����:����Ĭ��������T�����������[]
		}
		//cout<<"vector"<<endl;
	}

	SVector(T const* A,Rank n)		//��A������n������(������ʽ)
	{
		CopyFrom(A,0,n);
	}

	SVector(T const* A,Rank lo,Rank hi)
	{
		CopyFrom(A,lo,hi);
	}

	SVector(SVector<T> const& V)		//���帴��
	{
		CopyFrom(V.DataBuff,0,V.Size);
	}

	SVector(SVector<T> const& V,Rank lo,Rank hi)	//���临��
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


	//��������
	virtual ~SVector()
	{
		delete [] DataBuff;
		//cout<<"Clean Vector!"<<endl;
	}
	//ֻ���ӿ�
	int capacity() const
	{
		return Capacity;
	}

	Rank Num() const
	{
		return Size;
	}

	bool IsEmpty() const		//�շ���1���ǿշ���0
	{
		return (Size == 0);
	}

	bool Disordered() const		//δ���򷵻�1,�ź��򷵻�0
	{							//O(n)				
		Rank i = 0;
		while(i<Size-1)
		{
			if(DataBuff[i] > DataBuff[i+1])
			{
				return true;		//����i����i+1�������˵��δ����
			}
			i++;
		}
		return false;				//ѭ�����˵��������
	}

	Rank Find(T const& e,Rank lo,Rank hi)	//����[lo,hi)�ڲ���e���������򷵻��ȣ���δ�����򷵻�hi
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

	Rank Find(T const& e)			//ȫ�������
	{
		return Find(e,0,Size);
		
	}

	Rank BinSearch(T const& e,Rank lo,Rank hi) const	//���ֲ���,��������[lo,hi),����С�ڵ���e�����һ����
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
		return --lo;
    }

	Rank BinSearch(T const& e) const
	{													//O(logn)
		return BinSearch(e,0,Size);
	}


	//��д�ӿ�

	T& operator[](Rank r) const
	{
		return DataBuff[r];
	}

	Rank Insert(T const& e,Rank r)		//��Ԫ�� e���뵽 ��Ϊr��λ��
	{
		Expand();						//������Ҫ������
		for(int i = Size;i>r;i--)
		{
			DataBuff[i] = DataBuff[i-1];
		}
		DataBuff[r] = e;
		Size++;
		return r;	
	}

	Rank Insert(T const& e)				//ĩλԪ�ز���
	{
		return Insert(e,Size);
	}

	int Remove(Rank lo,Rank hi)			//����ɾ��[lo,hi)
	{
		if(lo == hi)					//�����˻����
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

	T Remove(Rank r)					//ɾ����Ϊr��Ԫ��
	{
		T e = DataBuff[r];
		Remove(r,r+1);
		return e;
	}

	int Deduplicate()					//��������Ψһ��//������ȷ��! ����ɾ���ĸ���
	{									//O(n^2)
		int oldsize = Size;
		Rank i = Size-1;
		Rank temp = 0;
		while(i)
		{

			temp = Find(DataBuff[i],temp,i);	//��[0,i)�������ظ�ֵ
			if(temp != i)
			{
				Remove(temp);
			}
			else								//��temp =i����������ɾ�����,��temp��0��ͷ����
			{
				temp = 0;
			}	
			i--;
		}
		Shrink();						//�����Ƿ���Ҫ����
		return (oldsize-Size);
	}

	int Uniquify()						//��������Ψһ��
	{
		Rank i = 0,j = 1;
		int oldsize = Size;
		while(j<Size)
		{
			//j++;			//ע������Ĵ���,������ж���BuffSize[Size]����BuffSize[Size-1]
			if(DataBuff[i] != DataBuff[j])
			{
				i++;
				DataBuff[i] = DataBuff[j];
			}
			j++;
		}
		Size = i+1;
		Shrink();
		return (oldsize-Size);			//����ɾ������
	}
	
	
		
	void Merge(Rank lo,Rank mi,Rank hi)		//�����й鲢��Ҫ�����β����ص�����������ͬһ������
	{										//����[lo,mi),[mi,hi) �д�����					
		int lb = mi-lo,lc = hi-mi;			//�����������еĳ���
		T* B = new T[lb];					//���ٿռ�������
		T* A = DataBuff+lo;
		T* C = DataBuff+mi;
		for(Rank i=0;i<lb;i++)
		{
			B[i] = A[i];					//��������
		}
		for(Rank n=0,i=0,j=0;(i<lb)||(j<lc);)			//i��jû�������ͼ����鲢
		{
			if((i<lb) && (B[i]<=C[j] || j>=lc))			//��B���л�û���꣬��B����С�ڵ���C���е�ǰԪ��
			{											//��C�����Ѿ�ɨ�赽ͷ����ʱֱ�ӽ�B����ʣ��Ԫ��������뼴�ɣ�
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
		delete [] B;						//ɾ����ʱ�ռ�
	
		//������ԭ����˼·���в�ͨ
//		while(lo2<hi2)
//		{
//			temp = BinSearch(V[lo2],lo1,hi1);						
			//Insert(V[lo2],temp);			//������Insert��������ԭ�����ڹ鲢����Ӱ�������е�Ԫ��

	}


	void MergeSort(Rank lo,Rank hi)			//�鲢��������[lo,hi)
	{
		if(hi-lo < 2)		//������ֻ��һ��Ԫ��
		{
			return;
		}
		Rank mi = (lo+hi)>>1;				//ȡ�е�

		MergeSort(lo,mi);
		MergeSort(mi,hi);

		Merge(lo,mi,hi);
	}


	//�����ӿ�

	void Traverse(void (*visit)(T const&))		//����ָ��ӿ�
	{
		for(int i=0;i<Size;i++)
		{
			(*visit)(DataBuff[i]);
		}
	}
	
	template<typename VST>					//�����Խӿ�
	void Traverse(VST& visit)
	{
		for(int i=0;i<Size;i++)
		{
			visit(DataBuff[i]);
		}
	}

};// class Vector

#endif //ADT_VECTOR_H
