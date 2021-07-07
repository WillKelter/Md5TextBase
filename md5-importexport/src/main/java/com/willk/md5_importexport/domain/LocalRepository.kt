package com.willk.md5_importexport.domain

import android.content.Context

class LocalRepository(val context: Context?) {

    private val roomDatabase = context?.applicationContext?.let { Md5Database.getInstance(it)}
    private val dao = roomDatabase?.md5Dao()

    fun getMd5(): List<Md5Entity> {
        return dao?.getAll()!!
    }

    fun search(search: String?): List<Md5Entity> {
        return dao?.search(search)!!
    }

    fun insertMd5(md5Entity: Md5Entity) {
        return dao?.insertAll(md5Entity)!!
    }

    fun deleteMd5() {
        return dao?.deleteAll()!!
    }
}