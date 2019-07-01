package com.vivek.nytimes.data.repo

import com.vivek.nytimes.data.local.DatabaseService
import com.vivek.nytimes.data.model.Story
import com.vivek.nytimes.data.remote.NetworkService
import javax.inject.Inject

class StoryRepository @Inject constructor(private val networkService: NetworkService, private val databaseService: DatabaseService) {

    fun getTopStories(section: String) =
        networkService.getTopStories(section).map {
            it.results
        }

    fun saveStories(stories: List<Story>) = databaseService.storyDao().insertAllStories(stories)
    fun totalstories() = databaseService.storyDao().countStories()

}