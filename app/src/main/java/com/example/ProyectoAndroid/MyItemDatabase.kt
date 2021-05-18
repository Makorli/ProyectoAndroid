package com.example.ProyectoAndroid

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyItemEntity::class],version = 1,exportSchema = false)


abstract class MyItemDatabase :RoomDatabase(){

    abstract fun ItemsDAO():MyItemDAO

    companion object{

        @Volatile
        private var INSTANCE:MyItemDatabase?=null

        fun getDatabase(context: Context):MyItemDatabase{
            return INSTANCE ?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    MyItemDatabase::class.java,
                    "items_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}