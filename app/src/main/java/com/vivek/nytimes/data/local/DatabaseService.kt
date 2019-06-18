package com.vivek.nytimes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vivek.nytimes.data.local.dao.StoryDao
import com.vivek.nytimes.data.model.Story
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        Story::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converter::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun storyDao(): StoryDao

    companion object {

        @Volatile private var INSTANCE: DatabaseService? = null

        fun getInstance(context: Context): DatabaseService =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                DatabaseService::class.java, "nytimes")
                .build()
    }
}