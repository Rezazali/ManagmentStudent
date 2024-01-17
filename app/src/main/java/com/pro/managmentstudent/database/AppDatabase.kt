package com.pro.managmentstudent.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pro.managmentstudent.models.Lesson
import com.pro.managmentstudent.models.SelectionUnit
import com.pro.managmentstudent.models.Student

@Database(entities = [Lesson::class,Student::class,SelectionUnit::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun idao(): Dao?

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase?{
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,"test.db").allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}