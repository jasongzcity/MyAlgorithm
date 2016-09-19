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

int primeNLT(int c,int n,char* file)	//取[c,n)间的最小素数
{
	Bitmap B(file,n);		//file由dsa课程提供,已经用位图格式记录了n以内的所有素数
	while(c<n)
	{
		if(!B.test(c))		//素数是false
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
	typedef hashfunc Hash;		//用外接类函数的方法定义哈希函数Hash()
private:
	Entry<K,V>** bucket;		//桶数组,存放词条结构的指针
	int entryNum;				//词条数目
	int bucketNum;				//词条数组桶的数目	依照课本entryNum/bucketNum是填充因子
	Bitmap* lazyRemoval;		//借助位图结构记录
protected:
	inline bool IsRemoved(int k)	//测试是否删除过
	{
		return lazyRemoval->test(k);//删除过返回true
	}
	inline void lazilyRemove(int k)	//删除第k位的桶内容时留下标记
	{
		lazyRemoval->set(k);		//因初始化默认所有比特位为FALSE
	}
	inline void unmarkRemoved(int k)
	{
		lazyRemoval->clear(k);
	}
	int ProbeForHit(const K& k)		//哈希表中搜寻相同关键码的词条(删除点继续搜寻)
	{
		Hash hash = Hash();
		int r = hash(k,bucketNum);			//找到初始点
		while((!bucket[r] && IsRemoved(r)) || ((bucket[r]) && (k!=bucket[r]->key)))	//留意次序,应先判断是否删除过,否则会引发指向空的解引用
		{												//这种判断方法就决定了删除过后补回来要消除相应的标记									
			r = (r+1)%bucketNum;
		}
		return r;
	}
	int ProbeForFree(const K& k)	//哈希表中搜寻关键码k,有空位置就停下来(插入)
	{
		Hash hash = Hash();
		int r = hash(k,bucketNum);
		while(bucket[r])
		{
			r = (r+1)%bucketNum;
		}
		return r;
	}
	void rehash()								//这一步需检验!
	{
		using namespace std;
		int oldbucketNum = bucketNum;
		Entry<K,V>** oldBucket = bucket;
		bucketNum = primeNLT(2*oldbucketNum,1048576,"../HashTable/prime-1048576-bitmap.txt");
		cout<<"new prime:"<<bucketNum<<endl;
		bucket = new Entry<K,V>*[bucketNum];
		memset(bucket,0,sizeof(Entry<K,V>*)*bucketNum);					//初始化
		//memcpy(bucket,oldbucket,sizeof(Entry<K,V>*)*oldbucketNum);	//拷贝数据(此方法是错误的)
		delete lazyRemoval;
		lazyRemoval = new Bitmap(bucketNum);
		entryNum = 0;			//因为等下要重新逐一插入
		for(int i=0;i<oldbucketNum;i++)
		{
			if(oldBucket[i])
			{
				Put(oldBucket[i]->key,oldBucket[i]->value);
				delete oldBucket[i];						//放入新桶中将原桶内容删除
			}
		}
		delete [] oldBucket;
	}
public:
	HashTable(int n=5)				//搜索大于等于n的最小素数作为哈希表长度
	{
		using namespace std;
		bucketNum = primeNLT(n,1048576,"../HashTable/prime-1048576-bitmap.txt");
		bucket = new Entry<K,V>* [bucketNum];
		memset(bucket,0,sizeof(Entry<K,V>*)*bucketNum);	//初始化置0
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
		return entryNum;		//词条数目即规模
	}
	virtual bool Put(K k,V v)
	{
		if(bucket[ProbeForHit(k)])		//若有雷同
		{
			return false;
		}
		int r = ProbeForFree(k);
		bucket[r] = new Entry<K,V>(k,v);
		++entryNum;
		if(IsRemoved(r))				//若有删除标记
		{
			unmarkRemoved(r);
		}
		if(entryNum*2>bucketNum)		//装填因子50%
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
			lazilyRemove(r);			//留下删除标记
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
