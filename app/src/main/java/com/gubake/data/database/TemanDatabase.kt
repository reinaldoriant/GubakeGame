package com.gubake.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Teman::class, Pemain::class], version = 1)
abstract class TemanDatabase :RoomDatabase() {
    abstract fun temanDao() : TemanDao
    abstract fun pemainDao() : PemainDao

    companion object{
        private var INSTANCE : TemanDatabase? =null

        fun getInstance(context: Context): TemanDatabase? {
            if (INSTANCE == null){
                synchronized(TemanDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    TemanDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}