package com.vivek.nytimes.data.repo

import com.vivek.nytimes.data.remote.NetworkService

class BusinessRepository(private val networkService: NetworkService) {

    fun getTopStories(section: String) =
        networkService.getTopStories(section)
}