//��
#include "iostream.h"

class CStringG
{
private:
	char *m_pString;
	int m_nLength;
	int m_KmpNext[256];				//KmpNext[0] = 0����KMP�㷨,�ò���0

public:
	CStringG()						//����
	{
		m_pString = NULL;
		m_nLength = 0;
	}
	virtual ~CStringG()
	{
		delete [] m_pString;		//����delete���������ţ���Ϊ������ʱ�����������
		m_nLength = 0;
	}

	bool StrAssign(char *chars)		//���ַ�����ֵ��ֵΪ chars,׼���������㷨������
	{
		if(m_pString)
		{
			delete [] m_pString;	//�ͷ�ԭ�пռ�	
		}
		int CharCount = 0;			//����chars�ַ���
		char *temp = chars;			//��ʱָ������ѭ������
		while(*temp)
		{	
			CharCount++;			
			temp++;
		}
		if(CharCount == 0)			//���ݿ��ַ���
		{
			m_nLength = 0;
			m_pString = NULL;
			return false;
		}
		m_pString = (char*) new char[(CharCount+1)];		//+1Ϊ�˴洢�ַ���������
		if(!m_pString)										//����ʧ��
		{
			m_nLength = 0;
			return false;
		}
		
		for(int i=0;i<CharCount;i++)
		{
			*(m_pString+i) = *chars;
			chars++;
		}
		*(m_pString+CharCount) = '\0';								//�������
		m_nLength = CharCount;
		SetKmpNext();
		return true;
	}

	int StrLength()
	{
		return m_nLength;
	}
	
	int StrCompare(const CStringG *str)				//��str���Ƚϣ����ڷ���>0��С�ڷ���<0�����ڷ���0
	{
		char *temp_str = str->m_pString;			//������ʱָ�������ۼӱȽ�
		char *temp_this = m_pString;			
		
		while((*temp_str) == (*temp_this))			//���ʱ
		{
			temp_str++;
			temp_this++;
			if((*temp_str)==0 && (*temp_this)==0)	//ͬʱ������˵���ַ�����ȫ���
			{
				return 0;
			}
		}
		if((*temp_this) < (*temp_str))				//�ַ����������г�Ա��С
		{
			return (*temp_this) - (*temp_str);
		}
		else									//���г�Ա�ϴ�
		{
			return (*temp_str) - (*temp_this);
		}
	}

	int StrCompare(char *str)					//�ַ����Ƚϵ�����
	{
		char *temp_this = m_pString;			//������ʱָ�����
		
		while((*str) == (*temp_this))			//���ʱ
		{
			str++;
			temp_this++;
			if((*str)==0 && (*temp_this)==0)	//ͬʱ������˵���ַ�����ȫ���
			{
				return 0;
			}
		}
		if((*temp_this) < (*str))				//�ַ����������г�Ա��С
		{
			return (*temp_this) - (*str);
		}
		else									//���г�Ա�ϴ�
		{
			return (*str) - (*temp_this);
		}
	}

	bool ClearStr()								//�������
	{
		delete [] m_pString;
		m_nLength = 0;
		m_pString = NULL;
	}

	const CStringG & operator+=(const CStringG &str)	//����ĳһ�ַ���
	{
		Concat(str);
		return *this;									//������Concat��ͬ��ֻ�践��this
	}
	
	bool Concat(const CStringG &str)					//��str1����m_pString��
	{
		char *temp = m_pString;
		m_pString =(char*) new char[(m_nLength+str.m_nLength+1)];		//+1Ϊ�˴�'\0'
		if(!m_pString)
		{
			m_pString = temp;											//ָ��ԭ����
			return false;
		}
		int count = 0;
		while(count<m_nLength)
		{
			*(m_pString+count) = *(temp+count);							//����
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

	const CStringG & operator=(const CStringG &str)				//���ƺ���
	{
		StrAssign(str.m_pString);								//�˴�����ɾ��
		return *this;
	}

	bool SubString(CStringG &Sub,int pos,int len)				//��Sub�����ַ�����pos���ַ��𳤶�Ϊlen���ַ���������pos�ַ���
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
			*(Sub.m_pString+count) = *(m_pString+pos-1+count);	//ɨ�踳ֵ
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

	//��ע��Ч���Ե͵�ƥ���㷨��ȡ�Ӵ���STRCMP����ʵ��
	int StrMatch_Kmp(const CStringG *Sub,int pos)				//�ڱ��ַ���pos���ַ���ʼɨ��ƥ�䣬����KMP�㷨
	{															//��ƥ�䷵��0��ƥ���򷵻�ƥ����ַ�λ��
		int i = pos-1,j = 0;									//i��j����m_pStringָ��ġ��αꡱ������ַ�����һ
		char *SubTemp = Sub->m_pString;							//ͬʱj�е��ִ���KMP�����ϵ����KMPNext��0��������
		while(j<Sub->m_nLength && i<m_nLength)					//nEXT[1] = 0
		{
			if(j==-1||*(m_pString+i) == *(SubTemp+j))			//�˴���j==-1��ʾ������kmp[1]���ݻ�����-1����ʾ��һ���ַ���ƥ��
			{													//���������һ�ַ�ɨ��
				i++;											//��������ƥ��
				j++;
			}
			else
			{
				j = Sub->m_KmpNext[j+1]-1;						//ϵ�����α��1,���ݻ�ȥ�����ַ�λ������Ӧ�α����һ
			}													//��j=0���ȵ�ʱ���п��ܰ�j==-1����ȥ�����趨��
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
				
	void SetKmpNext()											//�趨KMPNextϵ������Ҫ����ÿ���޸Ĺ��ַ����ķ�������
	{
		char *temp = m_pString;									//���������һ����ϵ��i��Ӧ���ǵ�i���ַ������α��1
		int i = 1,j = 0;
		m_KmpNext[1] = 0;
		while(i<m_nLength)
		{
			if(j == 0||*(temp+i-1) == *(temp+j-1))				//�α�+1
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
		while(i<256)											//���ò�����λ����0
		{
			m_KmpNext[i] = 0;
			i++;
		}
	}
};



	
