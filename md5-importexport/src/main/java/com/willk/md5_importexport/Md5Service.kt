package com.willk.md5_importexport

import android.app.Service
import android.content.Intent
import android.os.IBinder


import com.willk.aidl.Md5AidlInterface
import com.willk.md5_importexport.domain.LocalRepository
import com.willk.md5_importexport.domain.Md5Entity


class Md5Service : Service() {

    override fun onBind(intent: Intent): IBinder {
        return object: Md5AidlInterface.Stub() {

            override fun md5Search(aString: String?): Boolean {

                    var list = LocalRepository(applicationContext).search(aString)
                    return list.isEmpty()

            }

            override fun md5Add(aString: String?) {
                if (aString != null) {
                LocalRepository(applicationContext).insertMd5(md5Entity = Md5Entity(md5 = aString))
                }
            }

            override fun md5Check(string: String): Boolean {

                return Regex("^[0-9a-fA-F]{32}$").matches(string)
            }

        }
    }


}