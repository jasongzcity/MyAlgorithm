/* Bitmap.h Created on 2016-1-27 21:52:34 by LWZ Definition for class Bitmap */

#define BITMAP_H
#include <cstdlib>
#include <memory>

//这里采用的是向量方法
class Bitmap
{
private:
	char* buff;	//数据区
	int size;	//规模
protected:
	void init(int n)
	{
		size = n;
		int temp = (size+7)/8;
		buff = new char [temp];
		memset(buff,0,sizeof(char)*temp);		//初始化为0
	}
	void expand(int i)			//扩展到第i位
	{
		if(i<size)
		{
			return;				//若已扩展到则直接返回
		}
		//else
		char* oldbuff = buff;
		int oldsize = size;
		init(2*i);				//仿照向量方法两倍扩充
		memcpy(buff,oldbuff,(oldsize+7)/8);	//拷贝数据
		delete [] buff;
	}
public:
	Bitmap(int n=8)				//构造时设置规模
	{
		init(n);				
	}
	Bitmap(char* file,int n=8)	//按照制定规模从文件路径读取比特图
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
	//关键的三个接口set()clear()test()都可在常数时间内完成
	void set(int i)		//将第i位置为true
	{
		expand(i);		//检验位置是否合法
		int posi = i>>3;//该比特位所在字节
		buff[posi] |= (0x80>>(i&0x07));//即10000000>>(i&0111)可得到第i个比特位在该字节中的掩码
	}
	void clear(int i)	//将第i位设为false
	{
		expand(i);		
		int posi = i>>3;//比特位所在字节
		buff[posi] &= ~(0x80>>(i&0x07));
	}
	bool test(int i)	//测试第i位并返回布尔值
	{
		expand(i);
		int posi = i>>3;
		return buff[posi] & (0x80>>(i&0x07)); 
	}
	void dump(char* file)	//将位图导入到file指定的位图位置
	{
		FILE* fp = fopen(file,"w");	//读文件形式打开文件
		fwrite(buff,sizeof(char),(size+7)/8,fp);
		fclose(fp);
	}
	char* bits2string(int n)		//将前n个比特位转换成字符串,注意!上层调用需析构字符串!
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

//这个新位图基于自检环实现,节省了初始化时间,以空间作为牺牲
//根本原理在于用F向量记录写入次序(F[bit]=次序),T[F[bit](这里是紧邻的次序)]==bit时则true
class vBitmap
{
private:
	int* v;
	int size;
	int* stack;
	int top;
	int maxInt;
protected:
	void init(int n)	//n为初始规模
	{					//一开始时两向量都以调用者设定值初始化
		size = n;
		v = new int[size];
		maxInt = n;
		stack = new int[size];
		top = 0;
	}
	inline bool valid(int k)		//检验第k位是否合法
	{
		return ((k<top) && (k>=0));
	}
	inline bool erased(int k)		//判断第k比特位上的信息是否被擦除因向量v上信息不回被删除,擦除时只是改动stack上信息
	{
		return (valid(v[k]) && !(stack[v[k]]+1+k));//比特位在次序上合法且符合擦除式
	}
	void expand_v(int k)			//扩展向量v
	{
		if(k<maxInt)	//则无需扩展
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
		size = 2*top;			//top此时应该恰好等于size
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
	inline bool test(int k)				//检验第k位
	{
		return valid(v[k]) && (stack[v[k]] == k);
	}
	inline bool clear(int k)	//擦除第k位
	{
		if(test(k))				//若自检环存在则擦除,不存在则忽略
		{
			stack[v[k]] = -1-k;
		}
	}
	void set(int k)					//设置k位为true
	{
		expand_v(k);
		expand_stack();
		if(test(k))
		{
			return;
		}
		if(!erased(k))
		{
			v[k] = top++;			//若非擦除说明是新节点,需开辟
		}							//若是擦除则已经开辟过了
		stack[v[k]] = k;
	}
};