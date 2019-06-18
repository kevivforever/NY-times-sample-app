package com.vivek.nytimes.data.repo

import com.vivek.nytimes.data.local.DatabaseService
import com.vivek.nytimes.data.model.Story
import com.vivek.nytimes.data.remote.NetworkService

class StoryRepository(private val networkService: NetworkService, private val databaseService: DatabaseService) {

    fun getTopStories(section: String) =
        networkService.getTopStories(section).map {
            it.results
        }

    fun saveStories(stories: List<Story>) = databaseService.storyDao().insertAllStories(stories)
    fun countstories() = databaseService.storyDao().countCategories()

}