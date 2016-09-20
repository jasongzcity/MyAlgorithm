/* Created on 2016-1-29 13:30:46 byLWZ. For hashtable testing */

#include "HashTable.h"

/*				�ӿڲ���
 *	Hashfunction�Խ���ȷ
 *	Put��ȷ rehash��ȷ
 *  remove��ȷ get��ȷ
 *	size��ȷ									*/

class Hashfunction
{
public:
	int operator()(int key,int bucketnum)		//�������෨
	{
		return key%bucketnum;
	}
	//...������
};

int main(void)
{
	using namespace std;
	HashTable<char,int,Hashfunction> Hss=HashTable<char,int,Hashfunction>(5);//�����ܷ�Խ�keyΪchar���������Ϊint�Ĺ�ϣ����
	Hss.Put('a','a');
	Hss.Put('a','a');
	Hss.Put('b','b');
	Hss.Put('c','c');
	Hss.Put('f','f');
	Hss.Put('q','q');
	Hss.Put('r','r');
	Hss.Put('t','t');
	Hss.Put('z','z');
	Hss.Put('s','s');
	Hss.Put('y','y');
	Hss.Put('k','k');
	cout<<*Hss.Get('c')<<endl;
	cout<<"������Ŀ:"<<Hss.Size()<<endl;
	cout<<"ɾ����ʼ"<<endl;
	Hss.Remove('s');
	Hss.Remove('b');
	cout<<*Hss.Get('c')<<endl;
	if(!Hss.Get('b'))
	{
		cout<<"Remove Rightly!"<<endl;
	}
	cout<<"������Ŀ:"<<Hss.Size()<<endl;
	cin.get();
	return 0;
}