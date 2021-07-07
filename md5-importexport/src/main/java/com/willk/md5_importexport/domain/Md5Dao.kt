package com.willk.md5_importexport.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface Md5Dao {

    @Query("SELECT * FROM md5entity")
    fun getAll() : List<Md5Entity>

    @Query("SELECT * FROM md5entity WHERE md5 LIKE :search")
    fun search(search: String?): List<Md5Entity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg md5Entity: Md5Entity)


    @Query("DELETE FROM md5entity")
    fun deleteAll()
}