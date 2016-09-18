/* Created on 2016-1-28 by LWZ. templates for HashTable */

#define HASHTABLE_H
#include <memory>
#ifndef BITMAP_H
#include <Bitmap.h>
#endif
#ifndef	DICTIONARY_H
#include <Dictionary.h>
#endif
#ifndef ENTRY_H
#include <Entry.h>
#endif
#include <iostream>

int primeNLT(int c,int n,char* file)	//ȡ[c,n)�����С����
{
	Bitmap B(file,n);		//file��dsa�γ��ṩ,�Ѿ���λͼ��ʽ��¼��n���ڵ���������
	while(c<n)
	{
		if(!B.test(c))		//������false
		{
			break;
		}
		c++;
	}
	return c;
}

template<typename K,typename V,typename hashfunc>
class HashTable:public Dictionary<K,V>
{
	typedef hashfunc Hash;		//������ຯ���ķ��������ϣ����Hash()
private:
	Entry<K,V>** bucket;		//Ͱ����,��Ŵ����ṹ��ָ��
	int entryNum;				//������Ŀ
	int bucketNum;				//��������Ͱ����Ŀ	���տα�entryNum/bucketNum���������
	Bitmap* lazyRemoval;		//����λͼ�ṹ��¼
protected:
	inline bool IsRemoved(int k)	//�����Ƿ�ɾ����
	{
		return lazyRemoval->test(k);//ɾ��������true
	}
	inline void lazilyRemove(int k)	//ɾ����kλ��Ͱ����ʱ���±��
	{
		lazyRemoval->set(k);		//���ʼ��Ĭ�����б���λΪFALSE
	}
	inline void unmarkRemoved(int k)
	{
		lazyRemoval->clear(k);
	}
	int ProbeForHit(const K& k)		//��ϣ������Ѱ��ͬ�ؼ���Ĵ���(ɾ���������Ѱ)
	{
		Hash hash = Hash();
		int r = hash(k,bucketNum);			//�ҵ���ʼ��
		while((!bucket[r] && IsRemoved(r)) || ((bucket[r]) && (k!=bucket[r]->key)))	//�������,Ӧ���ж��Ƿ�ɾ����,���������ָ��յĽ�����
		{												//�����жϷ����;�����ɾ�����󲹻���Ҫ������Ӧ�ı��									
			r = (r+1)%bucketNum;
		}
		return r;
	}
	int ProbeForFree(const K& k)	//��ϣ������Ѱ�ؼ���k,�п�λ�þ�ͣ����(����)
	{
		Hash hash = Hash();
		int r = hash(k,bucketNum);
		while(bucket[r])
		{
			r = (r+1)%bucketNum;
		}
		return r;
	}
	void rehash()								//��һ�������!
	{
		using namespace std;
		int oldbucketNum = bucketNum;
		Entry<K,V>** oldBucket = bucket;
		bucketNum = primeNLT(2*oldbucketNum,1048576,"../HashTable/prime-1048576-bitmap.txt");
		cout<<"new prime:"<<bucketNum<<endl;
		bucket = new Entry<K,V>*[bucketNum];
		memset(bucket,0,sizeof(Entry<K,V>*)*bucketNum);					//��ʼ��
		//memcpy(bucket,oldbucket,sizeof(Entry<K,V>*)*oldbucketNum);	//��������(�˷����Ǵ����)
		delete lazyRemoval;
		lazyRemoval = new Bitmap(bucketNum);
		entryNum = 0;			//��Ϊ����Ҫ������һ����
		for(int i=0;i<oldbucketNum;i++)
		{
			if(oldBucket[i])
			{
				Put(oldBucket[i]->key,oldBucket[i]->value);
				delete oldBucket[i];						//������Ͱ�н�ԭͰ����ɾ��
			}
		}
		delete [] oldBucket;
	}
public:
	HashTable(int n=5)				//�������ڵ���n����С������Ϊ��ϣ����
	{
		using namespace std;
		bucketNum = primeNLT(n,1048576,"../HashTable/prime-1048576-bitmap.txt");
		bucket = new Entry<K,V>* [bucketNum];
		memset(bucket,0,sizeof(Entry<K,V>*)*bucketNum);	//��ʼ����0
		lazyRemoval = new Bitmap(bucketNum);
		entryNum = 0;
		cout<<"smallest prime:"<<bucketNum<<endl;
	}
	virtual ~HashTable()
	{
		for(int i=0;i<bucketNum;i++)
		{
			if(bucket[i])
			{
				delete bucket[i];
			}
		}
		delete [] bucket;
		delete lazyRemoval;
	}
	virtual int Size() const
	{
		return entryNum;		//������Ŀ����ģ
	}
	virtual bool Put(K k,V v)
	{
		if(bucket[ProbeForHit(k)])		//������ͬ
		{
			return false;
		}
		int r = ProbeForFree(k);
		bucket[r] = new Entry<K,V>(k,v);
		++entryNum;
		if(IsRemoved(r))				//����ɾ�����
		{
			unmarkRemoved(r);
		}
		if(entryNum*2>bucketNum)		//װ������50%
		{
			rehash();
		}
		return true;
	}
	virtual bool Remove(K k)
	{
		int r = ProbeForHit(k);
		if(bucket[r])
		{
			delete bucket[r];
			bucket[r] = NULL;
			lazilyRemove(r);			//����ɾ�����
			--entryNum;
			return true;
		}
		return false;
	}
	virtual V* Get(K k)
	{
		int r = ProbeForHit(k);
		if(bucket[r])
		{
			return &bucket[r]->value;
		}
		return NULL;
	}
};//class HashTable
