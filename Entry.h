/* Created on 2016-1-7 10:31:45 by LWZ. Template for entry */

#define ENTRY_H

template<typename K,typename V>
class Entry
{
public:
	V value;		
	K key;			//���Ҵ���

	//Ĭ�Ϲ��캯��
	Entry(K k = (K)0,V v = (V)0)
		:value(v),key(k){}

	//����������������� < == > !=
	bool operator<(const Entry<K,V>& E)	//���ͬ���͵ı�Ƚ�
	{
		return (key < E.key);
	}

	bool operator>(const Entry<K,V>& E)
	{
		return (key > E.key);
	}
	bool operator==(const Entry<K,V>& E)
	{
		return (key == E.key);	//�������ǻ���Ĵ������!
	}
	bool operator!=(const Entry<K,V>& E)
	{
		return (key != E.key);
	}
};//class Entry����