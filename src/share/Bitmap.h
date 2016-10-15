/**
* Bitmap.h
* Defines a simple Bitmap
* Last modified on: 2016/10/13
* Author:lwz
**/

#ifndef BITMAP_H
#define BITMAP_H

#include "./MyAlgorithm.h"
#include "./MyString.h"
#include <stdio.h>  //for file io
#include <iostream>

ALGORITHM_BEGIN

/**
* Bitmap stores information in bits.
**/
class Bitmap
{
public:
    static const int BIT_PER_CHAR = 8;
    static const int ADDR_BIT_PER_CHAR = 3;

protected:
    char* buff;
    int size;

    /**
    * Init a buffer with n bits.
    * Initially 0.
    **/
    void init(int n)
    {
        size = n;
        int temp = (size+7)/8;
        buff = new char [temp];
        memset(buff,0,sizeof(char)*temp);
        /*for(int i=0;i<temp;i++)
            buff[i] = 0;*/
    }

    /**
    * Expand the bitmap to the i bit.
    **/
    void expand(int i)
    {
        if(i<size)
            return;
        char* oldbuff = buff;
        int oldsize = size;
        int temp = (oldsize+7)/8;
        init(2*i);              //double the buffer
        memcpy(buff,oldbuff,(oldsize+7)/8);
        /*
        for(int j=0;j<temp;j++)
            buff[j] = oldbuff[j];*/
        delete [] oldbuff;
    }

public:
    /**
    * Init a bitmap with size of n.
    **/
    Bitmap(int n=8)
    {
        init(n);                
    }

    /**
    * Init a bitmap with size of n, the information stored in file.
    **/
    Bitmap(char* file,int n=8)
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

    //====modify methods.======//
    
    //use 10000000>>(i&0111) to locate the bit in the byte for i.

    /**
    * set the i bit to true.
    **/
    void set(int i)
    {
        expand(i);
        int posi = i>>ADDR_BIT_PER_CHAR;//locate the byte position of the bit.
        buff[posi] |= (0x80>>(i&0x07));//10000000>>(i%8) to express the bit in the byte position
    }

    /** 
    * set the i bit to false.
    **/
    void clear(int i)   
    {
        expand(i);      
        int posi = i>>ADDR_BIT_PER_CHAR;
        buff[posi] &= ~(0x80>>(i&0x07));
    }

    /**
    * clear whold bitmap.
    **/
    void clear()
    {
        int UsedChar = (size+7)/8-1;
        while(UsedChar>-1)
            buff[UsedChar--] = 0;
    }

    /**
    * Return i bit.
    **/
    bool test(int i)
    {
        expand(i);
        int posi = i>>3;
        return buff[posi] & (0x80>>(i&0x07)); 
    }

    /**
    * dump this bitmap to the file.
    **/
    void dump(char* file)
    {
        FILE* fp = fopen(file,"w");
        fwrite(buff,sizeof(char),(size+7)/8,fp);
        fclose(fp);
    }

    /**
    * 
    **/
    void printbits(int n)
    {
        using namespace std;
        expand(n);
        for(int i=0;i<n;i++)
        {
            if(test(i))
                cout<<"1 ";
            else
                cout<<"0 ";
        }
        cout<<endl;
    }
};

ALGORITHM_END

#endif //BITMAP_H
