package com.willk.md5_importexport.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Md5Entity::class], version = 1, exportSchema = false)
abstract class Md5Database: RoomDatabase() {
    abstract fun md5Dao(): Md5Dao

    companion object {
        @Volatile
        private var INSTANCE: Md5Database? = null

        fun getInstance(context: Context): Md5Database {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        Md5Database::class.java,
                        "notes_database"
                    ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}