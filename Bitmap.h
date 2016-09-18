/* Bitmap.h Created on 2016-1-27 21:52:34 by LWZ Definition for class Bitmap */

#define BITMAP_H
#include <cstdlib>
#include <memory>

//������õ�����������
class Bitmap
{
private:
	char* buff;	//������
	int size;	//��ģ
protected:
	void init(int n)
	{
		size = n;
		int temp = (size+7)/8;
		buff = new char [temp];
		memset(buff,0,sizeof(char)*temp);		//��ʼ��Ϊ0
	}
	void expand(int i)			//��չ����iλ
	{
		if(i<size)
		{
			return;				//������չ����ֱ�ӷ���
		}
		//else
		char* oldbuff = buff;
		int oldsize = size;
		init(2*i);				//��������������������
		memcpy(buff,oldbuff,(oldsize+7)/8);	//��������
		delete [] buff;
	}
public:
	Bitmap(int n=8)				//����ʱ���ù�ģ
	{
		init(n);				
	}
	Bitmap(char* file,int n=8)	//�����ƶ���ģ���ļ�·����ȡ����ͼ
	{
		init(n);
		FILE* fp = fopen(file,"r");
		fread(buff,sizeof(char),(size+7)/8,fp);
		fclose(fp);
	}
	virtual ~Bitmap()
	{
		delete [] buff;
		buff = NULL;
	}
	//�ؼ��������ӿ�set()clear()test()�����ڳ���ʱ�������
	void set(int i)		//����iλ��Ϊtrue
	{
		expand(i);		//����λ���Ƿ�Ϸ�
		int posi = i>>3;//�ñ���λ�����ֽ�
		buff[posi] |= (0x80>>(i&0x07));//��10000000>>(i&0111)�ɵõ���i������λ�ڸ��ֽ��е�����
	}
	void clear(int i)	//����iλ��Ϊfalse
	{
		expand(i);		
		int posi = i>>3;//����λ�����ֽ�
		buff[posi] &= ~(0x80>>(i&0x07));
	}
	bool test(int i)	//���Ե�iλ�����ز���ֵ
	{
		expand(i);
		int posi = i>>3;
		return buff[posi] & (0x80>>(i&0x07)); 
	}
	void dump(char* file)	//��λͼ���뵽fileָ����λͼλ��
	{
		FILE* fp = fopen(file,"w");	//���ļ���ʽ���ļ�
		fwrite(buff,sizeof(char),(size+7)/8,fp);
		fclose(fp);
	}
	char* bits2string(int n)		//��ǰn������λת�����ַ���,ע��!�ϲ�����������ַ���!
	{
		expand(n);
		char* str = new char[n+1];
		for(int i=0;i<n;i++)
		{
			test(i)?str[i]='1':str[i]='0';
		}
		str[n] = 0;
		return str;					
	}
};//vBitmap

//�����λͼ�����Լ컷ʵ��,��ʡ�˳�ʼ��ʱ��,�Կռ���Ϊ����
//����ԭ��������F������¼д�����(F[bit]=����),T[F[bit](�����ǽ��ڵĴ���)]==bitʱ��true
class vBitmap
{
private:
	int* v;
	int size;
	int* stack;
	int top;
	int maxInt;
protected:
	void init(int n)	//nΪ��ʼ��ģ
	{					//һ��ʼʱ���������Ե������趨ֵ��ʼ��
		size = n;
		v = new int[size];
		maxInt = n;
		stack = new int[size];
		top = 0;
	}
	inline bool valid(int k)		//�����kλ�Ƿ�Ϸ�
	{
		return ((k<top) && (k>=0));
	}
	inline bool erased(int k)		//�жϵ�k����λ�ϵ���Ϣ�Ƿ񱻲���������v����Ϣ���ر�ɾ��,����ʱֻ�ǸĶ�stack����Ϣ
	{
		return (valid(v[k]) && !(stack[v[k]]+1+k));//����λ�ڴ����ϺϷ��ҷ��ϲ���ʽ
	}
	void expand_v(int k)			//��չ����v
	{
		if(k<maxInt)	//��������չ
		{
			return;
		}
		//else
		int oldInt = maxInt;
		maxInt = 2*k;
		int* oldv = v;
		v = new int[maxInt];
		memcpy(v,oldv,oldInt);
		delete [] oldv;
	}
	void expand_stack()
	{
		if(top<size)
		{
			return;
		}
		int oldsize = size;
		int* oldstack = stack;
		size = 2*top;			//top��ʱӦ��ǡ�õ���size
		stack = new int[size];
		memcpy(stack,oldstack,oldsize);
		delete [] oldstack;
	}
public:
	vBitmap(int n=8)
	{
		init(n);
	}
	virtual ~vBitmap()
	{
		delete [] v;
		delete [] stack;
		v = NULL;
		stack = NULL;
	}
	inline bool test(int k)				//�����kλ
	{
		return valid(v[k]) && (stack[v[k]] == k);
	}
	inline bool clear(int k)	//������kλ
	{
		if(test(k))				//���Լ컷���������,�����������
		{
			stack[v[k]] = -1-k;
		}
	}
	void set(int k)					//����kλΪtrue
	{
		expand_v(k);
		expand_stack();
		if(test(k))
		{
			return;
		}
		if(!erased(k))
		{
			v[k] = top++;			//���ǲ���˵�����½ڵ�,�迪��
		}							//���ǲ������Ѿ����ٹ���
		stack[v[k]] = k;
	}
};