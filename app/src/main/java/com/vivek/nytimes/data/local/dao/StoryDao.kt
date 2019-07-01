package com.vivek.nytimes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.TypeConverters
import com.vivek.nytimes.data.local.Converter
import com.vivek.nytimes.data.model.Story
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface StoryDao {

    @Insert(onConflict = REPLACE)
    fun insertStory(category: Story): Completable

    @Insert(onConflict = REPLACE)
    fun insertAllStories(stories: List<Story>): Completable

    @Query("SELECT * FROM stories")
    fun getAllStories(): Observable<List<Story>>

    @Query("SELECT COUNT(*) from stories")
    fun countStories(): Observable<Int>
}