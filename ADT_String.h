//串
#include "iostream.h"

class CStringG
{
private:
	char *m_pString;
	int m_nLength;
	int m_KmpNext[256];				//KmpNext[0] = 0根据KMP算法,用不到0

public:
	CStringG()						//构造
	{
		m_pString = NULL;
		m_nLength = 0;
	}
	virtual ~CStringG()
	{
		delete [] m_pString;		//所有delete都带中括号，因为创建的时候带了中括号
		m_nLength = 0;
	}

	bool StrAssign(char *chars)		//给字符串赋值赋值为 chars,准备重载运算法并测试
	{
		if(m_pString)
		{
			delete [] m_pString;	//释放原有空间	
		}
		int CharCount = 0;			//计算chars字符数
		char *temp = chars;			//临时指针用于循环计数
		while(*temp)
		{	
			CharCount++;			
			temp++;
		}
		if(CharCount == 0)			//传递空字符串
		{
			m_nLength = 0;
			m_pString = NULL;
			return false;
		}
		m_pString = (char*) new char[(CharCount+1)];		//+1为了存储字符串结束符
		if(!m_pString)										//分配失败
		{
			m_nLength = 0;
			return false;
		}
		
		for(int i=0;i<CharCount;i++)
		{
			*(m_pString+i) = *chars;
			chars++;
		}
		*(m_pString+CharCount) = '\0';								//存结束符
		m_nLength = CharCount;
		SetKmpNext();
		return true;
	}

	int StrLength()
	{
		return m_nLength;
	}
	
	int StrCompare(const CStringG *str)				//和str串比较，大于返回>0，小于返回<0，等于返回0
	{
		char *temp_str = str->m_pString;			//构建临时指针用于累加比较
		char *temp_this = m_pString;			
		
		while((*temp_str) == (*temp_this))			//相等时
		{
			temp_str++;
			temp_this++;
			if((*temp_str)==0 && (*temp_this)==0)	//同时结束，说明字符串完全相等
			{
				return 0;
			}
		}
		if((*temp_this) < (*temp_str))				//字符不等且自有成员较小
		{
			return (*temp_this) - (*temp_str);
		}
		else									//自有成员较大
		{
			return (*temp_str) - (*temp_this);
		}
	}

	int StrCompare(char *str)					//字符串比较的重载
	{
		char *temp_this = m_pString;			//建立临时指针变量
		
		while((*str) == (*temp_this))			//相等时
		{
			str++;
			temp_this++;
			if((*str)==0 && (*temp_this)==0)	//同时结束，说明字符串完全相等
			{
				return 0;
			}
		}
		if((*temp_this) < (*str))				//字符不等且自有成员较小
		{
			return (*temp_this) - (*str);
		}
		else									//自有成员较大
		{
			return (*str) - (*temp_this);
		}
	}

	bool ClearStr()								//将串清空
	{
		delete [] m_pString;
		m_nLength = 0;
		m_pString = NULL;
	}

	const CStringG & operator+=(const CStringG &str)	//加上某一字符串
	{
		Concat(str);
		return *this;									//操作与Concat相同，只需返回this
	}
	
	bool Concat(const CStringG &str)					//将str1加在m_pString后
	{
		char *temp = m_pString;
		m_pString =(char*) new char[(m_nLength+str.m_nLength+1)];		//+1为了存'\0'
		if(!m_pString)
		{
			m_pString = temp;											//指向原区域
			return false;
		}
		int count = 0;
		while(count<m_nLength)
		{
			*(m_pString+count) = *(temp+count);							//拷贝
			count++;
		}
		delete [] temp;
		char *strTmp = str.m_pString;
		while(count<m_nLength+str.m_nLength)
		{
			*(m_pString+count) = *strTmp++;
			count++;
		}
		*(m_pString+count) = '\0';
		m_nLength += str.m_nLength;
		SetKmpNext();
		return true;
	}

	const CStringG & operator=(const CStringG &str)				//复制函数
	{
		StrAssign(str.m_pString);								//此处包含删除
		return *this;
	}

	bool SubString(CStringG &Sub,int pos,int len)				//用Sub返回字符串第pos个字符起长度为len的字符串（包含pos字符）
	{
		if(pos<1||pos>m_nLength||pos+len>m_nLength+1||len<1)
		{
			return false;
		}
		if(Sub.m_pString)
		{
			delete [] Sub.m_pString;
		}
		Sub.m_pString = (char*)new char[(len+1)];
		int count = 0;
		for(;count<len;count++)
		{
			*(Sub.m_pString+count) = *(m_pString+pos-1+count);	//扫描赋值
		}
		Sub.m_nLength = len;
		*(Sub.m_pString+count) = '\0';
		Sub.SetKmpNext();
		return true;
	}

	void DispString()
	{
		cout<<m_pString<<endl;
	}

	void DispKmpIndex()
	{
		int i = 1;
		cout<<m_KmpNext[i]<<endl;
		i++;
		while(m_KmpNext[i] != 0)
		{
			cout<<m_KmpNext[i]<<endl;
			i++;
		}
	}

	//备注：效率稍低的匹配算法由取子串和STRCMP函数实现
	int StrMatch_Kmp(const CStringG *Sub,int pos)				//在本字符串pos个字符后开始扫描匹配，运用KMP算法
	{															//不匹配返回0，匹配则返回匹配的字符位置
		int i = pos-1,j = 0;									//i和j用作m_pString指针的“游标”，须比字符数减一
		char *SubTemp = Sub->m_pString;							//同时j承担字串的KMP数组的系数。KMPNext【0】无意义
		while(j<Sub->m_nLength && i<m_nLength)					//nEXT[1] = 0
		{
			if(j==-1||*(m_pString+i) == *(SubTemp+j))			//此处的j==-1表示的是由kmp[1]传递回来的-1，表示第一个字符不匹配
			{													//需继续向下一字符扫描
				i++;											//相等则继续匹配
				j++;
			}
			else
			{
				j = Sub->m_KmpNext[j+1]-1;						//系数比游标大1,传递回去的是字符位数，对应游标需减一
			}													//当j=0不等的时候，有可能把j==-1传回去，需设定。
		}
		if(j == Sub->m_nLength)
		{
			return i+1-j;
		}
		else
		{
			return 0;
		}
	}
				
	void SetKmpNext()											//设定KMPNext系数，需要跟在每个修改过字符串的方法后面
	{
		char *temp = m_pString;									//与书上情况一样，系数i对应的是第i个字符，比游标大1
		int i = 1,j = 0;
		m_KmpNext[1] = 0;
		while(i<m_nLength)
		{
			if(j == 0||*(temp+i-1) == *(temp+j-1))				//游标+1
			{
				i++;
				j++;
				m_KmpNext[i] = j;
			}
			else
			{
				j = m_KmpNext[j];
			}
		}
		i++;
		while(i<256)											//将用不到的位数置0
		{
			m_KmpNext[i] = 0;
			i++;
		}
	}
};



	
