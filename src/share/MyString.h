/*********************
* MyString.h 
* Author: lwz.
* Since: 2016-9-24
*
*********************/

#ifndef MYSTRING_H
#define MYSTRING_H

#include <iostream>
#include <string.h> //usage of strlen & strcpy
#include "./MyAlgorithm.h"

ALGORITHM_BEGIN

/**
* String encapsulated char array. Maybe the most frequetly used data structure.
* Notice: the "position" is started from 0, that means the first letter in this 
* class is described as "in position 0"
**/
class MyString
{
private:
    char* buff;
    int length;
    int* kmp;//kmp array
    bool isKmpAllocated;//the kmp array is lazily allocated
    int kmp_capacity;//the capacity of the kmp array, to avoid uneccessary reallocate 

public:
    MyString()
    {
        buff = NULL;
        length = 0;
        kmp = NULL;
        kmp_capacity = 0;
        isKmpAllocated = false;
    }
    virtual ~MyString()
    {
        delete [] buff;
        delete [] kmp;
    }

    /**
    * Assign a string.
    * The parameter string should end with '\0'
    * return true if memory allocating success.
    * return false if assign failed.
    **/
    bool StrAssign(const char* chars)
    {
        if(buff)
            delete [] buff; 
        int CharCount = strlen(chars);
        if(CharCount == 0)//if empty.
        {
            length = 0;
            buff = NULL;
            return false;
        }
        buff = (char*) new char[(CharCount+1)];     //+1for '\0'
        if(!buff)//overflow
        {
            length = 0;
            return false;
        }
        strcpy(buff,chars);
        *(buff+CharCount) = '\0';
        length = CharCount;
        isKmpAllocated = false;//need to reallocate kmp array
        return true;
    }

    /**
    * Assign string.Copy the string content to this string.
    **/
    const MyString& operator=(const MyString& str)
    {
        StrAssign(str.buff);
        return *this;
    }

    int Length()
    {
        return length;
    }

    /**
    * Check if the kmp array needs to be calculated
    **/
    bool IsKmpAllocated()
    {
        return isKmpAllocated;
    }

    bool IsEmpty()
    {
        return length==0;
    }

    /**
    * Overload operator.
    * Return the reference of the char at position i.
    **/
    char& operator[](const int& i)
    {
        return this->buff[i];
    }

    /**
    * Return the reference of the char at position i.
    **/
    char& CharAt(const int& i)
    {
        return (*this)[i];
    }
    
    /**
    * Compare method.
    * Return true if equals.
    * Time complexity: O(n)
    **/
    bool StrCompare(const MyString& str)
    {
        if(this->Length()!=str.length) //fast checking
            return false;
        return StrCompare(str.buff);
    }

    /**
    * Compare with char array.
    * Return true if equal.
    * The pass in string should end with '\0' or may lead to unpredictable result.
    * Time complexity: O(n)
    **/
    bool StrCompare(const char* str)
    {
        char* temp_this = buff;
        int count = strlen(str);

        if(count!=this->Length())
            return false;
        
        while((*str)!='\0')
        {
            if((*str) != (*temp_this))
                return false;
            str++;
            temp_this++;
        }
        return true;
    }

    /**
    * Clear the string.
    **/
    bool Clear()
    {
        delete [] buff;//not reuse this array
        length = 0;
        buff = NULL;
    }

    /**
    * Overload operator += to concat a string
    **/
    const MyString& operator+=(const MyString &str)
    {
        Concat(str);
        return *this;
    }
    
    bool Concat(const MyString& str)
    {
        char* temp = buff;
        buff =(char*) new char[(length+str.length+1)];      //+1 for '\0'
        if(!buff)
        {
            buff = temp;
            return false;
        }
        strcpy(buff,temp);//copy old buff
        int count = length;
        delete [] temp;
        const char* strTmp = str.buff;
        while(count<length+str.length)
        {
            *(buff+count) = *strTmp++;
            count++;
        }
        *(buff+count) = '\0';
        length += str.length;
        isKmpAllocated = false;//need to reallocate kmp array
        return true;
    }

    /**
    * Get sub string from position pos and length len.
    * It will not modify the origin string.
    * Notice: len not include the ending '\0'. This string's position starts from 0.
    * Return false if the arguments are illegal.
    * Return true if the string has been allocated.
    **/
    bool SubString(MyString& Sub,int pos,int len)
    {
        if(pos<0 || pos+len>length || len<1)//illegal arguments
            return false;
        if(Sub.buff)
            delete [] Sub.buff;
        Sub.buff = (char*)new char[(len+1)];
        int count = 0;
        for(;count<len;count++)
            *(Sub.buff+count) = *(buff+pos+count);//copy
        Sub.length = len;
        *(Sub.buff+count) = '\0';
        Sub.isKmpAllocated = false;//the substring needs to reallocate the kmp array
        return true;
    }

    //put the char buffer to standard output. 
    void DispString()
    {
        std::cout<<buff<<std::endl;
    }

    //put kmp array to standard output.
    void DispKmpArray()
    {
        if(!isKmpAllocated)
        {
            std::cout<<"kmp array has not allocated"<<std::endl;
            return;
        }
        int i = 0;
        while(i<length)
        {
            std::cout<<kmp[i]<<std::endl;
            i++;
        }
    }

    //======matching methods======//

    /**
    * Search the target char in this string begin with begin position 
    * Begin with 0 defaultly.
    * Return the position of the target char.
    * Return -1 if not found.
    **/
    int PositionOf(const char& c,int BeginPosition)
    {
        if(IsEmpty())
            return -1;
        while(BeginPosition<length)
        {
            if(*(buff+BeginPosition)==c)
                return BeginPosition;
            BeginPosition++;
        }
        return -1;
    }

    /**
    * Match a string without generating a kmp array.
    * Begin with 0 defaultly.
    * Return the position of the target char.
    * Return -1 if not found.
    * Time complexity: O(length*strlen(mystr))
    **/
    int PositionOf(const MyString& mystr,int BeginPosition)
    {
        return PositionOf(mystr.buff,BeginPosition);
    }

    /**
    * Match a pattern without generating a kmp array.
    * Begin with 0 defaultly.
    * Return the position of the target char.
    * Return -1 if not found.
    * Time complexity: O(length*strlen(pattern))
    **/
    int PositionOf(const char* pattern,int BeginPosition)
    {
        int ptr_len = strlen(pattern);
        if(ptr_len>length)
            return -1;
        for(;BeginPosition<=length-ptr_len;BeginPosition++)
        {
            int i=BeginPosition;
            for(int j=0;j<ptr_len;j++,i++)
            {
                if(*(buff+i)!=*(pattern+j))
                    break;                //jump out of the inner loop if not matches
                if(j==ptr_len-1)   //match the whole pattern!
                    return BeginPosition;
            }
        }
        return -1;//not match
    }

    /**
    * Match method using kmp array.
    * This algorithm can improve efficiency when this string contains a lot of alike substrings.
    * Begin with 0 defaultly.
    * Return the position of the target char.
    * Return -1 if not found.
    * Time complexity: O(length*strlen(pattern))
    **/
    int PositionOf_Kmp(MyString& Sub,int BeginPosition)
    {
        Sub.SetKmpNext();
        int i=-1;//pointer to the pattern
        while(BeginPosition<length && i<Sub.length)
        {
            if(i<0 || Sub[i] == buff[BeginPosition])
            {
                BeginPosition++;
                i++;
            }
            else
                i = Sub.kmp[i];
        }
        return (i==Sub.length)?(BeginPosition-i):-1;
    }

    /**
    * Calculate KMP array for this string.
    * It is a procedure to match string to itself.
    * Time complexity: O(n)
    **/
    void SetKmpNext()
    {
        if(isKmpAllocated)
            return;
        if(kmp_capacity<length)//need to reallocate space
        {
            delete [] kmp;
            kmp_capacity = length;
            kmp = new int[kmp_capacity];
        }
        kmp[0] = -1;
        int i=-1,j=0;
        while(j<length-1)
        {
            if(i<0 || buff[i] == buff[j])//plus 1 for both pointer to match next char
            {
                i++;
                j++;
                if(buff[j]!=buff[i])
                    kmp[j] = i;
                else
                    kmp[j] = kmp[i];//special case when i==0 and buff[j] == buff[i]
            }
            else
                i = kmp[i];
        }
        isKmpAllocated = true;
    }
};

ALGORITHM_END

#endif //MYSTRING_H