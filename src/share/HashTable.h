/**
* HashTable.h.
* Last modified on: 2016/10/13
* Author:lwz
**/

#ifndef HASHTABLE_H
#define HASHTABLE_H

#include <memory>
#include "./Bitmap.h"
#include "./Dictionary.h"
#include "./MyAlgorithm.h"

ALGORITHM_BEGIN

/**
* The entry for HashTable.
* key-value pair.
**/
template<typename K,typename V>
class Entry
{
public:
    V value;        
    K key;
    int hashcode;

    Entry(K k = (K)0,V v = (V)0)
        :value(v),key(k){}

    //overload operators
    bool operator<(const Entry<K,V>& E)
    {
        return (key < E.key);
    }
    bool operator>(const Entry<K,V>& E)
    {
        return (key > E.key);
    }
    bool operator==(const Entry<K,V>& E)
    {
        return (key == E.key);
    }
    bool operator!=(const Entry<K,V>& E)
    {
        return (key != E.key);
    }
};

/**
* HashTable. type parameter: K for key, V for value.
* hashfunc for user defined Object Function type.
* hashfunc should have no-argument constructor
* and overloaded operator(), which accepts a key as a parameter, and return 
* integer hashcode.
* Also the key type should have operator=.
**/

#define DEFAULT_LOAD_FACTOR 0.5
#define DEFAULT_CAPACITY 8
template<typename K,typename V,typename hashfunc>
class HashTable:public Dictionary<K,V>
{
    typedef hashfunc Hash;
protected:
    Entry<K,V>** bucket;   //pointer to the array of pointers to the entries
    int entryNum;
    int bucketNum;             //the capacity of the bucket array
    Bitmap* lazyRemoval; //use bitmap to "remember" which bucket has been deleted.
    float load_factor;          //threshold load factor. Rehash if passed
    Hash hash;//hash function instance

    //test removed
    inline bool IsRemoved(int k)    
    {
        return lazyRemoval->test(k);
    }
    //mark remove
    inline void lazilyRemove(int k) 
    {
        lazyRemoval->set(k);  
    }
    //unmark remove
    inline void unmarkRemoved(int k)
    {
        lazyRemoval->clear(k);
    }

    //====two probing methods====//
    //two probing method stop while bucket[i] = NULL.
    //And use lazyRemoval to remember the removed entry to avoid unappropriate stopping.

    /** 
    * Search started with r, key k .
    **/
    int ProbeForHit(int r,K& k)
    {
        while((!bucket[r] && IsRemoved(r)) || ((bucket[r]) && (k!=bucket[r]->key)))//the equals judgement.                                  
            r = (r+1)%bucketNum;//keep searching
        return r;
    }

    /** 
    * Search free space started with int r
    **/
    int ProbeForFree(int r)
    {
        while(bucket[r])
            r = (r+1)%bucketNum;
        return r;
    }

    /** 
    * Move higher bit down.
    * Do this to prevent user provider poor hash method.
    **/
    int HashCodeOf(int raw)
    {
        return raw ^ (raw>>16);
    }

    //happens when pass threshold load factor
    void rehash()      
    {
        int oldbucketNum = bucketNum;
        bucketNum <<= 1;
        Entry<K,V>** oldBucket = bucket;
        bucket = new Entry<K,V>*[bucketNum];
        memset(bucket,0,sizeof(Entry<K,V>*)*bucketNum);
        lazyRemoval->test(bucketNum);//expand!
        entryNum = 0;//reset
        for(int i=0;i<oldbucketNum;i++)//put entry again.
        {
            if(oldBucket[i])
            {
                Put(oldBucket[i]->key,oldBucket[i]->value);
                delete oldBucket[i];
            }
        }
        delete [] oldBucket;
    }

public:
    /**
    * Init this hashtable with init capacity.
    **/
    HashTable(int capacity = DEFAULT_CAPACITY, float lf = DEFAULT_LOAD_FACTOR)
    {
        bucketNum = capacity;
        bucket = new Entry<K,V>* [bucketNum];
        memset(bucket,0,sizeof(Entry<K,V>*)*bucketNum);
        lazyRemoval = new Bitmap(bucketNum);
        entryNum = 0;
        load_factor = lf;
    }
    virtual ~HashTable()
    {
        for(int i=0;i<bucketNum;i++)
        {
            if(bucket[i])
                delete bucket[i];
        }
        delete [] bucket;
        delete lazyRemoval;
    }

    /** 
    * Return the size of this hashtable
    **/
    virtual int Size() const
    {
        return entryNum;
    }

    /** 
    * Get the current load factor.
    **/
    float CurrentLoadFactor()
    {
        return entryNum/bucketNum;
    }

    /** 
    * Get the setted threshold load factor.
    **/
    float getThresLoadFactor() 
    {
        return load_factor;
    }

    /** 
    * Set the threshold load factor lf no larger than 1.
    **/
    void setThresLoadFactor(float lf)
    {
        if(lf>=1)
            return;
        this->load_factor = lf;
    }

    /**
    * Put key-value pair.
    * Return true if put success, false if already exists.
    **/
    virtual bool Put(K k,V v)
    {
        int hashcode = HashCodeOf(hash(k));
        if(bucket[ProbeForHit( hashcode & (bucketNum-1), k) ] )//already exist
            return false;
        int r = ProbeForFree(hashcode & (bucketNum-1));
        bucket[r] = new Entry<K,V>(k,v);
        ++entryNum;
        bucket[r]->hashcode = hashcode;
        if(IsRemoved(r))
            unmarkRemoved(r);
        if(entryNum/bucketNum>load_factor)//the current load factor greater than threshold.
            rehash();
        return true;
    }

    /**
    * Remove key k.
    * Return true if success, false if not exists.
    **/
    virtual bool Remove(K k)
    {
        int hashcode = HashCodeOf(hash(k));
        int r = ProbeForHit(hashcode & (bucketNum-1), k);
        if(bucket[r])
        {
            delete bucket[r];
            bucket[r] = NULL;
            lazilyRemove(r);
            --entryNum;
            return true;
        }
        return false;
    }

    /** 
    * Return the pointer of value connected with key k.
    * Or Return NULL if key k not exists.
    **/
    virtual V* Get(K k)
    {
        int hashcode = HashCodeOf(hash(k));
        int r = ProbeForHit(hashcode & (bucketNum-1), k);
        if(bucket[r])
            return &bucket[r]->value;
        return NULL;
    }

    /**
    * Traverse method.
    * The object function should overload operator() accepting parameter Entry<K,V>&
    **/
    template<typename VST> void Traverse(VST& visit)
    {
        for(int i=0;i<bucketNum;i++)
        {
            if(bucket[i])//if not NULL
                visit(*bucket[i]);//visit the entry.
        }
    }
};

#undef DEFAULT_LOAD_FACTOR
#undef DEFAULT_CAPACITY

ALGORITHM_END

#endif //HASHTABLE_H
