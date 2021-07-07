// Md5AidlInterface.aidl
package com.willk.aidl;



// Declare any non-default types here with import statements

interface Md5AidlInterface {

    boolean md5Search(String aString);

    void md5Add(String aString);

    boolean md5Check(String string);
}